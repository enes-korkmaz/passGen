package de.hhn.it.devtools.components.passGen;

import de.hhn.it.devtools.apis.exceptions.WrongLoginCredentialsException;
import de.hhn.it.devtools.components.passGen.provider.SimpleToken;
import de.hhn.it.devtools.components.passGen.provider.SimpleUser;
import de.hhn.it.devtools.components.passGen.provider.SimpleUserManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSimpleUserGoodCases {
  private SimpleUserManagementService service;
  private SimpleUser user;
  private int userId;

  @BeforeEach
  void setup() throws WrongLoginCredentialsException {
    service = new SimpleUserManagementService();
    userId = service.createUser("user@example.com", "password123", true);
    user = service.getUser(userId);
    service.login("user@example.com", "password123");
  }

  @Test
  @DisplayName("Test if user can be retrieved with correct ID")
  public void testGetUser() {
    assertEquals(user, service.getUser(userId));
  }

  @Test
  @DisplayName("Test if credentials are correctly validated")
  public void testCheckCredentials() {
    assertTrue(service.checkCredentials("user@example.com", "password123"));
  }

  @Test
  @DisplayName("Test if token can be added and retrieved from user")
  public void testAddAndRetrieveToken() {
    SimpleToken newToken = new SimpleToken("token456", new HashMap<>(), new HashMap<>(), service);
    user.addTokenToUser(newToken);
    assertEquals(newToken, user.retrieveTokenFromUser());
  }

  @Test
  @DisplayName("Test if the password can be changed")
  public void testChangePassword() {
    user.setPassword("newPassword123");
    assertTrue(service.checkCredentials("user@example.com", "newPassword123"));
  }

  @Test
  @DisplayName("Test if the token can be removed from the user")
  public void testRemoveTokenFromUser() {
    user.removeTokenFromUser();
    assertNull(user.retrieveTokenFromUser());
  }

  @Test
  @DisplayName("Test returns true if the user is correct")
  public void testIsUser() {
    assertTrue(user.isUser());
  }

}
