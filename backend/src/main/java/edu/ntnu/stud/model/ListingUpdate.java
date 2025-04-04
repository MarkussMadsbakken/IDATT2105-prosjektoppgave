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
  private int postalCode;
  private boolean active;
  private boolean deleted;
  private boolean sold;

  /**
   * Default constructor for ListingUpdate.
   */
  public ListingUpdate() {
  }

  /**
   * Constructs a new ListingRequest with the specified details.
   *
   * @param uuid the UUID of the listing
   * @param name the name of the listing
   * @param price the price of the listing
   * @param description the description of the listing
   * @param category the category of the listing
   * @param subcategory a list of subcategory of the listing
   * @param postalCode the postal code of the listing
   * @param active the active status of the listing
   * @param deleted the deleted status of the listing
   * @param sold the sold status of the listing
   */
  public ListingUpdate(
      String uuid,
      String name,
      double price,
      String description,
      int category,
      int subcategory,
      int postalCode,
      boolean active,
      boolean deleted,
      boolean sold
  ) {
    this.uuid = uuid;
    this.name = name;
    this.price = price;
    this.description = description;
    this.category = category;
    this.subcategory = subcategory;
    this.postalCode = postalCode;
    this.active = active;
    this.deleted = deleted;
    this.sold = sold;
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

  public int getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(int postalCode) {
    this.postalCode = postalCode;
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
