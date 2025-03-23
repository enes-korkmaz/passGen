package de.hhn.it.devtools.apis.passGen;

import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;

/**
 * Interface for generating passcodes.
 */
public interface PasscodeGenerator {

  /**
   * The default length of the passcode.
   */
  int DEFAULT_LENGTH = 6;

  /**
   * Creates a passcode with the given length and passes it to the specified locker.
   * Its length is determined by the default length.
   *
   * @return passcode is passed to the specified locker
   * @throws IllegalParameterException if the length is not within the allowed range
   */
  int createPasscode() throws IllegalParameterException;

}