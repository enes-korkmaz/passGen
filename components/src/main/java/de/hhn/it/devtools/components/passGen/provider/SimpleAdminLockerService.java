package de.hhn.it.devtools.components.passGen.provider;

import static de.hhn.it.devtools.apis.passGen.LockerState.DISABLED;

import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;
import de.hhn.it.devtools.apis.passGen.AdminLockerService;
import de.hhn.it.devtools.apis.passGen.Locker;
import de.hhn.it.devtools.apis.passGen.LockerCabinet;
import de.hhn.it.devtools.apis.passGen.LockerState;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;


/**
 * A simple implementation of the AdminLockerService interface.
 */
public class SimpleAdminLockerService implements AdminLockerService {

  private static final org.slf4j.Logger logger =
          org.slf4j.LoggerFactory.getLogger(SimpleAdminLockerService.class);

  private int idCounterL = 0;
  private int idCounterLu = 0;
  private ConcurrentHashMap<Integer, Locker> lockers;
  private ConcurrentHashMap<Integer, LockerCabinet> lockerCabinet = new ConcurrentHashMap<>();

  public SimpleAdminLockerService() {
    this.lockers = SingletonLockerRepository.getInstance().getLockers();
    this.lockerCabinet = SingletonLockerRepository.getInstance().getLockerCabinets();
  }

  /**
   * Creates a new locker.
   *
   * @return the id of the new locker
   */
  public int createLocker(LockerCabinet lockerCabinet, String location) {
    if (lockerCabinet == null || location == null) {
      throw new IllegalArgumentException("LockerCabinet and location cannot be null.");
    }
    int newId = ++idCounterL;
    logger.info("Creating a new locker with ID: {}", newId);
    SimpleLocker locker = new SimpleLocker(newId, lockerCabinet, location);
    locker.adminSetState(DISABLED);
    SingletonLockerRepository.getInstance().addLocker(newId, locker);
    return newId;
  }

  /**
   * Removes a locker from the service.
   *
   * @param lockerId the id of the locker to be removed
   * @throws IllegalParameterException if the id of the locker does not exist
   */
  public void removeLocker(int lockerId) throws IllegalParameterException {
    logger.info("Removing a locker: {}", lockerId);
    if (!lockers.containsKey(lockerId)) {
      throw new IllegalParameterException("Locker does not exist.");
    }
    SingletonLockerRepository.getInstance().removeLocker(lockerId);
  }

  /**
   * Creates a new lockerCabinet.
   *
   * @return the id of the new lockerCabinet
   */
  public int createLockerCabinet(String location) throws IllegalArgumentException {
    if (location == null) {
      throw new IllegalArgumentException("Location cannot be null.");
    }
    int newId = ++idCounterLu;
    logger.info("Creating a new lockerCabinet with ID: {}", newId);
    SimpleLockerCabinet lockerCabinet = new SimpleLockerCabinet(newId, location, new HashMap<>());
    SingletonLockerRepository.getInstance().addLockerCabinet(newId, lockerCabinet);
    return newId;
  }

  /**
   * Adds a locker to a lockerCabinet.
   *
   * @param lockerCabinetId the id of the lockerCabinet
   * @param locker          the locker to be added
   * @throws IllegalParameterException if the id of the lockerCabinet does not exist
   */
  @Override
  public void addLockerToLockerCabint(int lockerCabinetId, Locker locker)
          throws IllegalParameterException {
    logger.info("Adding locker to lockerCabinet: {}", lockerCabinetId);
    if (!lockerCabinet.containsKey(lockerCabinetId)) {
      throw new IllegalParameterException("LockerCabinet does not exist.");
    }
    lockerCabinet.get(lockerCabinetId).addLocker(locker.getId(), locker);
  }

  /**
   * Returns the lockerCabinet with the given id.
   *
   * @param lockerCabinetId the id of the lockerCabinet
   * @return the lockerCabinet with the given id
   * @throws IllegalParameterException if the id of the lockerCabinet does not exist
   */
  public LockerCabinet getLockerCabinet(int lockerCabinetId) throws IllegalParameterException {
    logger.info("Getting lockerCabinet with id: {}", lockerCabinetId);
    if (!lockerCabinet.containsKey(lockerCabinetId)) {
      throw new IllegalParameterException("LockerCabinet does not exist.");
    }
    return lockerCabinet.get(lockerCabinetId);
  }

  /**
   * Removes a lockerCabinet from the service.
   *
   * @param lockerCabinetId the id of the lockerCabinet to be removed
   * @throws IllegalParameterException if the id of the lockerCabinet does not exist
   */
  public void removeLockerCabinet(int lockerCabinetId) throws IllegalParameterException {
    logger.info("Removing a lockerCabinet: {}", lockerCabinetId);
    if (!lockerCabinet.containsKey(lockerCabinetId)) {
      throw new IllegalParameterException("LockerCabinet does not exist.");
    }
    SingletonLockerRepository.getInstance().removeLockerCabinet(lockerCabinetId);
  }

  /**
   * Sets the state of a locker.
   *
   * @param lockerId the id of the locker
   * @param state    the new state of the locker
   * @throws IllegalParameterException if the id of the locker does not exist
   */
  public void setLockerState(int lockerId, LockerState state) throws IllegalParameterException {
    logger.info("Setting the state of a locker: {}", lockerId);
    if (!lockers.containsKey(lockerId)) {
      throw new IllegalParameterException("Locker does not exist.");
    }
    SimpleLocker locker = (SimpleLocker) lockers.get(lockerId);
    locker.adminSetState(state);
  }
}
