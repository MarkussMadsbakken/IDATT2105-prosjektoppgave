package edu.ntnu.stud.model.request;

/**
 * Represents a request to create or update a listing.
 * This class contains the necessary information for a listing, including
 * name, price, description, category, subcategory, postal code,
 * and status flags (active, deleted, sold).
 */
public class ListingRequest {
  private String name;
  private double price;
  private String description;
  private int category;
  private Integer subcategory;
  private double longitude;
  private double latitude;
  private boolean active;
  private boolean deleted;
  private boolean sold;

  /**
   * Constructs a new ListingRequest with the specified details.
   *
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
   */
  public ListingRequest(
      String name,
      double price,
      String description,
      int category,
      Integer subcategory,
      double longitude,
      double latitude,
      boolean active,
      boolean deleted,
      boolean sold) {
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

  public Integer getSubcategory() {
    return subcategory;
  }

  public void setSubcategory(Integer subcategory) {
    this.subcategory = subcategory;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
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
}
