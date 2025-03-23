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

/**
 * Callback interface to notify observers about changes in a locker's state or password.
 */
public interface LockerListener {

  /**
   * Informs the listener that the locker has changed its state.
   *
   * @param state The current state of the locker.
   */
  void newState(LockerState state);

  /**
   * Informs the listener that the locker's password has been updated.
   *
   * @param password The new password of the locker.
   */
  void newPassword(int password);
}
