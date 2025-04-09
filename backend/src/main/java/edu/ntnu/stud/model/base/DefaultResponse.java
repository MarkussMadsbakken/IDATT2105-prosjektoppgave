package edu.ntnu.stud.model.base;

/**
 * This class represents a default response in the application.
 */
public class DefaultResponse {
  private String message;
  private String shortMessage;

  public DefaultResponse(String message, String shortMessage) {
    this.message = message;
    this.shortMessage = shortMessage;
  }

  public String getMessage() {
    return message;
  }

  public String getShortMessage() {
    return shortMessage;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setShortMessage(String shortMessage) {
    this.shortMessage = shortMessage;
  }
}
