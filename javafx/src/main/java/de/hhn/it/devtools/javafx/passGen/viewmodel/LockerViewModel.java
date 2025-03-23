package de.hhn.it.devtools.javafx.passGen.viewmodel;

import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;
import de.hhn.it.devtools.apis.passGen.AdminLockerService;
import de.hhn.it.devtools.apis.passGen.Locker;
import de.hhn.it.devtools.apis.passGen.LockerState;
import de.hhn.it.devtools.components.passGen.provider.SimpleLocker;
import de.hhn.it.devtools.components.passGen.provider.SimpleLockerService;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

/**
 * A view model for the Locker view.
 */
public class LockerViewModel {
  private static final org.slf4j.Logger logger =
          org.slf4j.LoggerFactory.getLogger(LockerViewModel.class);

  private AdminLockerService adminService;
  private SimpleLockerService lockerService;
  private ObjectProperty<LockerState> lockerState = new SimpleObjectProperty<>();
  private StringProperty passcode = new SimpleStringProperty();
  private StringProperty cabinetLockerDetails = new SimpleStringProperty();
  private final Clipboard clipboard = Clipboard.getSystemClipboard();
  private final ClipboardContent content = new ClipboardContent();
  private ObjectProperty<Integer> lockerId = new SimpleObjectProperty<>();

  /**
   * Constructor for LockerViewModel.
   * Initializes the adminService and lockerService instances.
   *
   * @param adminService  an instance of the AdminLockerService
   * @param lockerService an instance of the SimpleLockerService
   */
  public LockerViewModel(AdminLockerService adminService, SimpleLockerService lockerService) {
    this.adminService = adminService;
    this.lockerService = lockerService;
    logger.info("LockerViewModel initialized");
  }

  /**
   * Changes the state of the given locker.
   *
   * @param lockerId the id of the locker to change the state of.
   * @param state    the new state of the locker.
   * @throws IllegalParameterException if the locker is not in a valid state.
   */
  public void changeLockerState(int lockerId, LockerState state) throws IllegalParameterException {
    adminService.setLockerState(lockerId, state);
    lockerState.set(state);
  }

  /**
   * Returns the locker with the given id.
   *
   * @param lockerId the id of the locker to get.
   * @return the locker with the given id.
   */
  public SimpleLocker getLockerVm(int lockerId) {
    try {
      Locker locker = lockerService.getLocker(lockerId);
      return (SimpleLocker) locker;
    } catch (IllegalParameterException e) {
      throw new RuntimeException(e);
    }
  }


  /**
   * Returns the lockerState property.
   *
   * @return the lockerState property.
   */
  public ObjectProperty<LockerState> lockerStateProperty() {
    return lockerState;
  }

  /**
   * Returns the current state of the locker.
   *
   * @return the current state of the locker.
   */
  public LockerState getLockerState() {
    return lockerState.get();
  }

  /**
   * Adds a new locker to the repository.
   *
   * @param locker to add.
   * @throws IllegalParameterException if the locker is not in a valid state.
   */
  public void removeLocker(Locker locker) throws IllegalParameterException {
    int lockerId = locker.getId();
    adminService.removeLocker(lockerId);
    logger.debug("Locker removed: {}", lockerId);
  }

  /**
   * Creates a new passcode for the given locker.
   *
   * @param locker for which a passcode should be created.
   * @throws IllegalParameterException if the locker is not in a valid state.
   */
  public void createPassCode(Locker locker) throws IllegalParameterException {
    int lockerId = locker.getId();
    lockerService.createLockerPassword(lockerId);
    passcode.set(String.valueOf(locker.getPassword()));
  }

  /**
   * Unlocks the given locker with the given password.
   *
   * @param locker   the locker to unlock.
   * @param password the password to unlock the locker.
   * @throws IllegalParameterException if the locker is not in a valid state.
   */
  public void unlockLocker(Locker locker, int password) throws IllegalParameterException {
    int lockerId = locker.getId();
    lockerService.unlockLocker(lockerId, password);
  }

  /**
   * Locks the given locker.
   *
   * @param locker to lock.
   * @throws IllegalParameterException if the locker is not in a valid state.
   */
  public void lockLocker(Locker locker) throws IllegalParameterException {
    int lockerId = locker.getId();
    lockerService.lockLocker(lockerId);
  }

  /**
   * Activates the given locker.
   *
   * @param locker to activate.
   * @throws IllegalParameterException if the locker is not in a valid state.
   */
  public void activateLocker(Locker locker) throws IllegalParameterException {
    int lockerId = locker.getId();
    lockerService.activateLocker(lockerId);
  }

  /**
   * Deactivates the given locker.
   *
   * @param locker to deactivate.
   * @throws IllegalParameterException if the locker is not in a valid state.
   */
  public void deactivateLocker(Locker locker) throws IllegalParameterException {
    int lockerId = locker.getId();
    lockerService.deactivateLocker(lockerId);
  }

  /**
   * Disables the given locker.
   *
   * @param locker to disable.
   * @throws IllegalParameterException if the locker is not in a valid state.
   */
  public void disableLocker(Locker locker) throws IllegalParameterException {
    int lockerId = locker.getId();
    lockerService.disableLocker(lockerId);
  }

  /**
   * Adds a callback to the given locker.
   *
   * @param locker to add the callback to.
   * @throws IllegalParameterException if the locker is not in a valid state.
   */
  public void addCallback(Locker locker) throws IllegalParameterException {
    int lockerId = locker.getId();
    //lockerService.addCallback(lockerId);
  }

  /**
   * Removes a callback from the given locker.
   *
   * @param locker to remove the callback from.
   * @throws IllegalParameterException if the locker is not in a valid state.
   */
  public void removeCallback(Locker locker) throws IllegalParameterException {
    int lockerId = locker.getId();
    //lockerService.removeCallback(lockerId);
  }

  /**
   * Returns the lockerId property.
   *
   * @return the lockerId property.
   */
  public StringProperty passcodeProperty() {
    return passcode;
  }

  /**
   * Returns the cabinetLockerDetails property.
   *
   * @return the cabinetLockerDetails property.
   */
  public StringProperty cabinetLockerDetailsProperty() {
    return cabinetLockerDetails;
  }

  /**
   * Generates a passcode for the given locker.
   *
   * @param locker the locker to generate the passcode for.
   */
  public void generatePasscode(Locker locker) {
    try {
      lockerService.createLockerPassword(locker.getId());
      passcode.set(String.valueOf(locker.getPassword()));
    } catch (IllegalParameterException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Confirms the passcode for the given locker.
   *
   * @param locker the locker to confirm the passcode for.
   */
  public void confirmPasscode(Locker locker) {
    try {
      int enteredPasscode = Integer.parseInt(passcode.get());
      lockerService.unlockLocker(locker.getId(), enteredPasscode);
    } catch (IllegalParameterException | IllegalStateException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Saves the passcode to the clipboard.
   */
  public void savePasscodeToClipboard() {
    content.putString(passcode.get());
    clipboard.setContent(content);
  }

  /**
   * Sets the details.
   *
   * @param details of the cabinet.
   */
  public void setCabinetLockerDetails(String details) {
    cabinetLockerDetails.set(details);
  }


  /**
   * Returns the lockerId property.
   *
   * @return the lockerId property.
   */
  public ObjectProperty<Integer> lockerIdProperty() {
    return lockerId;
  }

}
