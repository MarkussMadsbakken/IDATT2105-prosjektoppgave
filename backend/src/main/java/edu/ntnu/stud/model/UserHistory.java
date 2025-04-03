package edu.ntnu.stud.model;

import java.sql.Timestamp;

/**
 * This class represents the history of what listings a user has viewed.
 * It contains the user's ID, the listing ID, and the timestamp of when the listing was viewed.
 */
public class UserHistory {

  private long id;
  private long userId;
  private Timestamp timestamp;
  private String listingId;

  /**
   * Default constructor for UserHistory.
   */
  public UserHistory() {
    // Default constructor
  }

  /**
   * Constructor for UserHistory.
   */
  public UserHistory(long id, long userId, Timestamp timestamp, String listingId) {
    this.id = id;
    this.userId = userId;
    this.timestamp = timestamp;
    this.listingId = listingId;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }

  public String getListingId() {
    return listingId;
  }

  public void setListingId(String listingId) {
    this.listingId = listingId;
  }

}
