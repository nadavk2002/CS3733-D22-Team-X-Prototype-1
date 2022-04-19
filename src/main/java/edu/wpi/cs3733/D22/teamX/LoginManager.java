package edu.wpi.cs3733.D22.teamX;

import java.util.HashMap;
import java.util.Map;

// Singleton
public class LoginManager {
  private static final Map<String, Integer> logins =
      new HashMap<String, Integer>(); // <username, password.hashCode()>

  private LoginManager() {
    // Only the hashcode of the password is stored.
    // The user's input is hashed and checked against the Integer stored in the value slot
    logins.put("admin", "admin".hashCode()); // default admin login
    logins.put("staff", "staff".hashCode()); // default employee login
  }

  private static class LoginManagerHelper {
    private static final LoginManager loginMan = new LoginManager();
  }

  public static LoginManager getInstance() {
    return LoginManagerHelper.loginMan;
  }

  public Map<String, Integer> getLogins() {
    return logins;
  }

  /**
   * Adds a new account to the manager
   *
   * @param username the username of the account
   * @param hashedPassword the hashcode of the password being registered for that account
   */
  public void addLogin(String username, Integer hashedPassword) {
    if(logins.containsKey(username)) {
      System.out.println("username already taken");
    }
    else {
      logins.put(username, hashedPassword);
    }
  }

  //make method to update a login

  /**
   * Checks if the username and password are stored in logins
   *
   * @param username the username of the account being logged in to
   * @param hashedPassword the hashcode of the password being used to log in
   * @return true if the username is stored and the password is correct
   */
  public boolean isValidLogin(String username, Integer hashedPassword) {
    if (!logins.containsKey(username)) {
      System.out.println("Invalid username");
      return false;
    }
    int hashedPwd = logins.get(username);
    if (hashedPwd != hashedPassword) {
      System.out.println("Invalid password");
      return false;
    }
    return true;
  }
}
