package de.hhn.it.devtools.apis.passGen;

/**
 * Callback interface to notify observers about changes in a locker's state or password.
 */
public interface LockerListener {

  /**
   * Informs the listener that the locker has changed its state.
   *
   * @param state The current state of the locker.
   */
  void newState(LockerState state);

  /**
   * Informs the listener that the locker's password has been updated.
   *
   * @param password The new password of the locker.
   */
  void newPassword(int password);
}
