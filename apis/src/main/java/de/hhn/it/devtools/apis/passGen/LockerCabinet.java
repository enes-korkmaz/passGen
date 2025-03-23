package de.hhn.it.devtools.apis.passGen;

import java.util.Map;

/**
 * This interface provides methods for managing a locker cabinet.
 */
public interface LockerCabinet {

  /**
   * Set the location of the locker cabinet.
   *
   * @param location The location to set for the locker cabinet.
   */
  void setLocation(String location);

  /**
   * Gets the ID of the locker cabinet.
   *
   * @return The id of the cabinet
   */
  int getCabinetId();

  /**
   * Gets the location of the locker cabinet.
   *
   * @return The location of the locker cabinet.
   */
  String getLocation();

  /**
   * Adds a locker to the locker cabinet.
   *
   * @param id     The id of the locker.
   * @param locker The locker to be added.
   */
  void addLocker(int id, Locker locker);

  /**
   * Gets the entire locker cabinet as a map.
   *
   * @return A Map representing the locker cabinet.
   */
  Map<Integer, Locker> getLockerCabinet();

  /**
   * Gets a specific locker from the locker cabinet.
   *
   * @param id The id of a locker.
   * @return The locker with the id.
   */
  Locker getLocker(int id);

}
