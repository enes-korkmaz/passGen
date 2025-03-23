/**
 * This file is part of PassGen.
 *
 * Copyright (c) 2025 Enes Korkmaz, Nico Staudacher, Nadine Schoch and Nazanin Golalizadeh
 *
 * PassGen is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License Version 3 as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

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
