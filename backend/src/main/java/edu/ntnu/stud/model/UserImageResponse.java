package edu.ntnu.stud.model;

/**
 * Represents a response containing a user image.
 * This class includes the image encoded as a Base64 string and its file type.
 */
public class UserImageResponse {
  private String base64Image;
  private String fileType;

  /**
   * Constructs a new UserImageResponse with the specified Base64 encoded image and file type.
   *
   * @param base64Image the Base64 encoded image
   * @param fileType the file type of the image
   */
  public UserImageResponse(String base64Image, String fileType) {
    this.base64Image = base64Image;
    this.fileType = fileType;
  }

  // Getters and Setters
  public String getBase64Image() {
    return base64Image;
  }

  public void setBase64Image(String base64Image) {
    this.base64Image = base64Image;
  }

  public String getFileType() {
    return fileType;
  }

  public void setFileType(String fileType) {
    this.fileType = fileType;
  }
}