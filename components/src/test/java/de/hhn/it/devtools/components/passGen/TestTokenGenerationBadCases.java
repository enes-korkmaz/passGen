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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTokenGenerationBadCases {
  private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TestPasscodeGeneratorBadCases.class);
  SimpleUserManagementService userService;
  SimpleToken token;
  SimpleUser user1;
  SimpleUser user2;
  SimpleUser user3;
  SimpleUser user4;
  SimpleUser user5;
  int userId1 = 0;
  int userId2 = 0;
  int userId3 = 0;
  int userId4 = 0;
  int userId5 = 0;


  int userIdNotExisting = 0; // soll nie aktualisiert werden

  @BeforeEach
  void setup() {
    userService = new SimpleUserManagementService();
    token = new SimpleToken("", new HashMap<>(), new HashMap<>(), userService);
    userId1 = userService.createUser("user1@example.com", "1Password", true);
    userId2 = userService.createUser("user2@example.com", "12Password", true);
    userId3 = userService.createUser("user3@example.com", "123Password", true);
    userId4 = userService.createUser("user4@example.com", "1234Password", true);
    userId5 = userService.createUser("user5@example.com", "12345Password", true);
    user1 = userService.getUser(userId1);
    user2 = userService.getUser(userId2);
    user3 = userService.getUser(userId3);
    user4 = userService.getUser(userId4);
    user5 = userService.getUser(userId5);
  }


  @Test
  @DisplayName("Test if Token is generated")
  public void testTokenGeneration() throws IllegalArgumentException, TooManyTokensException {
    token.generateToken(userId1);

    logger.debug("Token " + user1.getToken().toString() + " was generated!");

    boolean success = false;
    if (user1.getToken().toString().length() == 12) {
      success = true;
    }
    assertTrue(success);
  }

  @Test
  @DisplayName("Test generating token twice for a user")
  public void testGenerationgTwoTokenForOneUser() throws IllegalArgumentException, TooManyTokensException {
    token.generateToken(userId1);

    logger.debug("Token für user " + userId1 + " entspricht: " + user1.getToken().toString());
    assertEquals(12, user1.getToken().toString().length());

    TooManyTokensException exception = assertThrows(TooManyTokensException.class, () -> token.generateToken(userId1));
  }

  @Test
  @DisplayName("If user1 generates token but user2 doesnt user2 shouldn't have a token")
  public void testTokenAssignmentToUser1() throws IllegalArgumentException, TooManyTokensException {
    token.generateToken(userId1);

    logger.debug("Token " + user1.getToken().toString() + " was assigned to user with id: " + userId1);

    NullPointerException exception = assertThrows(NullPointerException.class, () -> user2.getToken().toString().length());
  }

  @Test
  @DisplayName("Test removal of token from multiple users")
  public void testRemovalOfRightToken() throws IllegalArgumentException, TooManyTokensException {
    token.generateToken(userId1);
    token.generateToken(userId2);

    SimpleToken user1Token = user1.getToken();
    SimpleToken user2Token = user2.getToken();

    assertNotEquals(user1Token, user2Token);

    token.setExpirationTime(token.getTokenFromMap(user1), 600);
    assertTrue(token.isExpired(token.getTokenFromMap(user1), user1));

    assertNull(user1.getToken());
    assertEquals(user2Token, user2.getToken());
  }

  @Test
  @DisplayName("Test removal of the same token twice")
  public void testRemovalOfTokenTwice() throws IllegalArgumentException, TooManyTokensException {
    token.generateToken(userId1);

    token.setExpirationTime(token.getTokenFromMap(user1), 600);
    //1
    assertTrue(token.isExpired(token.getTokenFromMap(user1), user1));
    //2
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> token.isExpired(token.getTokenFromMap(user1), user1));
  }

  @Test
  @DisplayName("Test if generating token for non existen user")
  public void testTokenGenerationForNonExistentUser() throws IllegalArgumentException, TooManyTokensException {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> token.generateToken(userIdNotExisting));
  }

  @Test
  @DisplayName("Test generating and removing token for multiple users")
  public void testTokenPerformaceWhenGeneratingMultipleToken() throws IllegalArgumentException, TooManyTokensException {
    token.generateToken(userId1);
    token.generateToken(userId2);
    token.generateToken(userId3);
    token.generateToken(userId4);
    token.generateToken(userId5);

    token.setExpirationTime(token.getTokenFromMap(user1), 600);
    assertTrue(token.isExpired(token.getTokenFromMap(user1), user1));

    token.setExpirationTime(token.getTokenFromMap(user2), 600);
    assertTrue(token.isExpired(token.getTokenFromMap(user2), user2));

    token.setExpirationTime(token.getTokenFromMap(user3), 600);
    assertTrue(token.isExpired(token.getTokenFromMap(user3), user3));

    token.setExpirationTime(token.getTokenFromMap(user4), 600);
    assertTrue(token.isExpired(token.getTokenFromMap(user4), user4));

    token.setExpirationTime(token.getTokenFromMap(user5), 600);
    assertTrue(token.isExpired(token.getTokenFromMap(user5), user5));
  }
}