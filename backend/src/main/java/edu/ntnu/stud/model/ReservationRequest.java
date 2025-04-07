package edu.ntnu.stud.model;

/**
 * Represents a request to reserve a listing.
 */
public class ReservationRequest {
  private String listingId;

  public ReservationRequest() {
  }

  public ReservationRequest(String listingId) {
    this.listingId = listingId;
  }

  public String getListingId() {
    return listingId;
  }

  public void setListingId(String listingId) {
    this.listingId = listingId;
  }
}
