package de.hhn.it.devtools.components.passGen.provider;

import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;
import de.hhn.it.devtools.apis.exceptions.IllegalStateException;
import de.hhn.it.devtools.apis.passGen.Locker;
import de.hhn.it.devtools.apis.passGen.LockerCabinet;
import de.hhn.it.devtools.apis.passGen.LockerListener;
import de.hhn.it.devtools.apis.passGen.LockerState;
import de.hhn.it.devtools.apis.passGen.PasscodeGenerator;
import de.hhn.it.devtools.apis.passGen.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * A simple implementation of the Locker interface.
 */
public class SimpleLocker implements Locker {

  private static final org.slf4j.Logger logger =
          org.slf4j.LoggerFactory.getLogger(SimpleLocker.class);

  private int id;
  private LockerCabinet lockerCabinet;
  private String location;
  private int passcode;
  private LockerState state;
  private List<LockerListener> listeners = new ArrayList<>();
  private Stack<User> userStack = new Stack<>();

  /**
   * Creates a new locker.
   *
   * @param lockerId The id of the locker.
   * @param lockerCabinet The locker cabinet to which the locker belongs.
   * @param lockerLocation The location of the locker.
   */
  public SimpleLocker(int lockerId, LockerCabinet lockerCabinet, String lockerLocation) {
    id = lockerId;
    this.lockerCabinet = lockerCabinet;
    location = lockerLocation;
    state = LockerState.LOCKED; // Assuming default state
  }

  public void adminSetState(LockerState state) {
    this.state = state;
  }

  /**
   * Adds a listener to get updates on the state of the locker.
   *
   * @param listener listener to be added
   * @throws IllegalParameterException If the listener is null
   */
  public void addCallback(LockerListener listener) throws IllegalParameterException {
    if (listener == null) {
      throw new IllegalParameterException("The listener is null.");
    }
    logger.info("listener: {}", listener);
    listeners.add(listener);
  }

  /**
   * Removes a listener.
   *
   * @param listener listener to be removed
   * @throws IllegalParameterException If the listener is null
   */
  public void removeCallback(LockerListener listener) throws IllegalParameterException {
    if (listener == null) {
      throw new IllegalParameterException("The listener is null.");
    }
    logger.info("listener: {}", listener);
    listeners.remove(listener);
  }

  /**
   * Retrieves the listeners of the locker.
   *
   * @return List of listeners
   */
  @Override
  public List<LockerListener> getListener() {
    return listeners;
  }

  /**
   * Attempts to unlock the locker with the provided password.
   *
   * @param password The password to unlock the locker.
   * @throws IllegalParameterException If the password is incorrect or null.
   */
  public void unlock(int password) throws IllegalParameterException {
    if (passcode != password || password == 0) {
      throw new IllegalParameterException("The password is not correct.");
    }
    this.state = LockerState.UNLOCKED;
    logger.info("The locker has been unlocked.");

    for (LockerListener listener : listeners) {
      listener.newState(this.state);
    }
  }

  /**
   * Locks the locker, securing its contents.
   *
   * @throws IllegalStateException If the locker is already locked or cannot be locked.
   */
  public void lock() throws IllegalStateException {
    if (state == LockerState.LOCKED) {
      throw new IllegalStateException("The locker is already locked.");
    }
    this.state = LockerState.LOCKED;
    logger.info("The locker has been locked.");

    for (LockerListener listener : listeners) {
      listener.newState(this.state);
    }
  }

  /**
   * Checks if the locker is currently locked.
   *
   * @return True if the locker is locked, false otherwise.
   */
  public boolean isLocked() {
    logger.info("The locker state is: " + state);
    if (state == LockerState.LOCKED) {
      return true;
    }
    return false;
  }

  /**
   * Retrieves the current state of the locker.
   *
   * @return The current state of the locker.
   */
  public LockerState getState() {
    logger.info("Gets the locker state.");
    return state;
  }

  /**
   * Sets a new password for the locker, replacing the old one.
   *
   * @param newPassword The new password to set.
   * @throws IllegalParameterException If the new password is weak, too short, or null.
   */
  public void setPassword(int newPassword) throws IllegalParameterException {
    if (newPassword <= 0
            || String.valueOf(newPassword).length() < PasscodeGenerator.DEFAULT_LENGTH) {
      throw new IllegalParameterException("The password cannot be used for security reasons.");
    }
    this.passcode = newPassword;
    logger.info("The password has changed.");

    for (LockerListener listener : listeners) {
      listener.newPassword(newPassword);
    }
  }

  /**
   * Retrieves the password of the locker.
   *
   * @return password of the locker
   */
  @Override
  public int getPassword() {
    return passcode;
  }

  /**
   * Retrieves the unique identifier for the locker.
   *
   * @return The unique identifier of the locker.
   */
  public int getId() {
    logger.info("Gets the locker id.");
    return id;
  }

  /**
   * Activates the locker, making it ready for use.
   *
   * @throws IllegalStateException If the locker is already active or cannot be activated.
   */
  public void activate() throws IllegalStateException {
    logger.info("The state of the logger is: {}", state);
    if (this.state == LockerState.ACTIVE || this.state == LockerState.DISABLED) {
      throw new IllegalStateException("You cannot activate the locker.");
    }
    this.state = LockerState.ACTIVE;
    for (LockerListener listener : listeners) {
      listener.newState(this.state);
    }
  }

  /**
   * Deactivates the locker, preventing further use until reactivated.
   *
   * @throws IllegalStateException If the locker is not active or cannot be deactivated.
   */
  public void deactivate() throws IllegalStateException {
    logger.info("The state of the logger is: {}", state);
    if (state != LockerState.ACTIVE) {
      throw new IllegalStateException("");
    }
    this.state = LockerState.DEACTIVATED;

    for (LockerListener listener : listeners) {
      listener.newState(this.state);
    }
  }

  /**
   * Disables the locker, preventing any use.
   */
  public void disable() throws IllegalStateException {
    this.state = LockerState.DISABLED;
    logger.info("The locker has been disabled.");
  }

  /**
   * Checks if the locker is currently activated.
   *
   * @return True if the locker is activated, false otherwise.
   */
  public boolean isActivated() {
    logger.info("The state of the logger is: {}", state);
    if (state == LockerState.ACTIVE) {
      return true;
    }
    return false;
  }

}
