package edu.ntnu.stud.model;

/**
 * This class represents a bookmark in the application.
 */
public class Bookmark {
  private long userId; // ID of the user who created the bookmark
  private String listingId; // ID of the listing that is bookmarked

  /**
   * Constructor to create a new Bookmark object.
   *
   * @param userId the ID of the user who created the bookmark
   * @param listingId the ID of the listing that is bookmarked
   */
  public Bookmark(long userId, String listingId) {
    this.userId = userId;
    this.listingId = listingId;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public String getListingId() {
    return listingId;
  }

  public void setListingId(String listingId) {
    this.listingId = listingId;
  }
}
