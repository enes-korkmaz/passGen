package de.hhn.it.devtools.components.passGen.provider;


import de.hhn.it.devtools.apis.exceptions.WrongLoginCredentialsException;
import de.hhn.it.devtools.apis.passGen.User;
import de.hhn.it.devtools.apis.passGen.UserManagementService;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


/**
 * A simple implementation of the UserManagementService interface.
 */
public class SimpleUserManagementService implements UserManagementService {
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(SimpleUserManagementService.class);
  private HashMap<Integer, SimpleUser> users = new HashMap<>();

  /**
   * Creates a new user with the specified address and password.
   *
   * @param address  the address of the user
   * @param password the password of the user
   * @return the id of the new user
   */
  public int createUser(String address, String password, boolean isUser) {
    if (address == null || password == null) {
      throw new IllegalArgumentException("Address or password cannot be null.");
    }
    int newId = users.size() + 1;
    SimpleUser user = new SimpleUser(address, password, newId, isUser);
    logger.debug("Created user: {} with role: {}", address, isUser ? "User" : "Admin");
    users.put(newId, user);
    return newId;
  }

  /**
   * Logs in a user with the specified address and password.
   *
   * @param address  E-Mail-Address of the user
   * @param password key to log in with specified addess
   * @throws IllegalArgumentException       if the address or password is null
   * @throws WrongLoginCredentialsException if the login credentials are incorrect
   */
  @Override
  public void login(String address, String password) throws IllegalArgumentException,
          WrongLoginCredentialsException {
    if (address == null || password == null) {
      throw new IllegalArgumentException("Address or password cannot be null.");
    }
    for (SimpleUser user : users.values()) {
      if (user.getAddress().equals(address) && user.getPassword().equals(password)) {
        user.setToken(new SimpleToken(
                UUID.randomUUID().toString(), Map.of(), Map.of(), this));
        return;
      }
    }
    throw new WrongLoginCredentialsException("Login credentials are incorrect.");
  }

  /**
   * Getter for the users.
   *
   * @return the users
   */
  public HashMap<Integer, SimpleUser> getUsers() {
    return users;
  }

  /**
   * Getter for one specific user.
   *
   * @param userId Id of the user
   * @return the user
   * @throws IllegalArgumentException if the user is not found
   */
  @Override
  public SimpleUser getUser(int userId) throws IllegalArgumentException {
    if (!users.containsKey(userId)) {
      throw new IllegalArgumentException("No user found with the given Id.");
    }
    return users.get(userId);
  }

  /**
   * Getter for a user by address.
   *
   * @param address of the user
   * @return the user
   */
  public SimpleUser getUserByAddress(String address) {
    for (SimpleUser user : users.values()) {
      if (Objects.equals(user.getAddress(), address)) {
        return user;
      }
    }
    return null;
  }

  /**
   * Removes a user from the user management service.
   *
   * @throws IllegalArgumentException if the address or password is null
   */
  public void removeUser(int userId) throws IllegalArgumentException {
    if (!users.containsKey(userId)) {
      throw new IllegalArgumentException("No user found with the given Id.");
    }
    users.remove(userId);
  }


  /**
   * Changes the password of a user with the specified address.
   *
   * @param address     the address of the user
   * @param newPassword the new password
   * @throws IllegalArgumentException if the address or new password is null
   */
  @Override
  public void changePassword(String address, String newPassword) throws IllegalArgumentException {
    if (address == null || newPassword == null) {
      throw new IllegalArgumentException("Address or new password cannot be null.");
    }
    User user = users.get(address);
    if (user == null) {
      throw new IllegalArgumentException("No user found with the given address.");
    }
    ((SimpleUser) user).setPassword(newPassword);
  }

  /**
   * Checks if the login credentials are correct.
   *
   * @param address  E-Mail-Address
   * @param password key to log in with specified address
   * @return true if the credentials are correct, false otherwise
   */
  @Override
  public boolean checkCredentials(String address, String password) {
    if (address == null || password == null) {
      throw new IllegalArgumentException("Address or password cannot be null.");
    }
    for (User user : users.values()) {
      if (Objects.equals(user.getAddress(), address)
          && Objects.equals(user.getPassword(), password)) {
        return true;
      }
    }
    return false;
  }
}