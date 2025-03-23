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

package de.hhn.it.devtools.javafx.controllers;

import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;
import de.hhn.it.devtools.apis.exceptions.WrongLoginCredentialsException;
import de.hhn.it.devtools.apis.passGen.AdminLockerService;
import de.hhn.it.devtools.apis.passGen.Locker;
import de.hhn.it.devtools.apis.passGen.LockerState;
import de.hhn.it.devtools.components.passGen.provider.SimpleAdminLockerService;
import de.hhn.it.devtools.components.passGen.provider.SimpleLockerService;
import de.hhn.it.devtools.components.passGen.provider.SimpleUserManagementService;
import de.hhn.it.devtools.components.passGen.provider.SingletonLockerRepository;
import de.hhn.it.devtools.javafx.passGen.helper.PassGenScreenController;
import de.hhn.it.devtools.javafx.passGen.viewmodel.LockerCabinetViewModel;
import de.hhn.it.devtools.javafx.passGen.viewmodel.LockerViewModel;
import de.hhn.it.devtools.javafx.passGen.viewmodel.MainScreenViewModel;
import de.hhn.it.devtools.javafx.passGen.viewmodel.UserManagementViewModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class represents the controller for the PassGenService application.
 * It handles user authentication and navigation between screens.
 */
public class PassGenServiceController extends Controller implements Initializable {

  public static final String SCREEN_NAME = "LoginScreen";
  private static final Logger logger = LoggerFactory.getLogger(PassGenServiceController.class);

  private final AdminLockerService adminService;
  private final SimpleLockerService lockerService;
  private final SimpleUserManagementService userManagementService;
  private LockerCabinetViewModel lockerCabinetViewModel;
  private MainScreenViewModel mainScreenViewModel;
  private LockerViewModel lockerViewModel;
  private UserManagementViewModel userManagementViewModel;
  private SingletonLockerRepository singleton;
  private PassGenScreenController controller;

  @FXML
  private AnchorPane mainPane;
  @FXML
  private TextField tokenTextField;
  @FXML
  private TextField emailTextField;
  @FXML
  private PasswordField passwordTextField;
  @FXML
  private Button loginButton;
  @FXML
  private Button registerButton;
  @FXML
  Label loginLabel;

  private boolean isUser = false;

  /**
   * Constructor for PassGenServiceController.
   * Initializes the adminService, lockerService, and userManagementService instances.
   */
  public PassGenServiceController() {
    adminService = new SimpleAdminLockerService();
    lockerService = new SimpleLockerService();
    userManagementService = new SimpleUserManagementService();
    singleton = SingletonLockerRepository.getInstance();
    logger.info("Creating PassGenServiceController");

    createDemoData();
    createVms();
  }

  /**
   * Updates the view.
   */
  public void update() {
    logger.info("Updating PassGenServiceController");
    mainPane.getChildren().clear();
    mainPane.getChildren().addAll(loginButton, registerButton,
        emailTextField, passwordTextField, tokenTextField, loginLabel);
    userManagementViewModel.emailProperty().set("");
    userManagementViewModel.passwordProperty().set("");
    userManagementViewModel.tokenProperty().set("");
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    logger.info("Initializing LockerServiceController");
    controller = new PassGenScreenController(mainPane, this,
        mainScreenViewModel, lockerViewModel, lockerCabinetViewModel,
        userManagementViewModel);
    passwordTextField.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
      if (event.getCode().toString().equals("ENTER")) {
        clickLoginButton();
      }
    });
    tokenTextField.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
      if (event.getCode().toString().equals("ENTER")) {
        clickLoginButton();
      }
    });
    logger.debug("Binding text fields");
    emailTextField.textProperty().bindBidirectional(userManagementViewModel.emailProperty());
    passwordTextField.textProperty().bindBidirectional(userManagementViewModel.passwordProperty());
    tokenTextField.textProperty().bindBidirectional(userManagementViewModel.tokenProperty());
  }


  /**
   * Creates view models.
   */
  public void createVms() {
    logger.info("Creating view models");
    mainScreenViewModel = new MainScreenViewModel(singleton, adminService, userManagementService);
    lockerCabinetViewModel = new LockerCabinetViewModel(adminService, singleton);
    lockerViewModel = new LockerViewModel(adminService, lockerService);
    userManagementViewModel = new UserManagementViewModel(userManagementService);
  }

  /**
   * Creates sample users and cabinets with lockers for testing purposes.
   */
  public void createDemoData() {
    logger.info("Creating users");
    try {
      userManagementService.createUser("user1", "password1", true);
      userManagementService.createUser("user2", "password2", true);
      userManagementService.createUser("user3", "password3", true);
      userManagementService.createUser("user4", "password4", true);
      userManagementService.createUser("user5", "password5", true);
      userManagementService.createUser("admin", "admin", false);
    } catch (Exception e) {
      logger.error("Error creating users", e);
    }

    logger.info("Creating cabinets and adding lockers to them");
    try {
      int cabinetId1 = adminService.createLockerCabinet("A202");
      int cabinetId2 = adminService.createLockerCabinet("A404");

      int lockerId1 = adminService.createLocker(adminService.getLockerCabinet(cabinetId1), "A202");
      int lockerId2 = adminService.createLocker(adminService.getLockerCabinet(cabinetId1), "A202");
      int lockerId3 = adminService.createLocker(adminService.getLockerCabinet(cabinetId1), "A202");
      int lockerId4 = adminService.createLocker(adminService.getLockerCabinet(cabinetId1), "A202");
      int lockerId5 = adminService.createLocker(adminService.getLockerCabinet(cabinetId2), "A404");
      int lockerId6 = adminService.createLocker(adminService.getLockerCabinet(cabinetId2), "A404");
      int lockerId7 = adminService.createLocker(adminService.getLockerCabinet(cabinetId2), "A404");

      try {
        for (Locker locker : singleton.getLockers().values()) {
          if (locker.getId() <= 4) {
            adminService.addLockerToLockerCabint(cabinetId1, locker);
          } else if (locker.getId() > 4) {
            adminService.addLockerToLockerCabint(cabinetId2, locker);
          }
        }
      } catch (IllegalParameterException e) {
        logger.error("Error adding lockers to cabinets", e);
      }

      adminService.setLockerState(lockerId1, LockerState.DISABLED);
      adminService.setLockerState(lockerId2, LockerState.DEACTIVATED);
      adminService.setLockerState(lockerId3, LockerState.UNLOCKED);
      adminService.setLockerState(lockerId4, LockerState.LOCKED);
      adminService.setLockerState(lockerId5, LockerState.UNLOCKED);
      adminService.setLockerState(lockerId6, LockerState.DISABLED);
      adminService.setLockerState(lockerId7, LockerState.DEACTIVATED);

    } catch (IllegalParameterException | RuntimeException e) {
      logger.error("Error creating cabinets and locker", e);
    }
  }

  /**
   * Returns the main pane of the application.
   *
   * @return The main pane of the application.
   */
  public AnchorPane getMainPane() {
    return mainPane;
  }

  /**
   * This method is called when the login button is pressed.
   * It currently logs a message and switches to the main screen without performing
   * any actual login.
   * This is a placeholder function and should be replaced with actual login logic.
   */
  public void clickLoginButton() {
    logger.info("Login button pressed - attempting to authenticate");

    try {
      if (isTokenPresent()) {
        userManagementViewModel.tokenLogin();
        controller.switchTo("LoginScreen", "MainScreen");
        logger.info("Token authentication successful - navigating to MainScreen");
      } else if (areCredentialsPresent()) {
        userManagementViewModel.login();
        navigateBasedOnUserRole();
      } else {
        logger.error(
            "Authentication failed: Both token and login credentials are not provided or invalid");
        alertWrongPass();
      }
    } catch (WrongLoginCredentialsException e) {
      logger.error("Login error: Wrong credentials", e);
      shakeFieldLogin(0, 1, 1);
      alertWrongPass();
    } catch (IllegalParameterException e) {
      logger.error("Login error: Illegal token", e);
      shakeFieldLogin(1, 0, 0);
      alertWrongTocken();

    }
  }

  private boolean isTokenPresent() {
    return userManagementViewModel.tokenProperty().get() != null
        && !userManagementViewModel.tokenProperty().get().isEmpty();
  }

  private boolean areCredentialsPresent() {
    return userManagementViewModel.emailProperty().get() != null
        && !userManagementViewModel.emailProperty().get().isEmpty()
        && userManagementViewModel.passwordProperty().get() != null
        && !userManagementViewModel.passwordProperty().get().isEmpty();
  }

  private void navigateBasedOnUserRole() {
    boolean isUser = userManagementViewModel.checkIfUser().get();
    if (isUser) {
      logger.info("Continuing as a user.");
      controller.switchTo("LoginScreen", "TokenScreen");
      logger.info("Email and password authentication successful - navigating to TokenScreen");
    } else {
      logger.info("Continuing as an admin.");
      controller.switchTo("LoginScreen", "MainScreen");
      logger.info(
          "Email and password authentication successful - navigating to (admin) MainScreen");
    }
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
  public void shakeFieldLogin(int shake1, int shake2, int shake3) {
    TranslateTransition token = null;
    TranslateTransition password = null;
    TranslateTransition email = null;
    if (shake1 == 1) {
      token = new TranslateTransition(Duration.millis(50), tokenTextField);
      token.setByX(10);
      token.setCycleCount(4);
      token.setAutoReverse(true);
      token.play();
    }
    if (shake2 == 1) {
      password = new TranslateTransition(Duration.millis(50), passwordTextField);
      password.setByX(10);
      password.setCycleCount(4);
      password.setAutoReverse(true);
      password.play();
    }
    if (shake3 == 1) {
      email = new TranslateTransition(Duration.millis(50), emailTextField);
      email.setByX(10);
      email.setCycleCount(4);
      email.setAutoReverse(true);
      email.play();
    }
  }

  /**
   * This method is called when a wrong password is entered.
   */
  public void alertWrongPass() {
    logger.info("Showing alert for wrong password");
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.getDialogPane().setHeaderText("The password or email is incorrect.");
    alert.getDialogPane().setContentText("Try again.");
    alert.showAndWait();
  }

  /**
   * This method is called when a wrong tocken is entered.
   */
  public void alertWrongTocken() {
    logger.info("Showing alert for wrong tocken");
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.getDialogPane().setHeaderText("The tocken is incorrect.");
    alert.getDialogPane().setContentText("Try again.");
    alert.showAndWait();
  }
}