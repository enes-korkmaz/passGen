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

import de.hhn.it.devtools.apis.passGen.Locker;
import de.hhn.it.devtools.apis.passGen.LockerCabinet;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple implementation of the LockerCabinet interface.
 */
public class SimpleLockerCabinet implements LockerCabinet {
  private static final org.slf4j.Logger logger =
          org.slf4j.LoggerFactory.getLogger(SimpleLockerCabinet.class);

  private int cabinetId;
  private String location;
  private Locker locker;
  private Map<Integer, Locker> lockers = new HashMap<>();


  /**
   * Constructor for the locker cabinet.
   *
   * @param cabinetId The unique identifier of the locker.
   * @param location  The location of the locker unite.
   * @param lockers   The map to store the locker.
   */
  public SimpleLockerCabinet(int cabinetId, String location, Map<Integer, Locker> lockers) {
    this.cabinetId = cabinetId;
    this.location = location;
    this.lockers = lockers;
  }

  /**
   * Set the location of the locker cabinet.
   *
   * @param location The location to set to the locker cabinet.
   */
  public void setLocation(String location) {
    logger.info("Sets the location of the locker unite.");
    this.location = location;
  }

  /**
   * Gets the location of the locker cabinet.
   *
   * @return The location of the locker cabinet.
   */
  public String getLocation() {
    logger.info("Gets the locker unite location");
    return location;
  }

  /**
   * Adds a locker to the LockerCabinet.
   *
   * @param id     The id of the locker.
   * @param locker The locker where to be added.
   */
  public void addLocker(int id, Locker locker) {
    logger.info("Adds locker to the locker unite: {} {}", id, locker);
    lockers.put(id, locker);
  }

  /**
   * Removes a locker from the LockerCabinet.
   *
   * @param id The id of the locker to be removed.
   */
  public void removeLocker(int id) {
    logger.info("Removes locker from the locker unite: {}", id);
    lockers.remove(id);
  }

  /**
   * Gets the entire locker cabinet as a map.
   *
   * @return A Map representing the locker unite.
   */
  public Map<Integer, Locker> getLockerCabinet() {
    logger.info("returns the entire map of the locker.");
    return lockers;
  }

  /**
   * Gets a specific locker from the LockerCabinet.
   *
   * @param id The id of a locker.
   * @return The locker with the id.
   */
  public Locker getLocker(int id) {
    logger.info("Get a locker: {}", id);
    if (!lockers.containsKey(id)) {
      throw new IllegalArgumentException("The locker unit does not contains the given ID.");
    }
    return lockers.get(id);
  }

  /**
   * Getter for the cabinet id.
   *
   * @return the cabinet id
   */
  public int getCabinetId() {
    return cabinetId;
  }

}
