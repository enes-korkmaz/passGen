package de.hhn.it.devtools.apis.exceptions;

/**
 * Gets thrown when the user provides an address that isn't registered or the password does
 * not match the given address.
 */
public class WrongLoginCredentialsException extends Exception {
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(WrongLoginCredentialsException.class);

  /**
   * Instantiates a new Wrong login credentials exception.
   *
   * @param message the message
   */
  public WrongLoginCredentialsException(final String message) {
    super(message);
  }
}