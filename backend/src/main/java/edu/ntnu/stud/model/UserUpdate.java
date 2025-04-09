package edu.ntnu.stud.model;

/**
 * Represents a UserUpdate in the system.
 */
public class UserUpdate {
  private String firstName;
  private String lastName;

  /**
   * Constructs a new UserUpdate with the specified details.
   *
   * @param firstName the first name of the UserUpdate
   * @param lastName  the last name of the UserUpdate
   */
  public UserUpdate(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
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
