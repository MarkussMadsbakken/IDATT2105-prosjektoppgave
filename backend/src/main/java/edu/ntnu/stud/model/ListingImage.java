package edu.ntnu.stud.model;

import java.sql.Blob;
import java.util.UUID;

/**
 * Model class representing a ListingImage entity.
 * This class contains the properties and methods for managing ListingImage data.
 */
public class ListingImage {
  private String uuid;

  private Blob imageBlob;

  private String listingUuid;

  /**
   * Constructs a new ListingImage with the specified image blob and listing UUID.
   * Generates a new UUID for the ListingImage.
   *
   * @param imageBlob the image blob associated with the listing image
   * @param listingUuid the UUID of the listing associated with the image
   */
  public ListingImage(Blob imageBlob, String listingUuid) {
    this.uuid = UUID.randomUUID().toString();
    this.imageBlob = imageBlob;
    this.listingUuid = listingUuid;
  }

  /**
   * Constructs a new ListingImage with a generated UUID.
   */
  public ListingImage() {
    this.uuid = UUID.randomUUID().toString();
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public Blob getImageBlob() {
    return imageBlob;
  }

  public void setImageBlob(Blob imageBlob) {
    this.imageBlob = imageBlob;
  }

  public String getListingUuid() {
    return listingUuid;
  }

  public void setListingUuid(String listingUuid) {
    this.listingUuid = listingUuid;
  }
}
