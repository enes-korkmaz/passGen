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

import de.hhn.it.devtools.apis.passGen.LockerState;
import de.hhn.it.devtools.components.passGen.provider.SimpleLockerListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSimpleLockerListener {

  private SimpleLockerListener listener;


  @BeforeEach
  public void setup() {
    listener = new SimpleLockerListener();
  }

  @Test
  @DisplayName("Test listener for new State")
  public void testNewState() {
    LockerState lockerState = LockerState.ACTIVE;
    listener.newState(lockerState);
    assertEquals(lockerState, listener.getCurrentState());
  }

  @Test
  @DisplayName("Test listener for new State")
  public void testNewPassord() {
    int password = 1568456359;
    listener.newPassword(password);
    assertEquals(password, listener.getCurrentPassword());
  }
}
