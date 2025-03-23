package de.hhn.it.devtools.components.passGen.provider;

import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;
import de.hhn.it.devtools.apis.exceptions.TooManyTokensException;
import de.hhn.it.devtools.apis.passGen.Token;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A simple implementation of the Token interface.
 */
public class SimpleToken implements Token {
  private String token;
  private Map<SimpleToken, LocalDateTime> creationTime;
  private Map<SimpleToken, LocalDateTime> expirationTime;
  private SimpleUser user;
  private SimpleUserManagementService userService;
  // A secure random number generator.
  private static final SecureRandom secureRandom = new SecureRandom();
  // A base64 URL encoder.
  private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
  private Map<SimpleUser, SimpleToken> tokenMap = new ConcurrentHashMap<>();
  private static final org.slf4j.Logger logger =
          org.slf4j.LoggerFactory.getLogger(SimpleToken.class);

  /**
   * Creates a new SimpleToken with the specified token.
   *
   * @param token the token value
   */
  public SimpleToken(String token, Map<SimpleToken, LocalDateTime> creationTime,
                     Map<SimpleToken, LocalDateTime> expirationTime,
                     SimpleUserManagementService userService) {
    this.token = token;
    this.creationTime = creationTime;
    this.expirationTime = expirationTime;
    this.userService = userService;
  }

  /**
   * Returns the token value.
   *
   * @return the token value
   */
  public SimpleToken getTokenFromMap(SimpleUser user) {
    return tokenMap.get(user);
  }

  /**
   * Returns the creation time for the specified token.
   *
   * @param tokenKey the token whose creation time is to be retrieved
   * @return the creation time for the specified token
   * @throws IllegalArgumentException if the specified token is null
   */
  public LocalDateTime getCreationTime(SimpleToken tokenKey) {
    if (tokenKey == null) {
      throw new IllegalArgumentException("Token is null.");
    }
    return creationTime.get(tokenKey);
  }

  /**
   * Returns the expiration time for the specified token.
   *
   * @param tokenKey the token whose expiration time is to be retrieved
   * @return the expiration time for the specified token
   * @throws IllegalArgumentException if the specified token is null
   *                                  or does not reference any token expiry times
   */
  public LocalDateTime getExpirationTime(SimpleToken tokenKey) {
    // gets checked in isExpired() method
    //    if (tokenKey == null) {
    //      throw new IllegalArgumentException("Token is null.");
    //    }
    return expirationTime.get(tokenKey);
  }

  /**
   * Removes the token time info of a specified token.
   *
   * @param tokenKey the token whose info is to be removed
   */
  public void removeTokenTimeInfo(SimpleToken tokenKey) {
    // only when token is expired
    creationTime.remove(tokenKey);
    expirationTime.remove(tokenKey);
    logger.debug("Removed token time info from token with id: " + tokenKey);
  }

  /**
   * Checks if the specified token has expired.
   *
   * @param tokenKey the token to check
   * @return true if the token has expired, false otherwise
   * @throws IllegalArgumentException if the specified token is null
   *                                  or does not reference any token expiry times
   */
  public boolean isExpired(SimpleToken tokenKey, SimpleUser user) {
    if (tokenKey == null || !(expirationTime.containsKey(tokenKey))) {
      throw new IllegalArgumentException("Token is null or does not exist.");
    } else if (LocalDateTime.now().isAfter(getExpirationTime(tokenKey))) {
      removeTokenTimeInfo(tokenKey);
      user.removeTokenFromUser();
      tokenMap.remove(user, tokenKey);
      logger.debug("Removed token association with user:" + user.getAddress());

      for (int userIndex = 1; userIndex < userService.getUsers().size(); userIndex++) {
        SimpleUser currentUser = userService.getUsers().get(userIndex);
        if (currentUser == user) {
          removeToken(userIndex);
        }
      }
      logger.debug("Token is expired. Token id: " + tokenKey);
      return true;
    }
    logger.debug("Token is not expired. Token id: " + tokenKey);
    return false;
  }

  @Override
  public void removeToken(int userId) {
    tokenMap.remove(userService.getUser(userId));
    logger.debug("Removed token from user with id: " + userId);
  }

  @Override
  public synchronized void generateToken(int userId)
          throws IllegalArgumentException, TooManyTokensException {
    if (userId <= 0) {
      throw new IllegalArgumentException("User Id cannot be a null reference.");
    }

    //select user
    user = userService.getUser(userId);
    SimpleToken token = user.getToken();
    if (token != null) {
      throw new TooManyTokensException("User with id " + userId + " already has an active token.");
    }

    // generate a random byte array of size 8 == length 12 - doesn't hold up with expectation
    // is much longer might cause performance issues in real application
    byte[] randomBytes = new byte[8];
    secureRandom.nextBytes(randomBytes);

    // encode the random byte array into a string using base64 URL encoding
    String encodedRandomBytes = base64Encoder.encodeToString(randomBytes);

    // create a new SimpleToken with the encoded random bytes
    SimpleToken tokenToBeAdded =
            new SimpleToken(encodedRandomBytes, Map.of(), Map.of(), userService);

    // add creation time and associate it with the token
    creationTime.put(tokenToBeAdded, LocalDateTime.now());
    // add expiration time and associate it with the token
    expirationTime.put(tokenToBeAdded, LocalDateTime.now().plusSeconds(599)); //9.59 min
    // associate the token with the user
    tokenMap.put(user, tokenToBeAdded);

    user.addTokenToUser(tokenToBeAdded);
    logger.debug("Assigned token to user with id: " + userId);
  }

  /**
   * Returns the token.
   *
   * @return the token
   */
  @Override
  public String toString() {
    return token;
  }

  /**
   * ONLY FOR TESTING. DO NOT USE THIS ELSEWHERE.
   * Sets the expiration time of a specific token before the creation time which
   * will effectively expire it (the next time it gets checked).
   *
   * @param tokenKey the token whose expiration time is to be set
   * @param timeToSubtract the number of minutes to subtract from the current
   *                       expiration time before setting it
   */
  public void setExpirationTime(SimpleToken tokenKey, int timeToSubtract) {
    expirationTime.put(tokenKey, getExpirationTime(tokenKey).minusSeconds(timeToSubtract));
  }
}