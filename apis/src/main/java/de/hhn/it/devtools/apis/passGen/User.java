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
 * The interface User. (This class is a dummy at the moment - it may not be in use upon completion.)
 */
public interface User {

  /**
   * Getter for the property address for a specific user.
   *
   * @return the e-mail-address of the specified user.
   */
  String getAddress();

  /**
   * Getter for the property password for a specific user.
   *
   * @return the password of the specified user.
   */
  String getPassword();
}