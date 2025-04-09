package edu.ntnu.stud.model.base;

import java.sql.Timestamp;

/**
 * Represents a message in the system.
 */
public class Message {
  private long id;
  private Long chatId;
  private Long senderId;
  private String message;
  private Timestamp createdAt;

  /**
   * Default constructor.
   */
  public Message() {
  }

  /**
   * Constructs a new Message with the specified details.
   *
   * @param id        the ID of the message
   * @param chatId    the ID of the chat
   * @param senderId  the ID of the sender
   * @param message   the content of the message
   * @param createdAt the timestamp when the message was created
   */
  public Message(long id, Long chatId, Long senderId, String message, Timestamp createdAt) {
    this.id = id;
    this.chatId = chatId;
    this.senderId = senderId;
    this.message = message;
    this.createdAt = createdAt;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }
}