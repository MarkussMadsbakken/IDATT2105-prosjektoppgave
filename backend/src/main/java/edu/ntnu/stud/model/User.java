package edu.ntnu.stud.model;

import java.sql.Blob;

/**
 * Represents a user in the system.
 * This class contains user details such as username, password, first name, last
 * name, creation date, and admin status.
 */
public class User {

  private long id;

  private String username;

  private String password;

  private String firstName;

  private String lastName;

  private String createdAt;

  private boolean isAdmin;

  private Blob imageBlob;

  private String imageFileType;

  /**
   * Default constructor.
   */
  public User() {
  }

  /**
   * Constructs a new User with the specified details.
   *
   * @param username the username of the user
   * @param password the password of the user
   */
  public User(String username, String password, String firstName, String lastName) {
    this.username = username;
    this.password = password;
  }

  /**
   * Returns the ID of the user.
   *
   * @return the ID of the user
   */
  public long getId() {
    return id;
  }

  /**
   * Sets the ID of the user.
   *
   * @param id the ID to set
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Returns the username of the user.
   *
   * @return the username of the user
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the username of the user.
   *
   * @param username the username to set
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Returns the password of the user.
   *
   * @return the password of the user
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the password of the user.
   *
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Returns the first name of the user.
   *
   * @return the first name of the user
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Sets the first name of the user.
   *
   * @param firstName the first name to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Returns the last name of the user.
   *
   * @return the last name of the user
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Sets the last name of the user.
   *
   * @param lastName the last name to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Returns the creation date of the user.
   *
   * @return the creation date of the user
   */
  public String getCreatedAt() {
    return createdAt;
  }

  /**
   * Sets the creation date of the user.
   *
   * @param createdAt the creation date to set
   */
  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Returns whether the user is an admin.
   *
   * @return true if the user is an admin, false otherwise
   */
  public boolean isAdmin() {
    return isAdmin;
  }

  /**
   * Sets the admin status of the user.
   *
   * @param admin the admin status to set
   */
  public void setAdmin(boolean admin) {
    isAdmin = admin;
  }

  /**
   * Returns the image blob of the user.
   *
   * @return the image blob of the user
   */
  public Blob getImageBlob() {
    return imageBlob;
  }

  /**
   * Sets the image blob of the user.
   *
   * @param imageBlob the image blob to set
   */
  public void setImageBlob(Blob imageBlob) {
    this.imageBlob = imageBlob;
  }

  /**
   * Returns the image file type of the user.
   *
   * @return the image file type of the user
   */
  public String getImageFileType() {
    return imageFileType;
  }

  /**
   * Sets the image file type of the user.
   *
   * @param imageFileType the image file type to set
   */
  public void setImageFileType(String imageFileType) {
    this.imageFileType = imageFileType;
  }
}
