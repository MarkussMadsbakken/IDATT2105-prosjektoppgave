package edu.ntnu.stud.model;

public class CreateChatRequest {

  private String listingId;
  private long buyerId;
  private long sellerId;

  /**
   * Default constructor for CreateChatRequest.
   */
  public CreateChatRequest() {
  }

  /**
   * Constructs a new CreateChatRequest with the specified details.
   *
   * @param listingId the ID of the listing
   * @param buyerId   the ID of the buyer
   * @param sellerId  the ID of the seller
   */
  public CreateChatRequest(String listingId, long buyerId, long sellerId) {
    this.listingId = listingId;
    this.buyerId = buyerId;
    this.sellerId = sellerId;
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
