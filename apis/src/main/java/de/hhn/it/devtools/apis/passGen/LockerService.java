package de.hhn.it.devtools.apis.passGen;

import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This interface provides methods for managing a collection of lockers.
 * All lockers have the same functionality.
 */
public interface LockerService {

  /**
   * Adds a listener to receive updates on the state of a specific locker.
   *
   * @param id       ID of the locker
   * @param listener Object implementing the LockerListener interface
   * @throws IllegalParameterException if the ID does not exist or the listener is null
   */
  void addCallback(int id, LockerListener listener) throws IllegalParameterException;

  /**
   * Removes a listener from a specific locker.
   *
   * @param id       ID of the locker
   * @param listener Listener to be removed
   * @throws IllegalParameterException if the ID is invalid or the listener is null
   */
  void removeCallback(int id, LockerListener listener) throws IllegalParameterException;

  /**
   * Returns a concurrent map of registered lockers with their IDs as keys.
   *
   * @return ConcurrentHashMap of registered lockers
   */
  ConcurrentHashMap<Integer, Locker> getLockers();

  /**
   * Retrieves a specific locker by its ID.
   *
   * @param id ID of the locker
   * @return Locker with the given ID
   * @throws IllegalParameterException if a locker with this ID does not exist
   */
  Locker getLocker(int id) throws IllegalParameterException;

  /**
   * Retrieves the state of a specific locker by its ID.
   *
   * @param id ID of the locker
   * @return State of the locker
   * @throws IllegalParameterException if a locker with this ID does not exist
   */
  LockerState getLockerState(int id) throws IllegalParameterException;

  /**
   * Unlocks a locker with the given ID and password.
   *
   * @param id       ID of the locker
   * @param password Password to unlock the locker
   * @throws IllegalParameterException if the ID is invalid or the password is incorrect
   * @throws IllegalStateException     if the locker's current state
   *                                   does not allow it to be unlocked
   */
  void unlockLocker(int id, int password) throws IllegalParameterException, IllegalStateException;

  /**
   * Locks a locker with the given ID.
   *
   * @param id ID of the locker
   * @throws IllegalParameterException if the ID is invalid
   * @throws IllegalStateException     if the locker's current state does not allow it to be locked
   */
  void lockLocker(int id) throws IllegalParameterException, IllegalStateException;

  /**
   * Activates a locker with the given ID.
   *
   * @param id ID of the locker
   * @throws IllegalParameterException if the ID is invalid
   * @throws IllegalStateException     if the locker's current state
   *                                   does not allow it to be activated
   */
  void activateLocker(int id) throws IllegalParameterException, IllegalStateException;

  /**
   * Deactivates a locker with the given ID.
   *
   * @param id ID of the locker
   * @throws IllegalParameterException if the ID is invalid
   * @throws IllegalStateException     if the locker's current state
   *                                   does not allow it to be deactivated
   */
  void deactivateLocker(int id) throws IllegalParameterException, IllegalStateException;

  /**
   * Disables a locker with the given ID.
   *
   * @param id ID of the locker
   * @throws IllegalParameterException if the ID is invalid
   * @throws IllegalStateException     if the locker's current state
   *                                   does not allow it to be disabled
   */
  void disableLocker(int id) throws IllegalParameterException, IllegalStateException;

  /**
   * Sets the password of a locker with the given ID.
   *
   * @param id          ID of the locker
   * @param newPassword The new password for the locker
   * @throws IllegalParameterException if the ID is invalid
   * @throws IllegalStateException     if the locker's current state
   *                                   does not allow a new password to be set
   */
  void setLockerPassword(int id, int newPassword)
          throws IllegalParameterException, IllegalStateException;

}
