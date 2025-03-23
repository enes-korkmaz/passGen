package de.hhn.it.devtools.components.passGen;

import de.hhn.it.devtools.apis.passGen.LockerState;
import de.hhn.it.devtools.components.passGen.provider.SimpleLockerListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSimpleLockerListener {

  private SimpleLockerListener listener;


  @BeforeEach
  public void setup() {
    listener = new SimpleLockerListener();
  }

  @Test
  @DisplayName("Test listener for new State")
  public void testNewState() {
    LockerState lockerState = LockerState.ACTIVE;
    listener.newState(lockerState);
    assertEquals(lockerState, listener.getCurrentState());
  }

  @Test
  @DisplayName("Test listener for new State")
  public void testNewPassord() {
    int password = 1568456359;
    listener.newPassword(password);
    assertEquals(password, listener.getCurrentPassword());
  }
}
