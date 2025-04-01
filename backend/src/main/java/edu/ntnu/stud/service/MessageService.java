package edu.ntnu.stud.service;

import edu.ntnu.stud.model.Message;
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
   * Adds a new message to the system.
   *
   * @param message the message to be added
   */
  public void addMessage(Message message) {
    messageRepo.addMessage(message);
    // TODO: Send the message to the WebSocket topic for real-time updates
  }

  /**
   * Retrieves all messages associated with a specific listing ID and user ID.
   *
   * @param listingId the ID of the listing
   * @param userId    the ID of the user
   * @return a list of messages associated with the listing ID and user ID
   */
  public List<Message> getMessagesByListingIdAndUserId(String listingId, long userId) {
    return messageRepo.getMessagesByListingIdAndUserId(listingId, userId);
  }

  /**
   * Retrieves all messages associated with a specific user ID.
   *
   * @param userId the ID of the user
   * @return a list of messages associated with the user ID
   */
  public List<Message> getMessagesByUserId(long userId) {
    return messageRepo.getMessagesByUserId(userId);
  }


}
