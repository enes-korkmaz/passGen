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

import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;
import de.hhn.it.devtools.apis.passGen.Locker;
import de.hhn.it.devtools.components.passGen.provider.SimpleLockerService;
import de.hhn.it.devtools.javafx.passGen.helper.PassGenScreenController;
import de.hhn.it.devtools.javafx.passGen.viewmodel.LockerViewModel;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;

/**
 * Controller for the PassCodeScreen.
 * This class is responsible for handling user interactions and updating the UI.
 */
public class PassCodeScreenController extends AnchorPane {
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(PassCodeScreenController.class);
  public static final String SCREEN_NAME = "PassCodeScreen";
  PassGenScreenController controller;
  private SimpleLockerService lockerService;
  private LockerViewModel lockerViewModel;
  private Locker locker;

  int password;

  @FXML
  AnchorPane passCodePane;
  @FXML
  Label passwordHeadline;
  @FXML
  Label passcodeInstructionLabel;
  @FXML
  TextField newPasscodeField;
  @FXML
  Button copyPasscodeButton;
  @FXML
  Button returnButton;

  /**
   * Constructor for PassCodeScreenController.
   * Initializes the FXML components and sets up event handlers.
   *
   * @param controller Reference to the main controller.
   */
  public PassCodeScreenController(PassGenScreenController controller) {
    this.controller = controller;
    lockerViewModel = controller.getLockerViewModel();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/passGen/PassCodeScreen.fxml"));
    loader.setController(this);
    try {
      loader.load();
    } catch (IOException e) {
      logger.error("IOException when loading the FXML: " + e.getMessage(), e);
    }

    this.getChildren().add(passCodePane);
    newPasscodeField.textProperty().bind(lockerViewModel.passcodeProperty());
  }

  public void update() {
    logger.debug("PassCodeScreenController update called. PasscodeField text set to: "
            + newPasscodeField.getText());
  }

  /**
   * Event handler for the copy passcode button click.
   * Logs a debug message and should copy the generated passcode from the newPasscodeLabel.
   */
  @FXML
  public void clickCopyPasscode() {
    logger.debug("Copy passcode button was clicked");
    Clipboard clipboard = Clipboard.getSystemClipboard();
    ClipboardContent content = new ClipboardContent();
    content.putString(newPasscodeField.getText());
    clipboard.setContent(content);
  }

  /**
   * Event handler for the advance button click.
   * Logs a debug message and switches to the UserSelectionScreen.
   */
  @FXML
  public void clickReturn() {
    logger.debug("Advance button was clicked");
    //try to activate this button after the newPasscode was copied
    controller.switchTo("PassCodeScreen", "UserSelectionScreen");
  }
}
