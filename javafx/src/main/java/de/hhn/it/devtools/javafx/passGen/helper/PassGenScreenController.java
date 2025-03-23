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

package de.hhn.it.devtools.javafx.passGen.helper;

import de.hhn.it.devtools.javafx.controllers.PassGenServiceController;
import de.hhn.it.devtools.javafx.controllers.template.UnknownTransitionException;
import de.hhn.it.devtools.javafx.passGen.view.AdminSelectionScreenController;
import de.hhn.it.devtools.javafx.passGen.view.CabinetScreenController;
import de.hhn.it.devtools.javafx.passGen.view.MainScreenController;
import de.hhn.it.devtools.javafx.passGen.view.PassCodeScreenController;
import de.hhn.it.devtools.javafx.passGen.view.TokenScreenController;
import de.hhn.it.devtools.javafx.passGen.view.UserSelectionController;
import de.hhn.it.devtools.javafx.passGen.viewmodel.LockerCabinetViewModel;
import de.hhn.it.devtools.javafx.passGen.viewmodel.LockerViewModel;
import de.hhn.it.devtools.javafx.passGen.viewmodel.MainScreenViewModel;
import de.hhn.it.devtools.javafx.passGen.viewmodel.UserManagementViewModel;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class manages the different screens of the application.
 * It initializes all the controllers and provides methods to switch between screens.
 */
public class PassGenScreenController {

  private static final Logger logger = LoggerFactory.getLogger(PassGenScreenController.class);

  private AnchorPane pane;
  private PassGenServiceController loginScreenController;
  private TokenScreenController tokenScreenController;
  private MainScreenController mainScreenController;
  private CabinetScreenController cabinetScreenController;
  private AdminSelectionScreenController adminSelectionScreenController;
  private UserSelectionController userSelectionController;
  private PassCodeScreenController passCodeScreenController;
  private MainScreenViewModel mainScreenViewModel;
  private LockerViewModel lockerViewModel;
  private LockerCabinetViewModel lockerCabinetViewModel;
  private UserManagementViewModel userManagementViewModel;

  /**
   * Constructor for PassGenScreenController.
   * Initializes the controllers and logs an info message.
   *
   * @param pane                  The main pane where the screens are displayed.
   * @param loginScreenController The controller for the login screen.
   */
  public PassGenScreenController(AnchorPane pane, PassGenServiceController loginScreenController,
                                 MainScreenViewModel mainScreenViewModel,
                                 LockerViewModel lockerViewModel,
                                 LockerCabinetViewModel lockerCabinetViewModel,
                                 UserManagementViewModel userManagementViewModel) {
    this.pane = pane;
    this.mainScreenViewModel = mainScreenViewModel;
    this.lockerViewModel = lockerViewModel;
    this.lockerCabinetViewModel = lockerCabinetViewModel;
    this.userManagementViewModel = userManagementViewModel;
    this.loginScreenController = loginScreenController;
    logger.info("PassGenScreenController initialized with all screens created at startup");
    initalizeControllers();
  }

  public LockerViewModel getLockerViewModel() {
    return lockerViewModel;
  }

  public LockerCabinetViewModel getLockerCabinetViewModel() {
    return lockerCabinetViewModel;
  }

  public UserManagementViewModel getUserManagementViewModel() {
    return userManagementViewModel;
  }

  public MainScreenViewModel getMainScreenViewModel() {
    return mainScreenViewModel;
  }

  /**
   * Initializes all the controllers.
   */
  public void initalizeControllers() {
    tokenScreenController = new TokenScreenController(this);
    mainScreenController = new MainScreenController(this);
    cabinetScreenController = new CabinetScreenController(this);
    adminSelectionScreenController = new AdminSelectionScreenController(this);
    userSelectionController = new UserSelectionController(this);
    passCodeScreenController = new PassCodeScreenController(this);
  }

  /**
   * Switches to a different screen.
   * Clears the main pane, adds the new screen's pane, and logs the transition.
   *
   * @param fromScreen The name of the screen being left.
   * @param toScreen   The name of the screen being entered.
   * @throws UnknownTransitionException If the toScreen is not recognized.
   */
  public void switchTo(String fromScreen, String toScreen) {
    logger.info("Switching from {} to {}", fromScreen, toScreen);
    switch (toScreen) {
      case MainScreenController.SCREEN_NAME:
        pane.getChildren().clear();
        boolean isUser = userManagementViewModel.checkIfUser().get();
        mainScreenController.update(isUser);
        mainScreenController.updateCabinetWall();
        pane.getChildren().add(mainScreenController);
        break;
      case PassGenServiceController.SCREEN_NAME:
        loginScreenController.update();
        break;
      case TokenScreenController.SCREEN_NAME:
        pane.getChildren().clear();
        tokenScreenController.update();
        pane.getChildren().add(tokenScreenController);
        break;
      case CabinetScreenController.SCREEN_NAME:
        pane.getChildren().clear();
        pane.getChildren().add(cabinetScreenController);
        break;
      case AdminSelectionScreenController.SCREEN_NAME:
        pane.getChildren().clear();
        pane.getChildren().add(adminSelectionScreenController);
        break;
      case UserSelectionController.SCREEN_NAME:
        pane.getChildren().clear();
        userSelectionController.update();
        pane.getChildren().add(userSelectionController);
        break;
      case PassCodeScreenController.SCREEN_NAME:
        pane.getChildren().clear();
        passCodeScreenController.update();
        pane.getChildren().add(passCodeScreenController);
        break;
      default:
        logger.error("Attempted to switch to an unknown screen: {}", toScreen);
        throw new UnknownTransitionException("Unknown screen: " + toScreen);
    }

    logger.info("Screen switch to {} was successful.", toScreen);
  }

}
