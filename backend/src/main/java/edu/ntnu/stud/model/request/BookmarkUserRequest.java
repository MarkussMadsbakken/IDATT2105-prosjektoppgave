package edu.ntnu.stud.model.request;

/**
 * This class represents a request to bookmark a listing in the application.
 */
public class BookmarkUserRequest {
  private String listingId; // ID of the listing that is bookmarked

  /**
   * Constructor to create a new BookmarkUserRequest object.
   *
   * @param listingId the ID of the listing that is bookmarked
   */
  public BookmarkUserRequest(String listingId) {
    this.listingId = listingId;
  }

  public String getListingId() {
    return listingId;
  }

  public void setListingId(String listingId) {
    this.listingId = listingId;
  }
}
