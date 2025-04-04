package edu.ntnu.stud.model;

/**
 * Represents a message in the system.
 */
public class MessageRequest {
  private String listingId;
  private Long sellerId;
  private Long byerId;
  private String message;
  private boolean sentByBuyer;

  /**
   * Default constructor.
   */
  public MessageRequest() {
  }

  /**
   * Constructs a new Message with the specified details.
   *
   * @param listingId the ID of the listing
   * @param sellerId    the ID of the buyer
   * @param byerId    the ID of the buyer
   * @param message   the message content
   * @param sentByBuyer indicates if the message was sent by the buyer
   */
  public MessageRequest(
        String listingId, 
        long sellerId, 
        long byerId, 
        String message, 
        boolean sentByBuyer
  ) {
    this.listingId = listingId;
    this.byerId = byerId;
    this.message = message;
    this.sentByBuyer = sentByBuyer;
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
  public Long getByerId() {
    return byerId;
  }

  /**
   * Sets the ID of the buyer associated with the message.
   *
   * @param byerId the buyer ID to set.
   */
  public void setByerId(Long byerId) {
    this.byerId = byerId;
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
   * @param sentByBuyer true if the message was sent by the buyer, false otherwise.
   */
  public void setSentByBuyer(boolean sentByBuyer) {
    this.sentByBuyer = sentByBuyer;
  }
}