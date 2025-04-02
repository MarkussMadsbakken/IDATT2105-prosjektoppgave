package edu.ntnu.stud.controller;

import edu.ntnu.stud.model.Message;
import edu.ntnu.stud.service.JWTService;
import edu.ntnu.stud.service.MessageService;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller class for managing messages in the system.
 * This class provides endpoints for adding and retrieving messages.
 */
@RestController
@RequestMapping("/api/messages")
public class MessageController {

  @Autowired
  private MessageService messageService;
  @Autowired
  private JWTService jwtService;

  Logger logger = LoggerFactory.getLogger(MessageController.class);

  /**
   * Initializes the MessageController with a MessageService instance.
   *
   * @param messageService the MessageService instance to be used
   */
  public MessageController(MessageService messageService) {
    this.messageService = messageService;
  }

  /**
   * Adds a new message to the system.
   *
   * @param message the message to be added
   */
  @PostMapping
  public void addMessage(
      @RequestBody Message message, @RequestHeader("Authorization") String token) {
    long senderFromToken = jwtService.extractUserId(token);
    if (
        !((senderFromToken == message.getByerId() && message.isSentByBuyer()) 
        || (senderFromToken == message.getSellerId() && !message.isSentByBuyer()))
    ) {
      logger.error("Sender ID does not match the message sender.");
      throw new IllegalArgumentException("Sender ID does not match the message sender.");
    }
    // more validation can be added here
    logger.info("Adding message: {}", message);
    messageService.addMessage(message);
  }

  /**
   * Retrieves all messages associated with a specific listing ID and user ID.
   *
   * @param listingId the ID of the listing
   * @param userId    the ID of the user
   * @return a list of messages associated with the listing ID and user ID
   */
  @GetMapping("/{listingId}/{userId}")
  public List<Message> getMessagesByListingIdAndUserId(
      @PathVariable String listingId, @PathVariable long userId) {
    return messageService.getMessagesByListingIdAndUserId(listingId, userId);
  }

  /**
   * Retrieves all messages associated with a specific user ID.
   *
   * @param userId the ID of the user
   * @return a list of messages associated with the user ID
   */
  @GetMapping("/user/{userId}")
  public List<Message> getMessagesByUserId(@PathVariable long userId) {
    return messageService.getMessagesByUserId(userId);
  }

}
