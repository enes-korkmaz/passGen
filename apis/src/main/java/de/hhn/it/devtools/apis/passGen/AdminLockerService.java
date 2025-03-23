package de.hhn.it.devtools.apis.passGen;

import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;

/**
 * This interface provides methods for managing lockers.
 */
public interface AdminLockerService {

  /**
   * Creates a new locker.
   *
   * @param lockerCabinet the lockerCabinet to which the locker belongs
   * @param location      the location of the locker
   * @return the id of the new locker
   * @throws IllegalArgumentException if the lockerCabinet or location is null
   */
  int createLocker(LockerCabinet lockerCabinet, String location) throws IllegalArgumentException;

  /**
   * Removes a locker from the service.
   *
   * @param lockerId the id of the locker to be removed
   * @throws IllegalParameterException if the id of the locker does not exist
   */
  void removeLocker(int lockerId) throws IllegalParameterException;

  /**
   * Adds a locker to a lockerCabinet.
   *
   * @param lockerCabinetId the id of the lockerCabinet
   * @param locker          the locker to be added
   * @throws IllegalParameterException if the id of the lockerCabinet does not exist
   */
  void addLockerToLockerCabint(int lockerCabinetId, Locker locker) throws IllegalParameterException;

  /**
   * Returns the lockerCabinet with the given id.
   *
   * @param lockerCabinetId the id of the lockerCabinet
   * @return the lockerCabinet with the given id
   * @throws IllegalParameterException if the id of the lockerCabinet does not exist
   */
  public LockerCabinet getLockerCabinet(int lockerCabinetId) throws IllegalParameterException;

  /**
   * Creates a new lockerCabinet.
   *
   * @param location the location of the lockerCabinet
   * @return the id of the new lockerCabinet
   * @throws IllegalArgumentException if the location is null
   */
  int createLockerCabinet(String location) throws IllegalArgumentException;

  /**
   * Removes a lockerCabinet from the service.
   *
   * @param lockerCabinetId the id of the lockerCabinet to be removed
   * @throws IllegalParameterException if the id of the lockerCabinet does not exist
   */
  void removeLockerCabinet(int lockerCabinetId) throws IllegalParameterException;

  /**
   * Sets the state of a locker.
   *
   * @param lockerId the id of the locker
   * @param state    the new state of the locker
   * @throws IllegalParameterException if the id of the locker does not exist
   * @throws IllegalStateException     if the locker has a state that does not allow the change
   */
  void setLockerState(int lockerId, LockerState state)
          throws IllegalParameterException, IllegalStateException;

}

