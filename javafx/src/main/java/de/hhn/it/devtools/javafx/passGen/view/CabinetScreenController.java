package de.hhn.it.devtools.javafx.passGen.view;

import de.hhn.it.devtools.javafx.passGen.helper.PassGenScreenController;
import de.hhn.it.devtools.javafx.passGen.viewmodel.MainScreenViewModel;
import java.io.IOException;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * This class represents the controller for the CabinetScreen.
 * It handles the user interactions and updates the UI elements.
 */
public class CabinetScreenController extends AnchorPane {

  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(CabinetScreenController.class);
  public static final String SCREEN_NAME = "CabinetScreen";
  PassGenScreenController controller;
  MainScreenViewModel mainViewModel;

  @FXML
  AnchorPane cabinetPane;
  @FXML
  Label instructions;
  @FXML
  TextField locationField;
  @FXML
  TextField lockerQuantityField;
  @FXML
  Button addLockerButton;
  @FXML
  Button backButton;

  /**
   * Constructor for the CabinetScreenController.
   * It initializes the UI elements and sets up the event handlers.
   *
   * @param controller Reference to the main screen controller.
   */
  public CabinetScreenController(PassGenScreenController controller) {
    this.controller = controller;
    mainViewModel = controller.getMainScreenViewModel();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/passGen/CabinetScreen.fxml"));
    loader.setController(this);
    try {
      loader.load();
    } catch (IOException e) {
      logger.error("IOException when loading the FXML: " + e.getMessage(), e);
    }

    this.getChildren().add(cabinetPane);

    locationField.textProperty().bindBidirectional(mainViewModel.locationProperty());
    lockerQuantityField.textProperty().bindBidirectional(mainViewModel.numLockersProperty());
  }

  /**
   * Event handler for the add locker button click.
   * It logs a debug message and adds a new locker using the values from the text fields.
   * It also updates the list view in the main screen.
   */
  public void clickAddLocker() {
    logger.debug("Add locker button was clicked");

    if (checkField(locationField.getText(), lockerQuantityField.getText())) {
      mainViewModel.createCabinet(
          locationField.getText(), Integer.parseInt(lockerQuantityField.getText()));
      locationField.clear();
      lockerQuantityField.clear();
    }
    logger.info("Creation of cabinet failed complete necessary information and try again.");
  }

  /**
   * Checks if a cabinet can be created based on the provided location and quantity.
   *
   * @param location The location of the cabinet.
   * @param quantity The quantity of lockers in the cabinet.
   * @return True if the cabinet can be created, false otherwise.
   */
  public boolean checkField(String location, String quantity) {
    logger.info("Check if cabinet can be created.");

    boolean isValid = true;

    // Validate location
    if (location == null || location.length() < 4) {
      logger.warn("Location specification has to be at least 4 characters long.");
      shakeFieldCabinet(1, 0);
      isValid = false;
    }

    // Validate quantity
    int lockerQuantity = -1;
    try {
      lockerQuantity = Integer.parseInt(quantity);
    } catch (NumberFormatException e) {
      logger.warn("Quantity must be a valid number.");
      shakeFieldCabinet(0, 1);
      isValid = false;
    }

    if (lockerQuantity < 0 || lockerQuantity > 18) {
      logger.warn(
          "Cabinet can't be created with invalid locker quantity. Must be between 0 and 18.");
      shakeFieldCabinet(0, 1);
      isValid = false;
    }

    return isValid;
  }

  /**
   * This method is used to shake the text fields when the input is not valid.
   * It creates a TranslateTransition animation for each text field based on the parameters.
   *
   * @param shake1 An integer representing whether to shake the location field.
   *               A value of 1 indicates shaking, while 0 indicates no shaking.
   * @param shake2 An integer representing whether to shake the locker quantity field.
   *               A value of 1 indicates shaking, while 0 indicates no shaking.
   */
  public void shakeFieldCabinet(int shake1, int shake2) {
    TranslateTransition location = null;
    TranslateTransition quantity = null;
    if (shake1 == 1) {
      location = new TranslateTransition(Duration.millis(50), locationField);
      location.setByX(10);
      location.setCycleCount(4);
      location.setAutoReverse(true);
      location.play();
    }
    if (shake2 == 1) {
      quantity = new TranslateTransition(Duration.millis(50), lockerQuantityField);
      quantity.setByX(10);
      quantity.setCycleCount(4);
      quantity.setAutoReverse(true);
      quantity.play();
    }
  }

  /**
   * Event handler for the back button click.
   * It logs a debug message and switches back to the main screen.
   */
  public void clickBackButton() {
    logger.debug("Back button was clicked");
    locationField.clear();
    lockerQuantityField.clear();
    controller.switchTo(SCREEN_NAME, MainScreenController.SCREEN_NAME);
  }
}
