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

package de.hhn.it.devtools.javafx.passGen.viewmodel;

import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;
import de.hhn.it.devtools.apis.exceptions.WrongLoginCredentialsException;
import de.hhn.it.devtools.components.passGen.provider.SimpleToken;
import de.hhn.it.devtools.components.passGen.provider.SimpleUser;
import de.hhn.it.devtools.components.passGen.provider.SimpleUserManagementService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

/**
 * A view model for the UserManagement view.
 */
public class UserManagementViewModel {
  private static final org.slf4j.Logger logger =
          org.slf4j.LoggerFactory.getLogger(UserManagementViewModel.class);

  private SimpleUserManagementService userManagementService;

  private BooleanProperty userType = new SimpleBooleanProperty();
  private StringProperty email = new SimpleStringProperty();
  private StringProperty password = new SimpleStringProperty();
  private StringProperty token = new SimpleStringProperty();
  final Clipboard clipboard = Clipboard.getSystemClipboard();
  final ClipboardContent content = new ClipboardContent();

  /**
   * Constructor for UserManagementViewModel.
   * Initializes the userManagementService instance.
   *
   * @param userManagementService an instance of the SimpleUserManagementService
   */
  public UserManagementViewModel(SimpleUserManagementService userManagementService) {
    this.userManagementService = userManagementService;
  }

  /**
   * Logs in a user using email and password.
   *
   * @throws WrongLoginCredentialsException if the login credentials are incorrect
   */
  public void login() throws WrongLoginCredentialsException {
    userManagementService.login(email.get(), password.get());
    for (SimpleUser user : userManagementService.getUsers().values()) {
      if (user.getAddress().equals(email.get()) && user.getPassword().equals(password.get())) {
        userType.set(user.isUser());
        SimpleToken userToken = user.getToken();
        if (userToken != null) {
          setToken(userToken.toString());
          logger.debug("Token generated for user: {} with token: {} and isUser: {}",
                  email.get(), userToken.toString(), user.isUser());
        } else {
          logger.warn("User token is null for user: {}", email.get());
          // Handle the null token case as required
          // at the moment for users without token the token is null
          // token gets generated in that case and renewed every login
          // if login with email was used. If login with token is used
          // the token will expire after 599sec
        }
      }
    }
    logger.info("Login successful for user: {}", email.get());
  }

  /**
   * Placeholder for user registration.
   * This method currently does nothing but could be implemented in the future.
   */
  public void register() {
    //Placeholder now but could be implemented in the future
    //userManagementService.createUser(email.get(), password.get());
  }

  /**
   * Returns a StringProperty for the email field.
   * This property can be used to bind the email field in the view.
   *
   * @return the email property
   */
  public StringProperty emailProperty() {
    return email;
  }

  /**
   * Returns a StringProperty for the password field.
   * This property can be used to bind the password field in the view.
   *
   * @return the password property
   */
  public StringProperty passwordProperty() {
    return password;
  }

  /**
   * Returns a StringProperty for the token field.
   * This property can be used to bind the token field in the view.
   *
   * @return the token property
   */
  public StringProperty tokenProperty() {
    return token;
  }

  public BooleanProperty checkIfUser() {
    return userType;
  }

  /**
   * Logs in a user using a token.
   *
   * @throws IllegalParameterException if the token is incorrect
   */
  public void tokenLogin() throws IllegalParameterException {
    boolean tokenMatch = false;
    for (SimpleUser user : userManagementService.getUsers().values()) {
      SimpleToken userToken = user.getToken();
      if (userToken != null && userToken.toString().equals(token.get())) {
        tokenMatch = true;
        userType.set(user.isUser());
      }
    }
    if (!tokenMatch) {
      logger.error("Token login failed for token: {}", token.get());
      throw new IllegalParameterException("Token is incorrect.");
    }
    logger.info("Token login successful for token: {}", token.get());
  }

  /**
   * Method to save the copied token to the system clipboard.
   *
   * @param tokenFromTextField The token string to be copied.
   */
  public void saveCopiedToken(String tokenFromTextField) {
    content.putString(tokenFromTextField);
    clipboard.setContent(content);
    logger.info("Token was copied to system clipboard successfully");
  }

  public void setToken(String tokenValue) {
    token.set(tokenValue);
  }

}