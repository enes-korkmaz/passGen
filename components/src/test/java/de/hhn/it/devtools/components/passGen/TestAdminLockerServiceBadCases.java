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

import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;
import de.hhn.it.devtools.components.passGen.provider.SimpleAdminLockerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestAdminLockerServiceBadCases {
  private SimpleAdminLockerService service;

  @BeforeEach
  public void setup() {
    service = new SimpleAdminLockerService();
  }

  @Test
  @DisplayName("Test remove nonexistent locker")
  public void testRemoveNonexistentLocker() {
    assertThrows(IllegalParameterException.class, () -> service.removeLocker(-1));
  }

  @Test
  @DisplayName("Test create locker unite with null location")
  public void testRemoveNonexistentLockerUnite() {
    assertThrows(IllegalParameterException.class, () -> service.removeLocker(-1));
  }

  @Test
  @DisplayName("Test create locker unite with null location")
  public void testCreateLockerCabinetWithNullLocation() {
    assertThrows(IllegalArgumentException.class, () -> service.createLockerCabinet(null));
  }

  @Test
  @DisplayName("Test create locker with null locker unite")
  public void testCreateLockerWithNullLockerUnite() {
    assertThrows(IllegalArgumentException.class, () -> service.createLocker(null, "Location A"));
  }

  @Test
  @DisplayName("Test add/remove LockerUnite")
  public void testAddRemoveLockerCabinet() {
    assertThrows(IllegalParameterException.class, () -> service.addLockerToLockerCabint(-1, null));
    assertThrows(IllegalParameterException.class, () -> service.removeLockerCabinet(-1));
    assertThrows(IllegalParameterException.class, () -> service.getLockerCabinet(-1));
  }
}
