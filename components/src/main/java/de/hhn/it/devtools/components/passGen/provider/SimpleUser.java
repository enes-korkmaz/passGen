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

import de.hhn.it.devtools.apis.passGen.User;

/**
 * A simple implementation of the User interface.
 */
public class SimpleUser implements User {

  private int id;
  private boolean isUser;
  private String address;
  private String password;
  private SimpleToken token; // Verwende SimpleToken statt String
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(SimpleUser.class);

  /**
   * Creates a new SimpleUser with the specified address and password.
   *
   * @param userAddress  the address of the user
   * @param userPassword the password of the user
   */
  public SimpleUser(String userAddress, String userPassword, int id, boolean isUser) {
    logger.debug("Creating new user with address: {} and password: {} and id: {} and isUser: {}",
        userAddress, userPassword, id, isUser);
    this.isUser = isUser;
    if (userAddress == null || userAddress.isEmpty()) {
      throw new IllegalArgumentException("Address cannot be null or empty");
    }
    if (userPassword == null || userPassword.isEmpty()) {
      throw new IllegalArgumentException("Password cannot be null or empty");
    }
    this.address = userAddress;
    this.password = userPassword;
    this.id = id;
  }

  /**
   * Getter for the adress.
   *
   * @return address
   */
  public String getAddress() {
    return address;
  }

  /**
   * Getter for the password.
   *
   * @return password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Setter of the tocken.
   */
  public void setToken(SimpleToken token) {
    this.token = token;
  }

  /**
   * Getter for the tockem.
   *
   * @return tocken
   */
  public SimpleToken getToken() { // Getter für SimpleToken
    return token;
  }

  /**
   * Check if the user is the right one.
   *
   * @return true if the user is the right one
   */
  public boolean isUser() {
    return isUser;
  }

  /**
   * Setter fot the  password.
   *
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  // Methode, um ein Token zu einem User hinzuzufügen
  public void addTokenToUser(SimpleToken token) {
    this.token = token;
  }

  /**
   * Removes the token from the user.
   */
  public void removeTokenFromUser() {
    logger.debug("Removing token from user");
    this.token = null;
  }

  // Methode, um das Token eines Users zu erhalten
  public SimpleToken retrieveTokenFromUser() {
    return this.token;
  }
}
