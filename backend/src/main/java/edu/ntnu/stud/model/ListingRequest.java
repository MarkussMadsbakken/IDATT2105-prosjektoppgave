package edu.ntnu.stud.model;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * Represents a request to create or update a listing.
 * This class contains the necessary information for a listing, including
 * name, price, description, pictures, category, subcategories, postal code,
 * and status flags (active, deleted, sold).
 */
public class ListingRequest {
  private String name;
  private double price;
  private String description;
  private List<MultipartFile> pictures;
  private String category;
  private List<String> subcategories;
  private int postalCode;
  private boolean active;
  private boolean deleted;
  private boolean sold;


  /**
   * Constructs a new ListingRequest with the specified details.
   *
   * @param name the name of the listing
   * @param price the price of the listing
   * @param description the description of the listing
   * @param pictures a list of MultipartFiles representing the pictures of the listing
   * @param category the category of the listing
   * @param subcategories a list of subcategories of the listing
   * @param postalCode the postal code of the listing
   * @param active the active status of the listing
   * @param deleted the deleted status of the listing
   * @param sold the sold status of the listing
   */
  public ListingRequest(
      String name,
      double price,
      String description,
      List<MultipartFile> pictures,
      String category,
      List<String> subcategories,
      int postalCode,
      boolean active,
      boolean deleted,
      boolean sold
  ) {
    this.name = name;
    this.price = price;
    this.description = description;
    this.pictures = pictures;
    this.category = category;
    this.subcategories = subcategories;
    this.postalCode = postalCode;
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

  public List<MultipartFile> getPictures() {
    return pictures;
  }

  public void setPictures(List<MultipartFile> pictures) {
    // this.pictures = pictures;
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
}
