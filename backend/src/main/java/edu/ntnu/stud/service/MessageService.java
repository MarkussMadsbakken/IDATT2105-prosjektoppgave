package edu.ntnu.stud.service;

import edu.ntnu.stud.model.Message;
import edu.ntnu.stud.repo.MessageRepo;
import edu.ntnu.stud.util.Validate;
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
   * Validates the message fields.
   *
   * @param message the message to be validated
   */
  public void validateMessage(Message message) {
    Validate.that(message.getByerId(), Validate.isPositive(), "Buyer ID cannot be zero.");
    Validate.that(message.getSellerId(), Validate.isPositive(), "Seller ID cannot be zero.");
    Validate.that(message.getListingId(), Validate.isNotEmptyOrBlankOrNull(),
        "Listing ID cannot be empty or blank or null.");
    Validate.that(message.getMessage(), Validate.isNotEmptyOrBlankOrNull(),
        "Message cannot be empty or blank or null.");
    Validate.that(message.getCreatedAt(), Validate.isNotNull(),
        "CreatedAt cannot be null.");
  }

  /**
   * Verifies the sender of the message.
   *
   * @param message         the message to be verified
   * @param senderFromToken the sender ID from the token
   */
  public void verifySender(Message message, long senderFromToken) {
    if (!((senderFromToken == message.getByerId() && message.isSentByBuyer())
        || (senderFromToken == message.getSellerId() && !message.isSentByBuyer()))) {
      throw new IllegalArgumentException("Sender ID does not match the message sender.");
    }
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
