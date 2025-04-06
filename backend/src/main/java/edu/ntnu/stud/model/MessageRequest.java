package edu.ntnu.stud.model;

/**
 * Represents a message request sent by a user.
 */
public class MessageRequest {
  private String message;
  private Long chatId;
  private Long senderId;

  /**
   * Default constructor for MessageRequest.
   */
  public MessageRequest() {
  }

  /**
   * Constructs a new MessageRequest with the specified details.
   *
   * @param message  the content of the message
   * @param chatId   the ID of the chat
   * @param senderId the ID of the sender
   */
  public MessageRequest(String message, Long chatId, Long senderId) {
    this.message = message;
    this.chatId = chatId;
    this.senderId = senderId;
  }

  /**
   * Constructs a new MessageRequest with the specified message.
   *
   * @param message the content of the message
   */
  public MessageRequest(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Long getChatId() {
    return chatId;
  }

  public void setChatId(Long chatId) {
    this.chatId = chatId;
  }

  public Long getSenderId() {
    return senderId;
  }

  public void setSenderId(Long senderId) {
    this.senderId = senderId;
  }
}
