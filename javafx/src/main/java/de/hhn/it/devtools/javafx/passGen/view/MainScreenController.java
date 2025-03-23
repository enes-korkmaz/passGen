package de.hhn.it.devtools.javafx.passGen.view;

import de.hhn.it.devtools.apis.passGen.LockerCabinet;
import de.hhn.it.devtools.components.passGen.provider.SimpleLocker;
import de.hhn.it.devtools.components.passGen.provider.SimpleLockerCabinet;
import de.hhn.it.devtools.javafx.passGen.helper.PassGenScreenController;
import de.hhn.it.devtools.javafx.passGen.viewmodel.LockerViewModel;
import de.hhn.it.devtools.javafx.passGen.viewmodel.MainScreenViewModel;
import de.hhn.it.devtools.javafx.passGen.viewmodel.UserManagementViewModel;
import java.io.IOException;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Controller for the main screen of the application.
 * Manages the main functionalities like logout,
 * add/delete cabinets, and navigation to other screens.
 */
public class MainScreenController extends AnchorPane {
  private static final org.slf4j.Logger logger =
          org.slf4j.LoggerFactory.getLogger(MainScreenController.class);
  public static final String SCREEN_NAME = "MainScreen";
  private PassGenScreenController controller;
  private final MainScreenViewModel mainViewModel;
  private final LockerViewModel lockerViewModel;
  private final UserManagementViewModel userViewModel;
  private boolean isUser = false;

  @FXML
  HBox fullMainScreen;
  @FXML
  HBox buttonTriplet;
  @FXML
  VBox cabinetListAndButtons;
  @FXML
  VBox allLockerCabinet;
  @FXML
  Label cabinetHeader;
  @FXML
  Label lockerCabinetDetails;
  @FXML
  ListView cabinetList;
  @FXML
  Button logoutButton;
  //only seen by admin
  @FXML
  Button addCabinetButton;
  @FXML
  Button deleteCabinetButton;
  @FXML
  Button addLockerToCabinetButton;
  @FXML
  GridPane lockerCabinetWall;

  /**
   * Constructor for the MainScreenController.
   * Initializes the FXML components and sets up the controller.
   *
   * @param controller The main screen controller.
   */
  public MainScreenController(PassGenScreenController controller) {
    this.controller = controller;
    this.mainViewModel = controller.getMainScreenViewModel();
    this.lockerViewModel = controller.getLockerViewModel();
    this.userViewModel = controller.getUserManagementViewModel();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/passGen/MainScreen.fxml"));
    loader.setController(this);
    try {
      loader.load();
    } catch (IOException e) {
      logger.error("IOException when loading the FXML: " + e.getMessage(), e);
    }

    this.getChildren().add(fullMainScreen);

    cabinetList.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
              if (newSelection != null) {
                mainViewModel.setSelectedCabinet((SimpleLockerCabinet) newSelection);
                updateCabinetWall();
              }
            }
    );

    mainViewModel.setCabinets();
  }

  /**
   * Initializes the main screen.
   * Binds the cabinet list to the view model and sets up the cell factory.
   * Disables the add and delete cabinet buttons if the user is not an admin.
   */
  public void update(boolean checkUser) {
    cabinetList.itemsProperty().bindBidirectional(mainViewModel.cabinetsProperty());
    cabinetList.setCellFactory(param -> new ListCell<LockerCabinet>() {
      @Override
      protected void updateItem(LockerCabinet item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null || item.getLocation() == null) {
          setText(null);
        } else {
          setText("Location: " + item.getLocation() + ", ID: " + item.getCabinetId());
        }
      }
    });

    isUser = checkUser;

    addCabinetButton.setDisable(isUser);
    deleteCabinetButton.setDisable(isUser);
    addLockerToCabinetButton.setDisable(isUser);
    addCabinetButton.setVisible(!isUser);
    deleteCabinetButton.setVisible(!isUser);
    addLockerToCabinetButton.setVisible(!isUser);

    if (!isUser) {
      addCabinetButton.setStyle("-fx-background-color: violet;");
      deleteCabinetButton.setStyle("-fx-background-color: violet;");
      addLockerToCabinetButton.setStyle("-fx-background-color: violet;");
    }
  }

  /**
   * Handles the locker buttons click event.
   * Switches to the user selection screen or the admin selection screen based on the user type.
   *
   * @param event The action event.
   */
  private void handleLockerButtons(ActionEvent event) {
    Button button = (Button) event.getSource();
    String lockerId = button.getText();
    logger.info("Processing request of Locker " + lockerId);
    mainViewModel.setNumLockers(lockerId);
    if (isUser) {
      controller.switchTo("MainScreen", "UserSelectionScreen");
    } else {
      controller.switchTo("MainScreen", "AdminSelectionScreen");
    }
  }

  /**
   * Updates the cabinet wall with the lockers of the selected cabinet.
   */
  public void updateCabinetWall() {
    logger.info("Processing cabinet wall update.");
    SimpleLockerCabinet selectedCabinet = mainViewModel.getSelectedCabinet();
    lockerCabinetDetails.setText("Chose locker from cabinet.");
    if (selectedCabinet == null) {
      logger.error("No cabinet selected in the list view");
      return;
    }

    try {
      lockerCabinetWall.getChildren().clear();
      int cabinId = Objects.requireNonNull(selectedCabinet).getCabinetId();
      String cabinLocation = Objects.requireNonNull(selectedCabinet.getLocation());
      mainViewModel.setCabinetLocation(cabinLocation);
      logger.info("Selected cabinet at {} with ID: {}", cabinLocation, cabinId);
      mainViewModel.setLockers(cabinId);

      logger.info("Number of lockers in selected cabinet: {}",
              mainViewModel.lockersProperty().size());

    } catch (NullPointerException e) {
      logger.error("Cabinet selected is a null reference", e);
    }

    int numRows = 3;
    int numColumns = 6;

    for (int row = 0; row < numRows; row++) {
      for (int col = 0; col < numColumns; col++) {
        Button button = new Button();
        button.setPrefSize(100, 100);
        button.setMaxSize(100, 100);

        SimpleLocker locker = null;
        if (row * numColumns + col < mainViewModel.lockersProperty().size()) {
          locker = mainViewModel.lockersProperty().get(row * numColumns + col);
        }

        if (locker == null) {
          button.setDisable(true);
          button.setStyle("-fx-background-color: #DEDE65C0");
        } else {
          button.setText(String.valueOf(locker.getId()));
          updateLockerButton(locker, button);
        }

        button.setOnAction(this::handleLockerButtons);
        lockerCabinetWall.add(button, col, row);
      }
    }

    lockerCabinetWall.setVisible(true);
    lockerCabinetWall.setDisable(false);
  }

  /**
   * Updates the locker button based on the locker state.
   *
   * @param locker to update the button for.
   * @param button to update.
   */
  public void updateLockerButton(SimpleLocker locker, Button button) {
    switch (locker.getState()) {
      case DEACTIVATED:
        //user can activate, but activation in userSelection
        // - here only selection possible - this is default
        button.setStyle("-fx-background-color: #71c971;");
        button.setDisable(false);
        break;
      case DISABLED:
        //DISABLED - only admin can activate
        button.setDisable(isUser);
        button.setStyle("-fx-background-color: #e68484;");
        break;
      case UNLOCKED:
        //UNLOCKED - user can lock
        button.setStyle("-fx-background-color: #7070f1;");
        button.setDisable(false);
        break;
      case LOCKED:
        //LOCKED - other user already interacts with button/locker
        logger.debug(locker.getState().toString());
        button.setStyle("-fx-background-color: #e070f1;");
        button.setDisable(isUser);
        break;
      default:
        //  (ACTIVE || IN_USAGE)
        // e.g. when other user already interacts with button/locker
        logger.debug(locker.getState().toString());
        button.setDisable(isUser);
        button.setStyle("-fx-background-color: grey;");
    }
  }

  /**
   * Handles the delete cabinet button click event.
   * Deletes the selected cabinet from the list view.
   */
  public void clickDeleteCabinet() {
    logger.info("Delete cabinet button was clicked");
    SimpleLockerCabinet selectedCabinet =
            (SimpleLockerCabinet) cabinetList.getSelectionModel().getSelectedItem();
    if (selectedCabinet == null) {
      logger.error("No cabinet selected in the list view");
    }
    try {
      int cabinId = Objects.requireNonNull(selectedCabinet).getCabinetId();
      String cabinLocation = Objects.requireNonNull(selectedCabinet.getLocation());
      logger.info("Deleted cabinet at {} with ID: {}", cabinLocation, cabinId);
      mainViewModel.deleteCabinet(cabinId);
    } catch (NullPointerException e) {
      logger.error("Trying to delete cabinet with null reference.");
    }
  }

  /**
   * Handles the add cabinet button click event.
   * Switches to the cabinet screen to add a new cabinet.
   */
  public void clickAddCabinet() {
    logger.info("Add cabinet button was clicked");

    controller.switchTo("MainScreen", "CabinetScreen");
  }

  /**
   * Handles the add locker to cabinet button click event.
   * If a cabinet is selected, it adds a new locker to the selected cabinet.
   * If no cabinet is selected, it logs an error message.
   */
  public void clickAddLockerToCabinet() throws InterruptedException {
    logger.info("Add locker to cabinet was clicked");
    addLockerToCabinetButton.setDisable(true);
    SimpleLockerCabinet selectedCabinet =
            (SimpleLockerCabinet) cabinetList.getSelectionModel().getSelectedItem();

    if (selectedCabinet == null) {
      logger.error("No cabinet selected, can not add locker.");
      addLockerToCabinetButton.setDisable(false);
    }

    try {
      int cabinId = Objects.requireNonNull(selectedCabinet).getCabinetId();
      String cabinLocation = Objects.requireNonNull(selectedCabinet.getLocation());
      logger.info("Adding locker to cabinet at {} with ID: {}", cabinLocation, cabinId);
      mainViewModel.addLockerToCabinet(cabinId);
      updateCabinetWall();

      addLockerToCabinetButton.setDisable(false);
    } catch (NullPointerException e) {
      logger.error("Trying to delete cabinet with null reference.");
    }
  }

  /**
   * Handles the logout button click event.
   * Logs out the user and switches to the login screen.
   */
  public void clickLogout() {
    logger.info("Logout button was clicked");
    controller.switchTo("MainScreen", "LoginScreen");
  }
}