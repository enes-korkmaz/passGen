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

import de.hhn.it.devtools.apis.exceptions.TooManyTokensException;

/**
 * Interface for generating and removing tokens.
 */
public interface Token {

  /**
   * Removes the token associated with the specified user ID.
   *
   * @param userId the ID of the user whose token is to be removed
   */
  public void removeToken(int userId);

  /**
   * Generates a new token for the specified user ID.
   *
   * @param userId the ID of the user for whom the token is to be generated
   * @throws IllegalArgumentException if the user ID is less than or equal to zero
   * @throws TooManyTokensException   if the user has already an active token assigned
   */
  void generateToken(int userId) throws IllegalArgumentException, TooManyTokensException;
}