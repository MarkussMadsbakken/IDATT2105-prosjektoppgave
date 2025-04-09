package edu.ntnu.stud.model;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * Represents a listing in the system.
 * This class contains details about a listing such as name,
 * price, description, category, postal code, and owner ID.
 */
public class Listing {
  private String uuid;
  private String name;
  private double price;
  private String description;
  private Timestamp createdAt;
  private Timestamp updatedAt;
  private List<Blob> pictures;
  private int category;
  private int subcategory;
  private double longitude;
  private double latitude;
  private boolean active;
  private boolean deleted;
  private boolean sold;
  private Long buyerId;
  private Long ownerId;

  /**
   * Constructs a new Listing with default values.
   */
  public Listing() {
    this.uuid = UUID.randomUUID().toString();
  }

  /**
   * Constructs a new Listing with the specified details.
   *
   * @param name        the name of the listing
   * @param price       the price of the listing
   * @param description the description of the listing
   * @param category    the category of the listing
   * @param subcategory the subcategory of the listing
   * @param longitude   the longitude of the listing
   * @param latitude    the latitude of the listing
   * @param ownerId     the ID of the owner of the listing
   */
  public Listing(
      String name,
      double price,
      String description,
      int category,
      int subcategory,
      long longitude,
      long latitude,
      Long ownerId) {
    this.uuid = UUID.randomUUID().toString();
    this.name = name;
    this.price = price;
    this.description = description;
    this.category = category;
    this.subcategory = subcategory;
    this.latitude = latitude;
    this.longitude = longitude;
    this.ownerId = ownerId;
  }

  /**
   * Returns the uuid of the listing.
   *
   * @return the uuid of the listing
   */
  public String getUuid() {
    return uuid;
  }

  /**
   * Sets the uuid of the listing.
   *
   * @param uuid the uuid to set
   */
  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  /**
   * Returns the name of the listing.
   *
   * @return the name of the listing
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the listing.
   *
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns the price of the listing.
   *
   * @return the price of the listing
   */
  public double getPrice() {
    return price;
  }

  /**
   * Sets the price of the listing.
   *
   * @param price the price to set
   */
  public void setPrice(double price) {
    this.price = price;
  }

  /**
   * Returns the description of the listing.
   *
   * @return the description of the listing
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description of the listing.
   *
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Returns the creation timestamp of the listing.
   *
   * @return the creation timestamp of the listing
   */
  public Timestamp getCreatedAt() {
    return createdAt;
  }

  /**
   * Sets the creation timestamp of the listing.
   *
   * @param createdAt the creation timestamp to set
   */
  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Returns the last updated timestamp of the listing.
   *
   * @return the last updated timestamp of the listing
   */
  public Timestamp getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Sets the last updated timestamp of the listing.
   *
   * @param updatedAt the last updated timestamp to set
   */
  public void setUpdatedAt(Timestamp updatedAt) {
    this.updatedAt = updatedAt;
  }

  /**
   * Returns the pictures of the listing.
   *
   * @return the pictures of the listing
   */
  public List<Blob> getPictures() {
    return pictures;
  }

  /**
   * Sets the pictures of the listing.
   *
   * @param pictures the pictures to set
   */
  public void setPictures(List<Blob> pictures) {
    this.pictures = pictures;
  }

  /**
   * Returns the category of the listing.
   *
   * @return the category of the listing
   */
  public int getCategory() {
    return category;
  }

  /**
   * Sets the category of the listing.
   *
   * @param category the category to set
   */
  public void setCategory(int category) {
    this.category = category;
  }

  /**
   * Returns the subcategory of the listing.
   *
   * @return the subcategory of the listing
   */
  public int getSubcategory() {
    return subcategory;
  }

  /**
   * Sets the subcategory of the listing.
   *
   * @param subcategory the subcategory to set
   */
  public void setSubcategory(int subcategory) {
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

  /**
   * Sets the latitude of the listing.
   *
   * @param latitude the latitude to set
   */
  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  /**
   * Returns whether the listing is active.
   *
   * @return true if the listing is active, false otherwise
   */
  public boolean isActive() {
    return active;
  }

  /**
   * Sets whether the listing is active.
   *
   * @param active the active status to set
   */
  public void setActive(boolean active) {
    this.active = active;
  }

  /**
   * Returns whether the listing is deleted.
   *
   * @return true if the listing is deleted, false otherwise
   */
  public boolean isDeleted() {
    return deleted;
  }

  /**
   * Sets whether the listing is deleted.
   *
   * @param deleted the deleted status to set
   */
  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }

  /**
   * Returns whether the listing is sold.
   *
   * @return true if the listing is sold, false otherwise
   */
  public boolean isSold() {
    return sold;
  }

  /**
   * Sets whether the listing is sold.
   *
   * @param sold the sold status to set
   */
  public void setSold(boolean sold) {
    this.sold = sold;
  }

  /**
   * Returns the ID of the buyer of the listing.
   *
   * @return the ID of the buyer of the listing
   */
  public Long getBuyerId() {
    return buyerId;
  }

  /**
   * Sets the ID of the buyer of the listing.
   *
   * @param buyerId the buyer ID to set
   */
  public void setBuyerId(Long buyerId) {
    this.buyerId = buyerId;
  }

  /**
   * Returns the ID of the owner of the listing.
   *
   * @return the ID of the owner of the listing
   */
  public Long getOwnerId() {
    return ownerId;
  }

  /**
   * Sets the ID of the owner of the listing.
   *
   * @param ownerId the owner ID to set
   */
  public void setOwnerId(Long ownerId) {
    this.ownerId = ownerId;
  }
}