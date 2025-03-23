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

package de.hhn.it.devtools.components.passGen.provider;

import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;
import de.hhn.it.devtools.apis.exceptions.IllegalStateException;
import de.hhn.it.devtools.apis.passGen.Locker;
import de.hhn.it.devtools.apis.passGen.LockerListener;
import de.hhn.it.devtools.apis.passGen.LockerService;
import de.hhn.it.devtools.apis.passGen.LockerState;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A simple implementation of the LockerService interface.
 */
public class SimpleLockerService implements LockerService {

  private static final org.slf4j.Logger logger =
          org.slf4j.LoggerFactory.getLogger(SimpleLockerService.class);

  private ConcurrentHashMap<Integer, Locker> lockers;

  /**
   * Constructor that initializes the lockers map with the lockers from the repository.
   */
  public SimpleLockerService() {
    this.lockers = SingletonLockerRepository.getInstance().getLockers();
  }

  /**
   * Adds a listener to get updates on the state of the locker.
   *
   * @param id       id of the locker
   * @param listener object implementing the listener interface
   * @throws IllegalParameterException if either the id does not exist or the listener is a null
   *                                   reference.
   */
  public void addCallback(int id, LockerListener listener) throws IllegalParameterException {
    if (listener == null) {
      throw new IllegalParameterException("Listener is null.");
    }
    logger.info("addCallback: id = {}, listener = {}", id, listener);
    Locker locker = getLocker(id);
    locker.addCallback(listener);
  }

  /**
   * Removes a listener.
   *
   * @param id       id of the locker
   * @param listener listener to be removed
   * @throws IllegalParameterException if the id is invalid or the listener is null
   */
  public void removeCallback(int id, LockerListener listener) throws IllegalParameterException {
    if (listener == null) {
      throw new IllegalParameterException("Listener is null.");
    }
    logger.info("removeCallback: id = {}, listener = {}", id, listener);
    Locker locker = getLocker(id);
    locker.removeCallback(listener);
  }

  /**
   * Returns a map of registered lockers with their IDs as keys.
   *
   * @return HashMap of registered lockers
   */
  public ConcurrentHashMap<Integer, Locker> getLockers() {
    logger.info("returning HashMap of registered lockers");
    lockers = SingletonLockerRepository.getInstance().getLockers();
    return lockers;
  }

  /**
   * Returns the locker with the given id.
   *
   * @param id id of the locker
   * @return locker with the given id
   * @throws IllegalParameterException if a Locker with this id does not exist
   */
  public Locker getLocker(int id) throws IllegalParameterException {
    logger.info("getting locker with id: {}", id);
    Locker locker = lockers.get(id);
    if (locker != null) {
      return locker;
    } else {
      throw new IllegalParameterException("Locker with id " + id + " does not exist.");
    }
  }

  /**
   * Returns the state of the locker with the given id.
   *
   * @param id id of the locker
   * @return state of the locker
   * @throws IllegalParameterException if a Locker with this id does not exist
   */
  public LockerState getLockerState(int id) throws IllegalParameterException {
    logger.info("getting state of locker with id: {}", id);
    if (lockers.containsKey(id)) {
      return getLocker(id).getState();
    } else {
      throw new IllegalParameterException("Locker with id " + id + " does not exist.");
    }
  }

  /**
   * Opens a locker with the given id and password.
   *
   * @param id       id of the locker
   * @param password password to open the locker
   * @throws IllegalParameterException if the id is invalid or the password is incorrect
   */
  public void unlockLocker(int id, int password)
          throws IllegalParameterException, IllegalStateException {
    logger.info("unlocking locker with id: {}", id);
    if (lockers.containsKey(id)) {
      //checking if the locker state allows unlocking
      Locker locker = getLocker(id);
      if (locker.getState() == LockerState.LOCKED
              || locker.getState() == LockerState.ACTIVE) {
        locker.unlock(password);
        logger.debug("Locker with id {} unlocked", id);
      } else {
        throw new IllegalStateException("Locker with id " + id
                + " has a state other than LOCKED, ACTIVE.");
      }
    } else {
      throw new IllegalParameterException("Locker with id " + id + " does not exist.");
    }
  }

  /**
   * Closes a locker with the given id.
   *
   * @param id id of the locker
   * @throws IllegalParameterException if the id is invalid
   */
  public void lockLocker(int id) throws IllegalParameterException, IllegalStateException {
    logger.info("locking locker with id: {}", id);
    if (lockers.containsKey(id)) {
      //checking if the locker state allows locking
      Locker locker = getLocker(id);
      if (locker.getState() == LockerState.UNLOCKED
              || locker.getState() == LockerState.ACTIVE) {
        locker.lock();
        logger.debug("Locker with id {} locked", id);
      } else {
        throw new IllegalStateException("Locker with id " + id
                + " has a state other than UNLOCKED, ACTIVE.");
      }
    } else {
      throw new IllegalParameterException("Locker with id " + id + " does not exist.");
    }
  }

  /**
   * Activates a locker with the given id.
   *
   * @param id id of the locker
   * @throws IllegalParameterException if the id is invalid
   */
  public void activateLocker(int id) throws IllegalParameterException, IllegalStateException {
    logger.info("activating locker with id: {}", id);
    if (lockers.containsKey(id)) {
      //checking if the locker state allows activating
      Locker locker = getLocker(id);
      if (locker.getState() == LockerState.DEACTIVATED || locker.getState() == LockerState.LOCKED) {
        locker.activate();
        logger.debug("Locker with id {} activated", id);
      } else {
        throw new IllegalStateException("Locker with id " + id
                + " has a state other than DEACTIVATED or LOCKED.");
      }
    } else {
      throw new IllegalParameterException("Locker with id " + id
              + " does not exist.");
    }
  }

  /**
   * Deactivates a locker with the given id.
   *
   * @param id id of the locker
   * @throws IllegalParameterException if the id is invalid
   */
  public void deactivateLocker(int id) throws IllegalParameterException, IllegalStateException {
    logger.info("deactivating locker with id: {}", id);
    if (lockers.containsKey(id)) {
      //checking if the locker state allows deactivating
      Locker locker = getLocker(id);
      if (locker.getState() == LockerState.ACTIVE) {
        locker.deactivate();
        logger.debug("Locker with id {} deactivated", id);
      } else {
        throw new IllegalStateException("Locker with id " + id + " has a state other than ACTIVE.");
      }
    } else {
      throw new IllegalParameterException("Locker with id " + id + " does not exist.");
    }
  }

  /**
   * Disables a locker with the given id.
   *
   * @param id id of the locker
   * @throws IllegalParameterException if the id is invalid
   */
  public void disableLocker(int id) throws IllegalParameterException, IllegalStateException {
    logger.info("disabling locker with id: {}", id);
    if (lockers.containsKey(id)) {
      //checking if the locker state allows disabling
      Locker locker = getLocker(id);
      if (locker.getState() == LockerState.DEACTIVATED
              || locker.getState() == LockerState.ACTIVE) {
        locker.disable();
        logger.debug("Locker with id {} disabled", id);
      } else {
        throw new IllegalStateException("Locker with id " + id
                + " has a state other than UNLOCKED.");
      }
    } else {
      throw new IllegalParameterException("Locker with id " + id + " does not exist.");
    }
  }

  /**
   * Sets the password of a locker with the given id.
   *
   * @param id          ID of the locker
   * @param newPassword The new password for the locker
   * @throws IllegalParameterException if the id is invalid
   * @throws java.lang.IllegalStateException if the locker is not in the correct state
   */
  @Override
  public void setLockerPassword(int id, int newPassword)
          throws IllegalParameterException, java.lang.IllegalStateException {
    logger.info("setting password of locker with id: {}", id);
    if (lockers.containsKey(id)) {
      //checking if the locker state allows setting a password
      Locker locker = getLocker(id);
      if (locker.getState() == LockerState.ACTIVE) {
        locker.setPassword(newPassword);
      } else {
        throw new IllegalStateException("Locker with id " + id + " has a state other than ACTIVE.");
      }
    } else {
      throw new IllegalParameterException("Locker with id " + id + " does not exist.");
    }

  }

  /**
   * Creates the password of a locker with the given id.
   *
   * @param id id of the locker
   * @throws IllegalParameterException if the id is invalid
   */
  public void createLockerPassword(int id) throws IllegalParameterException, IllegalStateException {
    logger.info("setting password of locker with id: {}", id);
    if (lockers.containsKey(id)) {
      //checking if the locker state allows setting a password
      Locker locker = getLocker(id);
      if (locker.getState() == LockerState.ACTIVE) {
        SimplePasscodeGenerator passGen = new SimplePasscodeGenerator();
        int passcode = passGen.createPasscode();
        locker.setPassword(passcode);
      } else {
        throw new IllegalStateException("Locker with id " + id + " has a state other than ACTIVE.");
      }
    } else {
      throw new IllegalParameterException("Locker with id " + id + " does not exist.");
    }
  }
}