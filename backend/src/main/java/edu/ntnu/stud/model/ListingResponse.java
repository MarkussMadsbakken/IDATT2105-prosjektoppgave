package edu.ntnu.stud.model;

import java.util.List;

/**
 * Data Transfer Object (DTO) for Listing responses.
 */
public class ListingResponse {
  private String uuid;
  private String name;
  private double price;
  private String description;
  private String category;
  private List<String> subcategories;
  private int postalCode;
  private boolean active;
  private boolean deleted;
  private boolean sold;
  private long ownerId;

  // Getters and setters
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

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public List<String> getSubcategories() {
    return subcategories;
  }

  public void setSubcategories(List<String> subcategories) {
    this.subcategories = subcategories;
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

  public long getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(long ownerId) {
    this.ownerId = ownerId;
  }
}