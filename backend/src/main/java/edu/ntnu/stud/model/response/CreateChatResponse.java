package edu.ntnu.stud.model.response;

import edu.ntnu.stud.model.base.DefaultResponse;

/**
 * Represents a response for creating a chat.
 */
public class CreateChatResponse extends DefaultResponse {

  private long chatId;

  /**
   * Constructs a new CreateChatResponse with the specified details.
   *
   * @param chatId the ID of the chat
   */
  public CreateChatResponse(long chatId) {
    super("Chat created successfully", "chatCreated");
    this.chatId = chatId;
  }

  public long getChatId() {
    return chatId;
  }

  public void setChatId(long chatId) {
    this.chatId = chatId;
  }

}
