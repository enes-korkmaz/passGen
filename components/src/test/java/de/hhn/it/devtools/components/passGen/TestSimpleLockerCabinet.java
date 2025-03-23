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

import de.hhn.it.devtools.apis.passGen.Locker;
import de.hhn.it.devtools.components.passGen.provider.SimpleLocker;
import de.hhn.it.devtools.components.passGen.provider.SimpleLockerCabinet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestSimpleLockerCabinet {

  private SimpleLockerCabinet lockerUnite;
  Map<Integer, Locker> lockerUniteMap = new HashMap<>();

  @BeforeEach
  public void setup() {
    lockerUnite = new SimpleLockerCabinet(10, "Cellar", lockerUniteMap);
  }

  @Test
  @DisplayName("Test sets the location of the locker.")
  public void testSetLocation() {
    lockerUnite.setLocation("Libary");
    assertEquals("Libary", lockerUnite.getLocation());
  }

  @Test
  @DisplayName("Test gets the location of the locker.")
  public void tesGetLocation() {
    assertEquals("Cellar", lockerUnite.getLocation());
  }

  @Test
  @DisplayName("Test for adding a locker.")
  public void testAddLocker() {
    Locker locker = new SimpleLocker(1, lockerUnite, "Cellar");
    lockerUnite.addLocker(1, locker);
    assertEquals(locker, lockerUnite.getLocker(1));
  }

  @Test
  @DisplayName("Test get locker unite.")
  public void testGetLockerCabinet() {
    Locker locker1 = new SimpleLocker(1, lockerUnite, "Cellar");
    Locker locker2 = new SimpleLocker(2, lockerUnite, "Cellar");
    lockerUniteMap.put(1, locker1);
    lockerUniteMap.put(2, locker2);
    assertEquals(2, lockerUniteMap.size());
    Map<Integer, Locker> testMap = lockerUnite.getLockerCabinet();
    assertEquals(locker1, testMap.get(1));
    assertEquals(locker2, testMap.get(2));
  }

  @Test
  @DisplayName("Test get not existing locker id throws exception.")
  public void testGetLockerBadCase() {
    int lockerId = 5;
    assertThrows(IllegalArgumentException.class, () -> lockerUnite.getLocker(lockerId));
  }

}
