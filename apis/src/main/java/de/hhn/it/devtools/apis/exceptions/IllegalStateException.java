package de.hhn.it.devtools.apis.exceptions;

/**
 * This should be implemented as a runtime exception.
 */
public class IllegalStateException extends RuntimeException {

  /**
   * Instantiates a new illegal State  exception.
   *
   * @param message the message
   */
  public IllegalStateException(final String message) {
    super(message);
  }
}