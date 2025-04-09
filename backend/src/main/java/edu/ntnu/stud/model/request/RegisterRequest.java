package edu.ntnu.stud.model.request;

/**
 * Represents a registration request in the system.
 * This class contains the username and password provided by the user during registration.
 */
public class RegisterRequest {
  private String username;
  private String password;

  public RegisterRequest(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}