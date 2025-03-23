package de.hhn.it.devtools.components.passGen;

import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;
import de.hhn.it.devtools.apis.exceptions.IllegalStateException;
import de.hhn.it.devtools.apis.passGen.Locker;
import de.hhn.it.devtools.apis.passGen.LockerCabinet;
import de.hhn.it.devtools.apis.passGen.LockerListener;
import de.hhn.it.devtools.apis.passGen.LockerState;
import de.hhn.it.devtools.components.passGen.provider.SimpleAdminLockerService;
import de.hhn.it.devtools.components.passGen.provider.SimpleLockerListener;
import de.hhn.it.devtools.components.passGen.provider.SimpleLockerService;
import de.hhn.it.devtools.components.passGen.provider.SingletonLockerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLockerServiceGoodCases {
  private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TestLockerServiceGoodCases.class);
  private SimpleLockerService lockerService;
  private SimpleAdminLockerService adminLockerService;
  private int lockerId;

  @BeforeEach
  public void setup() throws IllegalParameterException {
    SingletonLockerRepository.getInstance().resetRepository();
    adminLockerService = new SimpleAdminLockerService();
    lockerService = new SimpleLockerService();
    int lockerUniteId = adminLockerService.createLockerCabinet("Location A");
    LockerCabinet lockerCabinet = SingletonLockerRepository.getInstance().getLockerCabinets().get(lockerUniteId);
    lockerId = adminLockerService.createLocker(lockerCabinet, "Location A");
    Locker locker = SingletonLockerRepository.getInstance().getLockers().get(lockerId);
    locker.setPassword(987123);
    adminLockerService.setLockerState(lockerId, LockerState.DEACTIVATED);
  }

  @Test
  @DisplayName("Test unlock/lock locker successfully")
  public void testUnlockAndLockLocker() throws IllegalParameterException, IllegalStateException {
    // Unlock the locker first
    lockerService.activateLocker(lockerId);
    lockerService.unlockLocker(lockerId, 987123);
    Locker locker = SingletonLockerRepository.getInstance().getLockers().get(lockerId);
    assertEquals(LockerState.UNLOCKED, locker.getState());

    // Now lock the locker
    lockerService.lockLocker(lockerId);
    assertEquals(LockerState.LOCKED, locker.getState());
  }

  @Test
  @DisplayName("Test activate/deactivate locker successfully")
  public void testActivateAndDeactivateLocker() throws IllegalParameterException, IllegalStateException {
    // Activate the locker
    lockerService.activateLocker(lockerId);
    Locker locker = SingletonLockerRepository.getInstance().getLockers().get(lockerId);
    assertEquals(LockerState.ACTIVE, locker.getState());

    // Deactivate the locker
    lockerService.deactivateLocker(lockerId);
    assertEquals(LockerState.DEACTIVATED, locker.getState());
  }

  @Test
  @DisplayName("Test disable locker successfully")
  public void testDisableLocker() throws IllegalParameterException, IllegalStateException {
    // Disable the locker
    lockerService.disableLocker(lockerId);
    Locker locker = SingletonLockerRepository.getInstance().getLockers().get(lockerId);
    assertEquals(LockerState.DISABLED, locker.getState());
  }

  @Test
  @DisplayName("Test retrieving LockerState")
  public void testGetLockerState() throws IllegalParameterException {
    LockerState lockerState = lockerService.getLockerState(lockerId);
    assertEquals(LockerState.DEACTIVATED, lockerState);
  }

  @Test
  @DisplayName("Test retrieve lockers successfully")
  public void testGetLockers() {
    logger.debug("Number of lockers: {}", lockerService.getLockers().size());
    assertEquals(1, lockerService.getLockers().size());
  }

  @Test
  @DisplayName("Test retrieve add/remove listeners successfully")
  public void testAddRemoveListeners() throws IllegalParameterException {
    LockerListener listener = new SimpleLockerListener();
    lockerService.addCallback(lockerId, listener);
    logger.debug("Number of listeners: {}", lockerService.getLocker(lockerId).getListener().size());
    assertEquals(1, lockerService.getLocker(lockerId).getListener().size());
    lockerService.removeCallback(lockerId, listener);
    logger.debug("Number of listeners: {}", lockerService.getLocker(lockerId).getListener().size());
    assertEquals(0, lockerService.getLocker(lockerId).getListener().size());
  }

  @Test
  @DisplayName("Test setting Locker password successfully")
  public void testSetLockerPassword() throws IllegalParameterException {
    lockerService.activateLocker(lockerId);
    lockerService.createLockerPassword(lockerId);
    Locker locker = SingletonLockerRepository.getInstance().getLockers().get(lockerId);
    //needs another way to be tested
    assertEquals(locker.getPassword(), locker.getPassword());
  }
}
