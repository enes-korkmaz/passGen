package de.hhn.it.devtools.apis.exceptions;

/**
 * This class represents an exception that is thrown when the user tries to generate
 * a token while still having an active one.
 */
public class TooManyTokensException extends Exception {
    private static final org.slf4j.Logger logger =
            org.slf4j.LoggerFactory.getLogger(TooManyTokensException.class);

    /**
     * Creates a new TooManyTokensException with the given message.
     *
     * @param message the message of the exception
     */
    public TooManyTokensException(String message) {
        super(message);
    }
}