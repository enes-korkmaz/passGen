package de.hhn.it.devtools.javafx.passGen.view;

import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;
import de.hhn.it.devtools.apis.passGen.Locker;
import de.hhn.it.devtools.apis.passGen.LockerState;
import de.hhn.it.devtools.javafx.passGen.helper.PassGenScreenController;
import de.hhn.it.devtools.javafx.passGen.viewmodel.LockerViewModel;
import de.hhn.it.devtools.javafx.passGen.viewmodel.MainScreenViewModel;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class represents the User Selection Screen in the PassGen application.
 * It extends javafx.scene.layout.AnchorPane and is responsible for handling user interactions.
 */
public class UserSelectionController extends AnchorPane {
  private static final Logger logger = LoggerFactory.getLogger(UserSelectionController.class);
  public static final String SCREEN_NAME = "UserSelectionScreen";
  private PassGenScreenController controller;
  private MainScreenViewModel mainScreenViewModel;
  private LockerViewModel lockerViewModel;
  private static final LockerState UNLOCKED = LockerState.UNLOCKED;

  @FXML
  private AnchorPane userSelectionPane;
  @FXML
  private Label cabinetDetails;
  @FXML
  private Label cabinetLockerDetails;
  @FXML
  private Label lockerDescription;
  @FXML
  private Label lockerDetails;
  @FXML
  private TextField passcodeField;
  @FXML
  private HBox passcodeAndPaste;
  @FXML
  private Button generatePasscode;
  @FXML
  private Button pasteButton;
  @FXML
  private HBox confirmAndBack;
  @FXML
  private Button confirmPasscode;
  @FXML
  private Button backButton;
  @FXML
  private VBox lockInfoAndButton;
  @FXML
  private Button lockLocker;
  @FXML
  private Label lockInfo;

  /**
   * Constructor for UserSelectionController.
   * Initializes the FXML components and sets up the event handlers.
   *
   * @param controller Reference to the main screen controller.
   */
  public UserSelectionController(PassGenScreenController controller) {
    this.controller = controller;
    this.mainScreenViewModel = controller.getMainScreenViewModel();
    this.lockerViewModel = controller.getLockerViewModel();
    FXMLLoader loader =
            new FXMLLoader(getClass().getResource("/fxml/passGen/UserSelectionScreen.fxml"));
    loader.setController(this);
    try {
      loader.load();
    } catch (IOException e) {
      logger.error("IOException when loading the FXML: " + e.getMessage(), e);
    }

    cabinetLockerDetails.textProperty().bind(mainScreenViewModel.locationProperty());
    lockerDetails.textProperty().bind(mainScreenViewModel.numLockersProperty());
    this.getChildren().add(userSelectionPane);
  }

  @FXML
  private void initialize() {
    // Any additional initialization code can go here
  }

  /**
   * Method to update the passcode field.
   */
  public void update() {
    logger.info("update called");
    passcodeField.clear();
    int lockerId = Integer.parseInt(mainScreenViewModel.getNumLocker());
    Locker locker = lockerViewModel.getLockerVm(lockerId);

    if (locker.getState() == LockerState.UNLOCKED) {
      handleButtonsWhenUnlocked();
    } else {
      logger.info("Enable all fx when but not locker lock button.");
      handleButtonsWhenLocked();
    }
  }

  /**
   * Event handler for the "Generate Passcode" button click.
   * Logs a debug message and generates a passcode.
   */
  @FXML
  public void clickGetPasscode() throws IllegalParameterException {
    logger.debug("Generate Passcode button was clicked");
    int lockerId = Integer.parseInt(mainScreenViewModel.getNumLocker());
    Locker locker = lockerViewModel.getLockerVm(lockerId);
    lockerViewModel.activateLocker(locker);
    lockerViewModel.generatePasscode(locker);
    controller.switchTo("UserSelectionScreen", "PassCodeScreen");
  }

  /**
   * Event handler for the "Confirm Passcode" button click.
   * Logs a debug message and performs further actions (e.g., navigate to main screen or logout).
   */
  @FXML
  public void clickConfirmPasscode() {
    logger.debug("Confirm passcode button was clicked");
    int lockerId = Integer.parseInt(mainScreenViewModel.getNumLocker());
    Locker locker = lockerViewModel.getLockerVm(lockerId);
    try {
      lockerViewModel.unlockLocker(locker, Integer.parseInt(passcodeField.getText()));
    } catch (IllegalParameterException | NumberFormatException e) {
      alertWrongPass();
    }
    if (locker.getState() == UNLOCKED) {
      logger.info("Disabeling buttons other than lock locker.");
      handleButtonsWhenUnlocked();
    } else {
      logger.info("Locker was not unlocked try again.");
    }
  }

  /**
   * This method handles the UI changes when the locker is unlocked.
   * It disables the generate passcode, passcode and paste buttons, and confirm and back buttons.
   * It also hides these buttons and shows the lock information and lock locker button.
   */
  public void handleButtonsWhenUnlocked() {
    generatePasscode.setDisable(true);
    passcodeAndPaste.setDisable(true);
    confirmPasscode.setDisable(true);
    generatePasscode.setVisible(false);
    passcodeAndPaste.setVisible(false);
    confirmPasscode.setVisible(false);

    lockInfoAndButton.setVisible(true);
    lockInfoAndButton.setDisable(false);
  }

  /**
   * This method handles the UI changes when the locker is locked.
   * It disables the generate passcode, passcode and paste buttons, and confirm and back buttons.
   * It also hides these buttons and shows the lock information and lock locker button.
   */
  public void handleButtonsWhenLocked() {
    lockInfoAndButton.setVisible(false);
    lockInfoAndButton.setDisable(true);

    generatePasscode.setDisable(false);
    passcodeAndPaste.setDisable(false);
    confirmPasscode.setDisable(false);
    generatePasscode.setVisible(true);
    passcodeAndPaste.setVisible(true);
    confirmPasscode.setVisible(true);

  }

  /**
   * Event handler for the "Back" button click.
   * Logs a debug message and navigates back to the main screen.
   */
  @FXML
  public void clickBackButton() {
    logger.debug("Back button was clicked");
    controller.switchTo("UserSelectionScreen", "MainScreen");
  }

  /**
   * If a password has been saved to the clipboard, it is inserted into the passcodeField.
   */
  @FXML
  public void pastePasswordButton() {
    Clipboard clipboard = Clipboard.getSystemClipboard();
    if (clipboard.hasString()) {
      passcodeField.setText(clipboard.getString());
    }
  }

  /**
   * Event handler for the "Lock Locker" button click.
   */
  @FXML
  public void lockLockerAfterUsage() {
    logger.info("Lock Locker button was clicked");
    int lockerId = Integer.parseInt(mainScreenViewModel.getNumLocker());
    Locker locker = lockerViewModel.getLockerVm(lockerId);
    try {
      logger.info("Processing locking locker");
      lockerViewModel.lockLocker(locker);
      logger.info("Processing activating locker");
      lockerViewModel.activateLocker(locker);
      logger.info("Processing deactivating locker");
      lockerViewModel.deactivateLocker(locker);
      controller.switchTo("UserSelectionScreen", "MainScreen");
    } catch (IllegalParameterException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * This method is called when a wrong password is entered.
   */
  public void alertWrongPass() {
    logger.info("Showing alert for wrong password");
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.getDialogPane().setHeaderText("The password is incorrect.");
    alert.getDialogPane().setContentText("Try again.");
    alert.showAndWait();
  }
}