package de.hhn.it.devtools.javafx.passGen.view;

import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;
import de.hhn.it.devtools.apis.passGen.Locker;
import de.hhn.it.devtools.apis.passGen.LockerState;
import de.hhn.it.devtools.components.passGen.provider.SimpleLocker;
import de.hhn.it.devtools.components.passGen.provider.SimpleLockerCabinet;
import de.hhn.it.devtools.components.passGen.provider.SimpleLockerService;
import de.hhn.it.devtools.javafx.passGen.helper.PassGenScreenController;
import de.hhn.it.devtools.javafx.passGen.viewmodel.LockerCabinetViewModel;
import de.hhn.it.devtools.javafx.passGen.viewmodel.LockerViewModel;
import de.hhn.it.devtools.javafx.passGen.viewmodel.MainScreenViewModel;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * This class represents the controller for the AdminSelectionScreen.
 * It handles the user interactions and updates the UI based on those interactions.
 */
public class AdminSelectionScreenController extends AnchorPane {

  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(AdminSelectionScreenController.class);

  public static final String SCREEN_NAME = "AdminSelectionScreen";
  PassGenScreenController controller;
  LockerCabinetViewModel cabinetViewModel;
  MainScreenViewModel mainScreenViewModel;
  LockerViewModel lockerViewModel;
  SimpleLockerService simpleLockerService;

  @FXML
  AnchorPane adminSelectionPane;
  @FXML
  Label cabinetDetails;
  @FXML
  Label cabinetLockerDetails;
  @FXML
  Label lockerDescription;
  @FXML
  Label lockerDetails;
  @FXML
  Button confirmConfigurationButton;
  @FXML
  Button backButton;
  @FXML
  Label changeStateLabel;
  @FXML
  ChoiceBox<LockerState> stateChoiceBox;
  @FXML
  Button removeLocker;

  /**
   * Constructor for the AdminSelectionScreenController.
   *
   * @param controller Reference to the main screen controller.
   */
  public AdminSelectionScreenController(PassGenScreenController controller) {
    this.controller = controller;
    this.lockerViewModel = controller.getLockerViewModel();
    this.cabinetViewModel = controller.getLockerCabinetViewModel();
    this.mainScreenViewModel = controller.getMainScreenViewModel();
    FXMLLoader loader =
        new FXMLLoader(getClass().getResource("/fxml/passGen/AdminSelectionScreen.fxml"));
    loader.setController(this);
    try {
      loader.load();
    } catch (IOException e) {
      logger.error("IOException when loading the FXML: " + e.getMessage(), e);
    }

    cabinetLockerDetails.textProperty().bind(mainScreenViewModel.locationProperty());
    lockerDetails.textProperty().bind(mainScreenViewModel.numLockersProperty());
    this.getChildren().add(adminSelectionPane);
  }


  /**
   * Method to initialize the AdminSelectionScreen.
   * It initializes the stateChoiceBox with the available locker states.
   */
  public void initialize() {
    stateChoiceBox.setItems(FXCollections.observableArrayList(LockerState.values()));
    stateChoiceBox.valueProperty().bindBidirectional(lockerViewModel.lockerStateProperty());
  }

  /**
   * Method to handle the click event of the confirm configuration button.
   * It logs a debug message and should set the selected state in stateChoiceBox.
   */
  public void clickConfirmConfiguration() throws IllegalParameterException {
    logger.debug("Confirm configuration button was clicked");
    // should set the selected state in stateChoiceBox - (return it as a parameter to viewModel)
    LockerState selectedState = LockerState.valueOf(stateChoiceBox.getValue().toString());
    String lockerId = mainScreenViewModel.getNumLocker();
    SimpleLocker locker = lockerViewModel.getLockerVm(Integer.parseInt(lockerId));

    switch (selectedState) {
      case LockerState.UNLOCKED:
        lockerViewModel.changeLockerState(Integer.parseInt(lockerId), LockerState.UNLOCKED);
        break;
      case LockerState.LOCKED:
        lockerViewModel.changeLockerState(Integer.parseInt(lockerId), LockerState.LOCKED);
        break;
      case LockerState.ACTIVE:
        lockerViewModel.changeLockerState(Integer.parseInt(lockerId), LockerState.ACTIVE);
        break;
      case LockerState.DEACTIVATED:
        lockerViewModel.changeLockerState(Integer.parseInt(lockerId), LockerState.DEACTIVATED);
        break;
      case LockerState.DISABLED:
        lockerViewModel.changeLockerState(Integer.parseInt(lockerId), LockerState.DISABLED);
        break;
      case LockerState.IN_USAGE:
        lockerViewModel.changeLockerState(Integer.parseInt(lockerId), LockerState.IN_USAGE);
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + selectedState);
    }

    System.out.println(lockerViewModel.getLockerState());

    controller.switchTo("AdminSelectionScreen", "MainScreen");
  }

  /**
   * Method to handle the click event of the back button.
   * It logs a debug message and switches to the main screen.
   */
  public void clickBackButton() {
    logger.debug("Back button was clicked");
    controller.switchTo("AdminSelectionScreen", "MainScreen");
  }

  /**
   * Method to handle the click event of the remove locker button.
   * It logs a debug message and removes the currently selected
   * locker from the currently selected cabinet.
   */
  public void clickRemoveLocker() throws IllegalParameterException {
    logger.debug("Remove locker button was clicked");
    // Retrieve the selected locker ID
    String lockerId = mainScreenViewModel.getNumLocker();
    Locker locker = lockerViewModel.getLockerVm(Integer.parseInt(lockerId));

    // Retrieve the selected locker cabinet from the view model
    SimpleLockerCabinet selectedCabinet = mainScreenViewModel.getSelectedCabinet();

    if (selectedCabinet == null) {
      logger.error("No cabinet selected in the list view");
      return;
    }

    try {
      // Remove locker from the selected cabinet
      cabinetViewModel.removeLockerFromCabinet(selectedCabinet, locker);
    } catch (IllegalParameterException e) {
      throw new RuntimeException(e);
    }
    controller.switchTo("AdminSelectionScreen", "MainScreen");
  }

}
