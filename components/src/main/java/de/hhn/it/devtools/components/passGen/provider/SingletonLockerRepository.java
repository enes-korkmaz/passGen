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
import java.util.concurrent.ConcurrentHashMap;

/**
 * A singleton repository for lockers.
 */
public class SingletonLockerRepository {
  private static SingletonLockerRepository instance;
  private ConcurrentHashMap<Integer, Locker> lockers = new ConcurrentHashMap<>();
  private ConcurrentHashMap<Integer, LockerCabinet> lockerCabinet = new ConcurrentHashMap<>();

  private SingletonLockerRepository() {
  }

  /**
   * Returns the singleton instance of the repository.
   *
   * @return the singleton instance
   */
  public static synchronized SingletonLockerRepository getInstance() {
    if (instance == null) {
      instance = new SingletonLockerRepository();
    }
    return instance;
  }

  /**
   * Returns the lockers.
   */
  public ConcurrentHashMap<Integer, Locker> getLockers() {
    return lockers;
  }

  /**
   * Adds a locker.
   *
   * @param id     of the locker
   * @param locker the locker to be added
   */
  public void addLocker(int id, Locker locker) {
    lockers.put(id, locker);
  }

  /**
   * Removes a locker.
   *
   * @param id of the locker
   */
  public void removeLocker(int id) {
    lockers.remove(id);
  }

  /**
   * Returns the locker cabinets.
   *
   * @return locker cabinets
   */
  public ConcurrentHashMap<Integer, LockerCabinet> getLockerCabinets() {
    return lockerCabinet;
  }

  /**
   * Adds a locker cabinet.
   *
   * @param id            of the cabinet
   * @param lockerCabinet the cabinet to be added
   */
  public void addLockerCabinet(int id, LockerCabinet lockerCabinet) {
    this.lockerCabinet.put(id, lockerCabinet);
  }

  /**
   * Removes a locker cabinet.
   *
   * @param id of the cabinet
   */
  public void removeLockerCabinet(int id) {
    lockerCabinet.remove(id);
  }

  /**
   * Resets the repository.
   */
  public void resetRepository() {
    lockers.clear();
    lockerCabinet.clear();
  }
}