package edu.ntnu.stud.service;

import edu.ntnu.stud.model.MessageRequest;
import edu.ntnu.stud.model.base.Message;
import edu.ntnu.stud.repo.MessageRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing messages in the system.
 * This class provides methods to add and retrieve messages.
 */
@Service
public class MessageService {

  @Autowired
  private MessageRepo messageRepo;

  /**
   * Gets all messages from a given chat.
   *
   * @param chatId the id of the chat
   * @return a list of messages in the chat
   */
  public List<Message> getAllMessagesByChatId(Long chatId) {
    return messageRepo.getMessagesByChatId(chatId);
  }

  /**
   * Adds a message to a chat.
   *
   * @param message the message to add
   * @return the amount of rows affected in the database
   */
  public int addMessage(MessageRequest message) {
    return messageRepo.addMessage(message);
  }

}
