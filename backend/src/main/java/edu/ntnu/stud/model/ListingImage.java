package edu.ntnu.stud.model;

import java.sql.Blob;

public class ListingImage {
  private String uuid;

  private Blob imageBlob;

  public ListingImage(String uuid, Blob imageBlob) {
    this.uuid = uuid;
    this.imageBlob = imageBlob;
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
}
