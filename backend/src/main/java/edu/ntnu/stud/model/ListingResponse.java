package edu.ntnu.stud.model;

import java.sql.Timestamp;

/**
 * Data Transfer Object (DTO) for Listing responses.
 */
public class ListingResponse {
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
  private Long buyerId;
  private long ownerId;
  private Timestamp createdAt;
  private Timestamp updatedAt;

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

  public Long getBuyerId() {
    return buyerId;
  }

  public void setBuyerId(Long buyerId) {
    this.buyerId = buyerId;
  }

  public long getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(long ownerId) {
    this.ownerId = ownerId;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  public Timestamp getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Timestamp updatedAt) {
    this.updatedAt = updatedAt;
  }
}