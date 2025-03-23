package de.hhn.it.devtools.components.passGen;

import de.hhn.it.devtools.apis.exceptions.TooManyTokensException;
import de.hhn.it.devtools.components.passGen.provider.SimpleToken;
import de.hhn.it.devtools.components.passGen.provider.SimpleUser;
import de.hhn.it.devtools.components.passGen.provider.SimpleUserManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTokenGenerationGoodCases {
  private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TestTokenGenerationGoodCases.class);
  SimpleUserManagementService userService;
  SimpleToken token;
  SimpleUser user;
  int userId = 0;

  @BeforeEach
  void setup() {
    userService = new SimpleUserManagementService();
    token = new SimpleToken(null, new HashMap<>(), new HashMap<>(), userService);
    userId = userService.createUser("user@example.com", "123Password", true);
    user = userService.getUser(userId);
  }


  @Test
  @DisplayName("Test if Token gets generated")
  public void testTokenGeneration() throws IllegalArgumentException, TooManyTokensException {
    token.generateToken(userId);
    logger.debug("Token " + user.getToken().toString() + " was generated!");

    boolean success = false;
    if (user.getToken().toString().length() == 12) {
      success = true;
    }
    assertTrue(success);
  }

  @Test
  @DisplayName("Test Token length")
  public void testTokenLength() throws IllegalArgumentException, TooManyTokensException {
    token.generateToken(userId);
    logger.debug("Token für user " + userService.getUsers().size() + " entspricht: " + user.getToken().toString());
    assertEquals(12, user.getToken().toString().length());
  }

  @Test
  @DisplayName("Test if token is assigned to user")
  public void testTokenAssignmentToUser() throws IllegalArgumentException, TooManyTokensException {
    token.generateToken(userId);
    logger.debug("Token " + user.getToken().toString() + " was assigned to user with id: " + userId);

    boolean assigned = false;
    if (user.getToken().toString().length() == 12) {
      assigned = true;
    }
    assertTrue(assigned);
  }

  @Test
  @DisplayName("Test if token is expired before expiration time has passed")
  public void testTokenExpiredBeforeExpiration() throws IllegalArgumentException, TooManyTokensException {
    token.generateToken(userId);
    boolean success = false;
    if (user.getToken().toString().length() == 12) {
      success = true;
    }
    assertTrue(success);

    //token expires after 10min
    logger.debug(String.valueOf(token.getTokenFromMap(user)));
    assertFalse(token.isExpired(token.getTokenFromMap(user), user));
  }

  @Test
  @DisplayName("Test if token is expired after expiration time has passed")
  public void testTokenExpiredAfterExpiration() throws IllegalArgumentException, TooManyTokensException {
    token.generateToken(userId);
    boolean success = false;
    if (user.getToken().toString().length() == 12) {
      success = true;
    }
    assertTrue(success);

    //actual creation time
    logger.debug("Actual creation time: " + token.getCreationTime(token.getTokenFromMap(user)));
    //substract +10 min from token expiration time
    token.setExpirationTime(token.getTokenFromMap(user), 600);
    logger.debug("Modified expiration time: " + token.getExpirationTime(token.getTokenFromMap(user)));

    assertTrue(token.isExpired(token.getTokenFromMap(user), user));
  }

  @Test
  @DisplayName("Test if token gets removed before expiration time has passed")
  public void testTokenRemovalBeforeExpiration() throws IllegalStateException, TooManyTokensException {
    token.generateToken(userId);
    SimpleToken originalToken = token.getTokenFromMap(user);

    boolean success = false;
    if (user.getToken().toString().length() == 12) {
      success = true;
    }
    assertTrue(success);

    token.setExpirationTime(token.getTokenFromMap(user), 500);
    assertFalse(token.isExpired(token.getTokenFromMap(user), user));

    //test if original token still exists
    SimpleToken currentToken = token.getTokenFromMap(user);
    assertEquals(originalToken, currentToken);
    logger.debug("Original token " + originalToken + " equals current token " + currentToken + " of user " + userId);
  }

  @Test
  @DisplayName("Test if token gets removed after expiration time has passed")
  public void testTokenRemovalAfterExpiration() throws IllegalStateException, TooManyTokensException {
    token.generateToken(userId);

    boolean success = false;
    if (user.getToken().toString().length() == 12) {
      success = true;
    }
    assertTrue(success);

    token.setExpirationTime(token.getTokenFromMap(user), 600);
    assertTrue(token.isExpired(token.getTokenFromMap(user), user));

    logger.debug("After removing token for user equals " + token.getTokenFromMap(user));
    assertNull(token.getTokenFromMap(user));
  }

  @Test
  @DisplayName("Test if token gets removed from user")
  public void testTokenRemovalFromUser() throws IllegalStateException, TooManyTokensException {
    token.generateToken(userId);
    SimpleToken originalToken = token.getTokenFromMap(user);

    boolean success = false;
    if (user.getToken().toString().length() == 12) {
      success = true;
    }
    assertTrue(success);

    assertEquals(originalToken, user.getToken());

    token.setExpirationTime(token.getTokenFromMap(user), 600);
    assertTrue(token.isExpired(token.getTokenFromMap(user), user));

    assertNull(user.getToken());
  }
}