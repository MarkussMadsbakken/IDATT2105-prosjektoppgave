package edu.ntnu.stud.model;

import java.sql.Timestamp;

/**
 * Represents a notification in the system.
 * This class contains details about the notification such as the user ID, type,
 * and content.
 */
public class Notification {

  private long id;
  private long userId;
  private boolean isRead;
  private Timestamp time;
  private String link;
  private String message;

  /**
   * Default constructor.
   */
  public Notification() {
  }

  /**
   * Constructs a new Notification with the specified details.
   */
  public Notification(long userId, String message, String link, boolean isRead, Timestamp time) {
    this.userId = userId;
    this.message = message;
    this.link = link;
    this.isRead = isRead;
    this.time = time;
  }

  /**
   * Returns the ID of the notification.
   *
   * @return the ID of the notification
   */
  public long getId() {
    return id;
  }

  /**
   * Sets the ID of the notification.
   *
   * @param id the ID to set
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Returns the user ID associated with the notification.
   *
   * @return the user ID
   */
  public long getUserId() {
    return userId;
  }

  /**
   * Sets the user ID associated with the notification.
   *
   * @param userId the user ID to set
   */
  public void setUserId(long userId) {
    this.userId = userId;
  }

  /**
   * Returns whether the notification has been read.
   *
   * @return true if the notification is read, false otherwise
   */
  public boolean isRead() {
    return isRead;
  }

  /**
   * Sets the read status of the notification.
   *
   * @param isRead the read status to set
   */
  public void setRead(boolean isRead) {
    this.isRead = isRead;
  }

  /**
   * Returns the time the notification was created.
   *
   * @return the creation time
   */
  public Timestamp getTime() {
    return time;
  }

  /**
   * Sets the time the notification was created.
   *
   * @param time the creation time to set
   */
  public void setTime(Timestamp time) {
    this.time = time;
  }

  /**
   * Returns the link associated with the notification.
   *
   * @return the link
   */
  public String getLink() {
    return link;
  }

  /**
   * Sets the link associated with the notification.
   *
   * @param link the link to set
   */
  public void setLink(String link) {
    this.link = link;
  }

  /**
   * Returns the message of the notification.
   *
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  /**
   * Sets the message of the notification.
   *
   * @param message the message to set
   */
  public void setMessage(String message) {
    this.message = message;
  }

}
