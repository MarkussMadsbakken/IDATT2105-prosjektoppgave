package edu.ntnu.stud.service;

import edu.ntnu.stud.model.Message;
import edu.ntnu.stud.model.MessageRequest;
import edu.ntnu.stud.repo.ChatRepo;
import edu.ntnu.stud.repo.MessageRepo;
import edu.ntnu.stud.util.Validate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

  public boolean addMessage(MessageRequest message) {
    return messageRepo.addMessage(message);
  }

}
