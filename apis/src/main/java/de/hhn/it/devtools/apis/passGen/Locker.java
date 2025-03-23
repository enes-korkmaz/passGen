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

package de.hhn.it.devtools.apis.passGen;

import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;
import de.hhn.it.devtools.apis.exceptions.IllegalStateException;
import java.util.List;

/**
 * Interface representing the functionality of Locker.
 */
public interface Locker {
  //<editor-fold desc="Locker Cabinet Stuff">


  //</editor-fold>

  /**
   * Adds a listener to get updates on the state of the locker.
   *
   * @param listener listener to be added
   * @throws IllegalParameterException If the listener is null
   */
  void addCallback(LockerListener listener) throws IllegalParameterException;

  /**
   * Removes a listener.
   *
   * @param listener listener to be removed
   * @throws IllegalParameterException if the listener is null
   */
  void removeCallback(LockerListener listener) throws IllegalParameterException;

  /**
   * Retrieves the listener of the locker.
   *
   * @return The listener of the locker.
   */
  List<LockerListener> getListener();

  //<editor-fold desc="Locker Stuff">

  /**
   * Attempts to unlock the locker with the provided password.
   *
   * @param password The password to unlock the locker.
   * @throws IllegalParameterException If the password is incorrect or null.
   */
  void unlock(int password) throws IllegalParameterException;

  /**
   * Locks the locker, securing its contents.
   *
   * @throws IllegalStateException If the locker is already locked or cannot be locked.
   */
  void lock() throws IllegalStateException;

  /**
   * Checks if the locker is currently locked.
   *
   * @return True if the locker is locked, false otherwise.
   */
  boolean isLocked();

  /**
   * Retrieves the current state of the locker.
   *
   * @return The current state of the locker.
   */
  LockerState getState();

  /**
   * Sets a new password for the locker, replacing the old one.
   *
   * @param newPassword The new password to set.
   * @throws IllegalParameterException If the new password is weak, too short, or null.
   */
  void setPassword(int newPassword) throws IllegalParameterException;

  /**
   * Retrieves the password of the locker.
   *
   * @return The password of the locker.
   */
  int getPassword();

  /**
   * Retrieves the unique identifier for the locker.
   *
   * @return The unique identifier of the locker.
   */
  int getId();

  /**
   * Activates the locker, making it ready for use.
   *
   * @throws IllegalStateException If the locker is already active or cannot be activated.
   */
  void activate() throws IllegalStateException;

  /**
   * Deactivates the locker, preventing further use until reactivated.
   *
   * @throws IllegalStateException If the locker is not active or cannot be deactivated.
   */
  void deactivate() throws IllegalStateException;

  /**
   * Disables the locker, any use.
   *
   * @throws IllegalStateException If the locker is not active or cannot be disabled.
   */
  void disable() throws IllegalStateException;

  /**
   * Checks if the locker is currently activated.
   *
   * @return True if the locker is activated, false otherwise.
   */
  boolean isActivated();
  //</editor-fold>
}
