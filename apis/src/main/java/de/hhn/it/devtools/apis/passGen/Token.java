package de.hhn.it.devtools.apis.passGen;

import de.hhn.it.devtools.apis.exceptions.TooManyTokensException;

/**
 * Interface for generating and removing tokens.
 */
public interface Token {

  /**
   * Removes the token associated with the specified user ID.
   *
   * @param userId the ID of the user whose token is to be removed
   */
  public void removeToken(int userId);

  /**
   * Generates a new token for the specified user ID.
   *
   * @param userId the ID of the user for whom the token is to be generated
   * @throws IllegalArgumentException if the user ID is less than or equal to zero
   * @throws TooManyTokensException   if the user has already an active token assigned
   */
  void generateToken(int userId) throws IllegalArgumentException, TooManyTokensException;
}