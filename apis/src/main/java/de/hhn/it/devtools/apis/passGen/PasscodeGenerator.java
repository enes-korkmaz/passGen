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

/**
 * Interface for generating passcodes.
 */
public interface PasscodeGenerator {

  /**
   * The default length of the passcode.
   */
  int DEFAULT_LENGTH = 6;

  /**
   * Creates a passcode with the given length and passes it to the specified locker.
   * Its length is determined by the default length.
   *
   * @return passcode is passed to the specified locker
   * @throws IllegalParameterException if the length is not within the allowed range
   */
  int createPasscode() throws IllegalParameterException;

}