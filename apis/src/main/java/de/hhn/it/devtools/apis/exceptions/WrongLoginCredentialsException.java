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

package de.hhn.it.devtools.apis.exceptions;

/**
 * Gets thrown when the user provides an address that isn't registered or the password does
 * not match the given address.
 */
public class WrongLoginCredentialsException extends Exception {
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(WrongLoginCredentialsException.class);

  /**
   * Instantiates a new Wrong login credentials exception.
   *
   * @param message the message
   */
  public WrongLoginCredentialsException(final String message) {
    super(message);
  }
}