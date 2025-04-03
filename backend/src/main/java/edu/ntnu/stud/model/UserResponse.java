package edu.ntnu.stud.model;

/**
 * Represents a UserResponse in the system.
 * This class contains UserResponse details such as username, first name, last
 * name, creation date, and admin status.
 */
public class UserResponse {

  private long id;

  private String username;

  private String firstName;

  private String lastName;

  private String createdAt;

  private boolean isAdmin;

  /**
   * Default constructor.
   */
  public UserResponse() {
  }

  /**
   * Constructs a new UserResponse with the specified details.
   *
   * @param username the username of the UserResponse
   * @param firstName the first name of the UserResponse
   * @param lastName the last name of the UserResponse
   * @param createdAt the creation date of the UserResponse
   * @param isAdmin the admin status of the UserResponse
   */
  public UserResponse(String username, String firstName, String lastName, String createdAt,
      boolean isAdmin) {
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.createdAt = createdAt;
    this.isAdmin = isAdmin;
  }

  /**
   * Constructs a new UserResponse from a User object.
   *
   * @param user the User object to create the UserResponse from
   */
  public UserResponse(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.createdAt = user.getCreatedAt();
    this.isAdmin = user.isAdmin();
  }

  /**
   * Returns the ID of the UserResponse.
   *
   * @return the ID of the UserResponse
   */
  public long getId() {
    return id;
  }

  /**
   * Sets the ID of the UserResponse.
   *
   * @param id the ID to set
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Returns the username of the UserResponse.
   *
   * @return the username of the UserResponse
   */
  public String getusername() {
    return username;
  }

  /**
   * Sets the username of the UserResponse.
   *
   * @param username the username to set
   */
  public void setusername(String username) {
    this.username = username;
  }

  /**
   * Returns the first name of the UserResponse.
   *
   * @return the first name of the UserResponse
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Sets the first name of the UserResponse.
   *
   * @param firstName the first name to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Returns the last name of the UserResponse.
   *
   * @return the last name of the UserResponse
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Sets the last name of the UserResponse.
   *
   * @param lastName the last name to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Returns the creation date of the UserResponse.
   *
   * @return the creation date of the UserResponse
   */
  public String getCreatedAt() {
    return createdAt;
  }

  /**
   * Sets the creation date of the UserResponse.
   *
   * @param createdAt the creation date to set
   */
  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Returns whether the UserResponse is an admin.
   *
   * @return true if the UserResponse is an admin, false otherwise
   */
  public boolean isAdmin() {
    return isAdmin;
  }

  /**
   * Sets the admin status of the UserResponse.
   *
   * @param admin the admin status to set
   */
  public void setAdmin(boolean admin) {
    isAdmin = admin;
  }
}
