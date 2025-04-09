package edu.ntnu.stud.model.base;

/**
 * Represents a chat between a buyer and a seller for a specific listing.
 */

public class Chat {
  private long id;
  private String listingId;
  private long buyerId;
  private long sellerId;

  /**
   * Default constructor for Chat.
   */
  public Chat() {
  }

  /**
   * Constructs a new Chat with the specified details.
   */
  public Chat(long chatId, String listingId, long buyerId, long sellerId) {
    this.id = chatId;
    this.listingId = listingId;
    this.buyerId = buyerId;
    this.sellerId = sellerId;
  }

  public long getChatId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getListingId() {
    return listingId;
  }

  public void setListingId(String listingId) {
    this.listingId = listingId;
  }

  public long getBuyerId() {
    return buyerId;
  }

  public void setBuyerId(long buyerId) {
    this.buyerId = buyerId;
  }

  public long getSellerId() {
    return sellerId;
  }

  public void setSellerId(long sellerId) {
    this.sellerId = sellerId;
  }
}
