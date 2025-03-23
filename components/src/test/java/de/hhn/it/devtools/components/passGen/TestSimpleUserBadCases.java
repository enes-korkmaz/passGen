package de.hhn.it.devtools.components.passGen;

import de.hhn.it.devtools.apis.exceptions.WrongLoginCredentialsException;
import de.hhn.it.devtools.components.passGen.provider.SimpleUser;
import de.hhn.it.devtools.components.passGen.provider.SimpleUserManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestSimpleUserBadCases {
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
  @DisplayName("Test user retrieval with incorrect ID")
  public void testGetUserWithWrongId() {
    assertThrows(IllegalArgumentException.class, () -> service.getUser(-1));
  }

  @Test
  @DisplayName("Test credential validation with incorrect password")
  public void testCheckCredentialsWithWrongPassword() {
    assertFalse(service.checkCredentials("user@example.com", "wrongPassword"));
  }

  @Test
  @DisplayName("Test adding and retrieving token with null token")
  public void testAddAndRetrieveTokenWithNull() {
    user.addTokenToUser(null);
    assertNull(user.retrieveTokenFromUser());
  }


  @Test
  @DisplayName("Test adding null address")
  public void testAddNullAddress() {
    assertThrows(IllegalArgumentException.class,
        () -> new SimpleUser(null, "password123", 1, true));
  }

  @Test
  @DisplayName("Test adding null password")
  public void testAddNullPassword() {
    assertThrows(IllegalArgumentException.class,
        () -> new SimpleUser("user@example.com", null, 1, true));
  }

  @Test
  @DisplayName("Test change password with null user")
  public void changePasswordWithNullUser() {
    assertThrows(IllegalArgumentException.class,
        () -> service.changePassword(null, "newPassword123"));
  }
}
