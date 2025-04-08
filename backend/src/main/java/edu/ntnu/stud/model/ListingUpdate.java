package edu.ntnu.stud.model;

/**
 * Represents a request to update a listing.
 * This class contains the necessary information for a listing, including
 * name, price, description, category, subcategory, postal code,
 * and status flags (active, deleted, sold).
 */
public class ListingUpdate {
  private String uuid;
  private String name;
  private double price;
  private String description;
  private int category;
  private int subcategory;
  private long longitude;
  private long latitude;
  private boolean active;
  private boolean deleted;
  private boolean sold;
  private Long buyerId;

  /**
   * Default constructor for ListingUpdate.
   */
  public ListingUpdate() {
  }

  /**
   * Constructs a new ListingRequest with the specified details.
   *
   * @param uuid        the UUID of the listing
   * @param name        the name of the listing
   * @param price       the price of the listing
   * @param description the description of the listing
   * @param category    the category of the listing
   * @param subcategory a list of subcategory of the listing
   * @param longitude   the longitude of the listing
   * @param latitude    the latitude of the listing
   * @param active      the active status of the listing
   * @param deleted     the deleted status of the listing
   * @param sold        the sold status of the listing
   * @param buyerId     the ID of the buyer of the listing
   */
  public ListingUpdate(
      String uuid,
      String name,
      double price,
      String description,
      int category,
      int subcategory,
      long longitude,
      long latitude,
      boolean active,
      boolean deleted,
      boolean sold,
      Long buyerId) {
    this.uuid = uuid;
    this.name = name;
    this.price = price;
    this.description = description;
    this.category = category;
    this.subcategory = subcategory;
    this.longitude = longitude;
    this.latitude = latitude;
    this.active = active;
    this.deleted = deleted;
    this.sold = sold;
    this.buyerId = buyerId;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getCategory() {
    return category;
  }

  public void setCategory(int category) {
    this.category = category;
  }

  public int getSubcategory() {
    return subcategory;
  }

  public void setSubcategory(int subcategory) {
    this.subcategory = subcategory;
  }

  public long getLongitude() {
    return longitude;
  }

  public void setLongitude(long longitude) {
    this.longitude = longitude;
  }

  public long getLatitude() {
    return latitude;
  }

  public void setLatitude(long latitude) {
    this.latitude = latitude;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }

  public boolean isSold() {
    return sold;
  }

  public void setSold(boolean sold) {
    this.sold = sold;
  }

  public Long getBuyerId() {
    return buyerId;
  }

  public void setBuyerId(Long buyerId) {
    this.buyerId = buyerId;
  }
}
