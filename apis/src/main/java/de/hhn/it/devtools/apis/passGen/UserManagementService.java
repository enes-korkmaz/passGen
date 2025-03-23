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

import de.hhn.it.devtools.apis.exceptions.WrongLoginCredentialsException;
import java.util.HashMap;

/**
 * This interface provides methods for user management.
 */
public interface UserManagementService {

  /**
   * Logs in the user with the specified E-Mail-Address and passcode.
   * Creates a Token for the user and adds it to the list of logged in users.
   * [!!!Suggestion- ToManyTriesException - Nice-to-have, if one has tired to log in numerous times
   * he will be meet with a message to try later. He won't be able to log in for some time.]
   *
   * @param address  E-Mail-Address
   * @param password key to log in with specified address
   * @throws IllegalArgumentException       if either the address or password is a null reference
   * @throws WrongLoginCredentialsException if the credentials do not match
   */
  void login(String address, String password) throws IllegalArgumentException,
          WrongLoginCredentialsException;


  /**
   * Returns the user with the specified Token.
   *
   * @param userId Id of the user
   * @return the user with the specified Token
   * @throws IllegalArgumentException if the Token is null or the user is not registered
   */
  User getUser(int userId) throws IllegalArgumentException;

  /**
   * Removes the user with the specified token.
   *
   * @param userId Id of the user to be removed
   * @throws IllegalArgumentException if the token is null or the user is not registered
   */
  void removeUser(int userId) throws IllegalArgumentException;

  /**
   * Changes the password for the user with the specified E-Mail-Address.
   *
   * @param address     E-Mail-Address
   * @param newPassword new password to be set
   * @throws IllegalArgumentException if either the address or newPassword is null,
   *                                  or the user is not registered
   */
  void changePassword(String address, String newPassword) throws IllegalArgumentException;

  /**
   * Checks whether the credentials are registered or not.
   * (Does not differentiate if not registered or the password not matching)
   *
   * @param address  E-Mail-Address
   * @param password key to log in with specified address
   * @return boolean value
   */
  boolean checkCredentials(String address, String password);
}
