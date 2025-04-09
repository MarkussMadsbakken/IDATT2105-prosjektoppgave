package edu.ntnu.stud.model.base;

import java.sql.Timestamp;

/**
 * Represents a reservation of a listing in the system.
 */
public class Reservation {
  private Long id;
  private String listingId;
  private Long userId;
  private Timestamp createdAt;

  public Reservation() {
  }

  /**
   * Constructs a new Reservation with the specified details.
   *
   * @param id the ID of the reservation
   * @param listingId the ID of the listing reserved
   * @param buyerId the ID of the user who made the reservation
   * @param createdAt the timestamp when the reservation was created
   */
  public Reservation(Long id, String listingId, Long buyerId, Timestamp createdAt) {
    this.id = id;
    this.listingId = listingId;
    this.userId = buyerId;
    this.createdAt = createdAt;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getListingId() {
    return listingId;
  }

  public void setListingId(String listingId) {
    this.listingId = listingId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long buyerId) {
    this.userId = buyerId;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }
}