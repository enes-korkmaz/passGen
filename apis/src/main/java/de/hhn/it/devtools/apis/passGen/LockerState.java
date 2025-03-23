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
 * Enum to represent the different states a locker can be in.
 */
public enum LockerState {
  /**
   * Locker is opened/unlocked.
   */
  UNLOCKED,

  /**
   * Locker is closed/locked and has to be activated to be able to interact with.
   */
  LOCKED,

  /**
   * Locker is activated but locked (can be unlocked).
   */
  ACTIVE,

  /**
   * Locker is locked and cannot be interacted with (Initial state).
   */
  DEACTIVATED,

  /**
   * Locker is disabled and cannot be activated and interacted with by user.
   */
  DISABLED,

  /**
   * Locker is in usage, active and (unlocked).
   */
  IN_USAGE
}