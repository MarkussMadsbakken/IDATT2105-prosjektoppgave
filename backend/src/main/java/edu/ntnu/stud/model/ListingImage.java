package edu.ntnu.stud.model;

import java.sql.Blob;
import java.util.UUID;

public class ListingImage {
  private String uuid;

  private Blob imageBlob;

  private String listingUuid;

  public ListingImage(Blob imageBlob, String listingUuid) {
    this.uuid = UUID.randomUUID().toString();
    this.imageBlob = imageBlob;
    this.listingUuid = listingUuid;
  }

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
