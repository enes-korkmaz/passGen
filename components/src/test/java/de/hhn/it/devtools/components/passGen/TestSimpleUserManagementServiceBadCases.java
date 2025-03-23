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

import de.hhn.it.devtools.apis.exceptions.WrongLoginCredentialsException;
import de.hhn.it.devtools.components.passGen.provider.SimpleUserManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TestSimpleUserManagementServiceBadCases {
  private SimpleUserManagementService service;
  private int userId;

  @BeforeEach
  void setUp() {
    service = new SimpleUserManagementService();
    userId = service.createUser("user@example.com", "password123", true);
  }

  @Test
  @DisplayName("Test createUser with null arguments")
  void testCreateUserWithNullArguments() {
    assertThrows(IllegalArgumentException.class, () -> service.createUser(null, null, true));
  }

  @Test
  @DisplayName("Test login with wrong credentials")
  void testLoginWithWrongCredentials() {
    assertThrows(WrongLoginCredentialsException.class, () -> service.login("user@example.com", "wrongPassword"));
  }

  @Test
  @DisplayName("Test getUser with incorrect ID")
  void testGetUserWithIncorrectId() {
    assertThrows(IllegalArgumentException.class, () -> service.getUser(-1));
  }

  @Test
  @DisplayName("Test removeUser with invalid ID")
  void testRemoveUserWithInvalidId() {
    assertThrows(IllegalArgumentException.class, () -> service.removeUser(-1));
  }

  @Test
  @DisplayName("Test changePassword with null arguments")
  void testChangePasswordWithNullArguments() {
    assertThrows(IllegalArgumentException.class, () -> service.changePassword(null, null));
  }

  @Test
  @DisplayName("Test checkCredentials with null arguments")
  void testCheckCredentialsWithNullArguments() {
    assertThrows(IllegalArgumentException.class, () -> service.checkCredentials(null, null));
  }
}