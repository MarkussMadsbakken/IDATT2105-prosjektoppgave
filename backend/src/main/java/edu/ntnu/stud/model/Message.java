package edu.ntnu.stud.model;

import java.sql.Timestamp;

/**
 * Represents a message in the system.
 */
public class Message {
  private long id;
  private String listingId;
  private Long sellerId;
  private Long buyerId;
  private String message;
  private Timestamp createdAt;
  private boolean sentByBuyer;

  /**
   * Default constructor.
   */
  public Message() {
  }

  /**
   * Constructs a new Message with the specified details.
   *
   * @param listingId   the ID of the listing
   * @param buyerId     the ID of the buyer
   * @param message     the message content
   * @param createdAt   the creation date of the message
   * @param sentByBuyer indicates if the message was sent by the buyer
   */
  public Message(
      String listingId, Long buyerId, String message, Timestamp createdAt, boolean sentByBuyer) {
    this.listingId = listingId;
    this.buyerId = buyerId;
    this.message = message;
    this.createdAt = createdAt;
    this.sentByBuyer = sentByBuyer;
  }

  /**
   * Retrieves the unique identifier of the message.
   *
   * @return the ID of the message as a long.
   */
  public long getId() {
    return id;
  }

  /**
   * Sets the unique identifier of the message.
   *
   * @param id the ID to set for the message.
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Retrieves the ID of the listing associated with the message.
   *
   * @return the listing ID as a String.
   */
  public String getListingId() {
    return listingId;
  }

  /**
   * Sets the ID of the listing associated with the message.
   *
   * @param listingId the listing ID to set.
   */
  public void setListingId(String listingId) {
    this.listingId = listingId;
  }

  /**
   * Retrieves the ID of the seller associated with the message.
   *
   * @return the seller ID as a Long.
   */
  public Long getSellerId() {
    return sellerId;
  }

  /**
   * Sets the ID of the seller associated with the message.
   *
   * @param sellerId the seller ID to set.
   */
  public void setSellerId(Long sellerId) {
    this.sellerId = sellerId;
  }

  /**
   * Retrieves the ID of the buyer associated with the message.
   *
   * @return the buyer ID as a Long.
   */
  public Long getbuyerId() {
    return buyerId;
  }

  /**
   * Sets the ID of the buyer associated with the message.
   *
   * @param buyerId the buyer ID to set.
   */
  public void setbuyerId(Long buyerId) {
    this.buyerId = buyerId;
  }

  /**
   * Retrieves the content of the message.
   *
   * @return the message content as a String.
   */
  public String getMessage() {
    return message;
  }

  /**
   * Sets the content of the message.
   *
   * @param message the message content to set.
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * Retrieves the creation date of the message.
   *
   * @return the creation date as a String.
   */
  public Timestamp getCreatedAt() {
    return createdAt;
  }

  /**
   * Sets the creation date of the message.
   *
   * @param createdAt the creation date to set.
   */
  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Checks if the message was sent by the buyer.
   *
   * @return true if the message was sent by the buyer, false otherwise.
   */
  public boolean isSentByBuyer() {
    return sentByBuyer;
  }

  /**
   * Sets whether the message was sent by the buyer.
   *
   * @param sentByBuyer true if the message was sent by the buyer, false
   *                    otherwise.
   */
  public void setSentByBuyer(boolean sentByBuyer) {
    this.sentByBuyer = sentByBuyer;
  }
}