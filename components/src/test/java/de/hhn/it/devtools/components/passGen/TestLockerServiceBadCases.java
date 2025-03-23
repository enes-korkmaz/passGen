package de.hhn.it.devtools.components.passGen;

import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;
import de.hhn.it.devtools.apis.exceptions.IllegalStateException;
import de.hhn.it.devtools.apis.passGen.Locker;
import de.hhn.it.devtools.apis.passGen.LockerCabinet;
import de.hhn.it.devtools.apis.passGen.LockerState;
import de.hhn.it.devtools.components.passGen.provider.SimpleAdminLockerService;
import de.hhn.it.devtools.components.passGen.provider.SimpleLockerService;
import de.hhn.it.devtools.components.passGen.provider.SingletonLockerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestLockerServiceBadCases {
  private SimpleAdminLockerService adminLockerService;
  private SimpleLockerService lockerService;
  private int lockerId;

  @BeforeEach
  public void setup() throws IllegalParameterException {
    adminLockerService = new SimpleAdminLockerService();
    lockerService = new SimpleLockerService();
    int lockerUniteId = adminLockerService.createLockerCabinet("Location A");
    LockerCabinet lockerCabinet = SingletonLockerRepository.getInstance().getLockerCabinets().get(lockerUniteId);
    lockerId = adminLockerService.createLocker(lockerCabinet, "Location A");
    Locker locker = SingletonLockerRepository.getInstance().getLockers().get(lockerId);
    locker.setPassword(987123);
    adminLockerService.setLockerState(lockerId, LockerState.DISABLED);
  }

  @Test
  @DisplayName("Test unlock locker with wrong password")
  public void testUnlockLockerWithWrongPassword() throws IllegalParameterException {
    int wrongPassword = 0000000;
    adminLockerService.setLockerState(lockerId, LockerState.LOCKED);
    assertThrows(IllegalParameterException.class, () -> lockerService.unlockLocker(lockerId, wrongPassword));
  }

  @Test
  @DisplayName("Test unlock nonexistent locker")
  public void testUnlockNonexistentLocker() {
    int nonexistentId = -1;
    assertThrows(IllegalParameterException.class, () -> lockerService.unlockLocker(nonexistentId, 1234));
  }

  @Test
  @DisplayName("Test Illegal State Transition")
  public void testIllegalStateTransition() throws IllegalParameterException {
    // Try locking an already locked locker
    adminLockerService.setLockerState(lockerId, LockerState.LOCKED);  // First lock it
    assertThrows(IllegalStateException.class, () -> lockerService.lockLocker(lockerId));

    // Try unlocking an already unlocked locker
    lockerService.unlockLocker(lockerId, 987123);  // First unlock it
    assertThrows(IllegalStateException.class, () -> lockerService.unlockLocker(lockerId, 000000));
  }

  @Test
  @DisplayName("Test add/remove callback with null listener")
  public void testAddRemoveCallbackWithNullListener() throws IllegalParameterException {
    assertThrows(IllegalParameterException.class, () -> lockerService.addCallback(lockerId, null));
    assertThrows(IllegalParameterException.class, () -> lockerService.removeCallback(lockerId, null));
  }

  @Test
  @DisplayName("Test retrieve nonexistent locker and its state")
  public void testRetrieveNonexistentLockerAndItsState() {
    int nonexistentId = -1;
    assertThrows(IllegalParameterException.class, () -> lockerService.getLocker(nonexistentId));
    assertThrows(IllegalParameterException.class, () -> lockerService.getLockerState(nonexistentId));
  }

  @Test
  @DisplayName("Test setting password with wrong id")
  public void testSetPasswordWithWrongId() {
    int nonexistentId = -1;
    assertThrows(IllegalParameterException.class, () -> lockerService.createLockerPassword(nonexistentId));
  }

  @Test
  @DisplayName("Test state transitions")
  public void testStateTransitions() throws IllegalParameterException {
    // Try to lock a locker that is already locked
    adminLockerService.setLockerState(lockerId, LockerState.LOCKED);
    assertThrows(IllegalStateException.class, () -> lockerService.lockLocker(lockerId));

    // Try to activate a locker that is already active
    adminLockerService.setLockerState(lockerId, LockerState.ACTIVE);
    assertThrows(IllegalStateException.class, () -> lockerService.activateLocker(lockerId));

    // Try to deactivate a locker that is already deactivated
    adminLockerService.setLockerState(lockerId, LockerState.DEACTIVATED);
    assertThrows(IllegalStateException.class, () -> lockerService.deactivateLocker(lockerId));

    // Try to disable a locker that is already disabled
    adminLockerService.setLockerState(lockerId, LockerState.DISABLED);
    assertThrows(IllegalStateException.class, () -> lockerService.disableLocker(lockerId));
  }

  @Test
  @DisplayName("Test lock Locker with a not existing ID")
  public void testStateLockerWithNotExistingId() {
    int notExistingId = -1;

    // Try to lock a locker that does not exist
    assertThrows(IllegalParameterException.class, () -> lockerService.lockLocker(notExistingId));

    // Try to activate a locker that does not exist
    assertThrows(IllegalParameterException.class, ()-> lockerService.activateLocker(notExistingId));

    // Try to deactivate a locker that does not exist
    assertThrows(IllegalParameterException.class, ()-> lockerService.deactivateLocker(notExistingId));

    // Try to disable a locker that does not exist
    assertThrows(IllegalParameterException.class, ()-> lockerService.disableLocker(notExistingId));
  }
}
