package de.hhn.it.devtools.components.passGen;

import de.hhn.it.devtools.components.passGen.provider.SimpleUserManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestSimpleUserManagementServiceGoodCases {

  private SimpleUserManagementService service;
  private int userId;

  @BeforeEach
  void setUp() {
    service = new SimpleUserManagementService();
    userId = service.createUser("user@example.com", "password123", true);
  }

  @Test
  @DisplayName("Test createUser with valid arguments")
  void testCreateUserWithValidArguments() {
    int newUserId = service.createUser("newuser@example.com", "newPassword123", true);
    assertNotNull(service.getUser(newUserId));
  }

  @Test
  @DisplayName("Test login with correct credentials")
  void testLoginWithCorrectCredentials() {
    assertDoesNotThrow(() -> service.login("user@example.com", "password123"));
  }

  @Test
  @DisplayName("Test getUser with correct ID")
  void testGetUserWithCorrectId() {
    assertNotNull(service.getUser(userId));
  }

  @Test
  @DisplayName("Test removeUser with valid ID")
  void testRemoveUserWithValidId() {
    service.removeUser(userId);
    assertThrows(IllegalArgumentException.class, () -> service.getUser(userId));
  }


  @Test
  @DisplayName("Test checkCredentials with correct credentials")
  void testCheckCredentialsWithCorrectCredentials() {
    assertTrue(service.checkCredentials("user@example.com", "password123"));
  }

  @Test
  @DisplayName("Test getUserByAddress with correct address")
  void testGetUserByAddressWithCorrectAddress() {
    assertNotNull(service.getUserByAddress("user@example.com"));
  }
}
