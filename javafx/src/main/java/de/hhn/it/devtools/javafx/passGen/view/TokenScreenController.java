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

package de.hhn.it.devtools.javafx.passGen.view;

import de.hhn.it.devtools.javafx.passGen.helper.PassGenScreenController;
import de.hhn.it.devtools.javafx.passGen.viewmodel.UserManagementViewModel;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class represents the controller for the TokenScreen.
 * It handles the logic and interactions of the TokenScreen.
 */
public class TokenScreenController extends AnchorPane {
  private static final Logger logger =
          LoggerFactory.getLogger(TokenScreenController.class);
  public static final String SCREEN_NAME = "TokenScreen";
  private PassGenScreenController controller;

  @FXML
  AnchorPane tokenPane;
  @FXML
  Label tokenHeader;
  @FXML
  TextField tokenField;
  @FXML
  Button copyTokenButton;
  @FXML
  Button advanceButton;
  UserManagementViewModel userManagementViewModel;

  /**
   * Constructor for the TokenScreenController.
   *
   * @param controller Reference to the main screen controller.
   */
  public TokenScreenController(PassGenScreenController controller) {
    this.controller = controller;
    userManagementViewModel = controller.getUserManagementViewModel();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/passGen/TokenScreen.fxml"));
    loader.setController(this);
    try {
      loader.load();
    } catch (IOException e) {
      logger.error("IOException when loading the FXML: " + e.getMessage(), e);
    }

    this.getChildren().add(tokenPane);
    tokenField.textProperty().bindBidirectional(userManagementViewModel.tokenProperty());
  }

  /**
   * Method to update the token field.
   * Logs a debug message and sets the token field text to the token property.
   */
  public void update() {
    tokenField.setText(userManagementViewModel.tokenProperty().get());
    logger.debug("TokenScreenController update called. "
            + "TokenField text set to: " + tokenField.getText());
  }

  /**
   * Method to handle the click event of the copy token button.
   * Logs a debug message and should copy the token in the tokenLabel.
   */
  @FXML
  public void clickCopyToken() {
    logger.info("Copy button was clicked");
    String currentText = tokenField.getText();

    if (currentText == null) {
      logger.error("NullPointerException when trying to copy the token ");
    }
    userManagementViewModel.saveCopiedToken(currentText);
  }

  /**
   * Method to handle the click event of the advance button.
   * Logs a debug message and switches to the main screen.
   */
  @FXML
  public void clickAdvance() {
    logger.debug("Advance button was clicked");
    controller.switchTo(SCREEN_NAME, MainScreenController.SCREEN_NAME);
  }
}