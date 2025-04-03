package edu.ntnu.stud.model;

/**
 * Represents a UserUpdate in the system.
 */
public class UserUpdate {
  private String username;
  private String firstName;
  private String lastName;

  public UserUpdate() {
  }

  /**
   * Constructs a new UserUpdate with the specified details.
   *
   * @param username  the username of the UserUpdate
   * @param firstName the first name of the UserUpdate
   * @param lastName  the last name of the UserUpdate
   */
  public UserUpdate(String username, String firstName, String lastName) {
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
}
