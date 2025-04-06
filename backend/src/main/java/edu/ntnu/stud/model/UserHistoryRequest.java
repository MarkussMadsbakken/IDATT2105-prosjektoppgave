package edu.ntnu.stud.model;

import java.sql.Timestamp;

/**
 * This class represents a request to add a user history entry.
 */
public class UserHistoryRequest {
  private String listingId;

  /**
   * Default constructor for UserHistoryRequest.
   */
  public UserHistoryRequest() {
    // Default constructor
  }

  /**
   * Constructor for UserHistoryRequest.
   *
   * @param listingId the ID of the listing viewed by the user
   */
  public UserHistoryRequest(Timestamp timestamp, String listingId) {
    this.listingId = listingId;
  }

  public String getListingId() {
    return listingId;
  }

  public void setListingId(String listingId) {
    this.listingId = listingId;
  }
}
