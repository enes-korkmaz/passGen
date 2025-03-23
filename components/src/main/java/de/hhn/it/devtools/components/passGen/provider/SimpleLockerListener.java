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

package de.hhn.it.devtools.components.passGen.provider;

import de.hhn.it.devtools.apis.passGen.LockerListener;
import de.hhn.it.devtools.apis.passGen.LockerState;

/**
 * A simple implementation of the LockerListener interface.
 */
public class SimpleLockerListener implements LockerListener {
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(SimpleLockerListener.class);

  private LockerState currentState;

  private int currentPassword;


  /**
   * Informs the listener that the Locker has changed its state.
   *
   * @param state actual state of the Locker
   */
  public void newState(LockerState state) {
    logger.info("The locker state has changed: {}", state);
    this.currentState = state;
  }

  /**
   * Informs the listener that the password has changed.
   *
   * @param password actual password of the Locker
   */
  public void newPassword(int password) {
    // In Locker is the password a integer.
    // I have no idea what to do.

    logger.info("The locker password has changed.");
    this.currentPassword = password;
  }

  /**
   * Getter for the current state.
   *
   * @return current state for the locker.
   */
  public LockerState getCurrentState() {
    return currentState;
  }

  /**
   * Getter for the current password.
   *
   * @return current password for the locker.
   */
  public int getCurrentPassword() {
    return currentPassword;
  }

}
