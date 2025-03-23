package de.hhn.it.devtools.components.passGen;

import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;
import de.hhn.it.devtools.apis.exceptions.IllegalStateException;
import de.hhn.it.devtools.apis.passGen.LockerState;
import de.hhn.it.devtools.components.passGen.provider.SimpleLocker;
import de.hhn.it.devtools.components.passGen.provider.SimpleLockerCabinet;
import de.hhn.it.devtools.components.passGen.provider.SimpleLockerListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestSimpleLocker {

  private SimpleLocker simpleLocker;
  private SimpleLockerCabinet lockerCabinet;
  private SimpleLockerListener simpleListener;

  @BeforeEach
  public void setup() {
    simpleLocker = new SimpleLocker(2, lockerCabinet, "Cellar");
    simpleListener = new SimpleLockerListener();
  }


  @Test
  @DisplayName("Test if the listener can be null by add Callback.")
  public void testAddCallback() {
    assertThrows(IllegalParameterException.class, () -> simpleLocker.addCallback(null));
  }

  @Test
  @DisplayName("Test if the listener can be null by remove callback.")
  public void testRemoveCallback() {
    assertThrows(IllegalParameterException.class, () -> simpleLocker.removeCallback(null));
  }

  @Test
  @DisplayName("Test if the locker passed the new state to the listener.")
  public void testUnlock() throws IllegalParameterException {
    assertThrows(IllegalParameterException.class, () -> simpleLocker.unlock(0));
    simpleLocker.addCallback(simpleListener);
    int password = 156151;
    simpleLocker.setPassword(password);
    simpleLocker.unlock(password);

    assertEquals(LockerState.UNLOCKED, simpleListener.getCurrentState());
  }

  @Test
  @DisplayName("Test throws a exception if the locker is already locked.")
  public void testBadLock() {
    simpleLocker.adminSetState(LockerState.LOCKED);
    assertThrows(IllegalStateException.class, () -> simpleLocker.lock());
  }

  @Test
  @DisplayName("Test checks whether the current state is locked.")
  public void testLock() throws IllegalStateException {
    simpleLocker.adminSetState(LockerState.UNLOCKED);
    try {
      simpleLocker.addCallback(simpleListener);
    } catch (IllegalParameterException e) {
      System.out.println("Error message when adding a listener: " + e.getMessage());
    }
    simpleLocker.lock();
    assertEquals(LockerState.LOCKED, simpleListener.getCurrentState());
  }

  @Test
  @DisplayName("Test if the locker is already locked.")
  public void testIsLockedWhenLocked() {
    simpleLocker.adminSetState(LockerState.LOCKED);
    assertTrue(simpleLocker.isLocked());
  }

  @Test
  @DisplayName("Test if the locker is not locked.")
  public void testIsLockedWhenNotLocked() {
    simpleLocker.adminSetState(LockerState.UNLOCKED);
    assertFalse(simpleLocker.isLocked());
  }

  @Test
  @DisplayName("Test throws exception if the password < 0.")
  public void testBadSetPassword() {
    assertThrows(IllegalParameterException.class, () -> simpleLocker.setPassword(-4));
  }

  @Test
  @DisplayName("Test throws exception if the state is already active.")
  public void testAlreadyActivate() {
    simpleLocker.adminSetState(LockerState.ACTIVE);
    assertThrows(IllegalStateException.class, () -> simpleLocker.activate());
  }

  @Test
  @DisplayName("Testthrows exception if the state is disabled.")
  public void testBadActivateDisable() {
    simpleLocker.adminSetState(LockerState.DISABLED);
    assertThrows(IllegalStateException.class, () -> simpleLocker.activate());
  }

  @Test
  @DisplayName("Test checks whether the current state is active.")
  public void testActivate() {
    try {
      simpleLocker.addCallback(simpleListener);
    } catch (IllegalParameterException e) {
      System.out.println("Error message when adding a listener: " + e.getMessage());
    }
    simpleLocker.activate();
    assertEquals(LockerState.ACTIVE, simpleListener.getCurrentState());
  }

  @Test
  @DisplayName("Test throws exception if the state is not active.")
  public void testBadDeactive() {
    if (simpleLocker.getState() != LockerState.ACTIVE) {
      assertThrows(IllegalStateException.class, () -> simpleLocker.deactivate());
    }
  }

  /*@Test
  @DisplayName("Test checks whether the current state is deactivate.")
  public void testDeactivate(){
    try {
      simpleLocker.addCallback(simpleListener);
    } catch (IllegalParameterException e) {
      System.out.println("Error message when adding a listener: " + e.getMessage());
    }
    simpleLocker.deactivate();
    assertEquals(LockerState.DEACTIVATED, simpleListener.getCurrentState());
  }*/


  @Test
  @DisplayName("Test if the locker is activated.")
  public void testIsActivatedWhenActivated() {
    simpleLocker.adminSetState(LockerState.ACTIVE);
    assertTrue(simpleLocker.isActivated());
  }

  @Test
  @DisplayName("Test if the locker is not activated.")
  public void testIsActivatedWhenNotActivated() {
    simpleLocker.adminSetState(LockerState.UNLOCKED);
    assertFalse(simpleLocker.isActivated());
  }

}
