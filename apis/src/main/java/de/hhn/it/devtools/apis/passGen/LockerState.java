package de.hhn.it.devtools.apis.passGen;

/**
 * Enum to represent the different states a locker can be in.
 */
public enum LockerState {
  /**
   * Locker is opened/unlocked.
   */
  UNLOCKED,

  /**
   * Locker is closed/locked and has to be activated to be able to interact with.
   */
  LOCKED,

  /**
   * Locker is activated but locked (can be unlocked).
   */
  ACTIVE,

  /**
   * Locker is locked and cannot be interacted with (Initial state).
   */
  DEACTIVATED,

  /**
   * Locker is disabled and cannot be activated and interacted with by user.
   */
  DISABLED,

  /**
   * Locker is in usage, active and (unlocked).
   */
  IN_USAGE
}