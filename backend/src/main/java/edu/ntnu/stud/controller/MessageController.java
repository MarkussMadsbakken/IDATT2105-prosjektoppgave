package edu.ntnu.stud.controller;

import edu.ntnu.stud.model.Message;
import edu.ntnu.stud.service.MessageService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  Logger logger = LoggerFactory.getLogger(MessageController.class);

  /**
   * Adds a new message to the system.
   *
   * @param message the message to be added
   */
  @PostMapping
  public ResponseEntity<String> addMessage(
      @RequestBody Message message, @RequestHeader("Authorization") String token) {

    try {
      messageService.validateMessage(message);
      messageService.verifySender(message, token);
    } catch (Exception e) {
      logger.error("Validation or verification failed: {}", e.getMessage());
      return ResponseEntity.badRequest().body("Error: " + e.getMessage());
    }

    logger.info("Adding message: {}", message);
    messageService.addMessage(message);
    return ResponseEntity.ok("Message added successfully.");
  }

  /**
   * Retrieves all messages associated with a specific user.
   *
   * @param token the JWT token containing user claims
   * @return a list of messages associated with the user ID
   */
  @GetMapping
  public List<Message> getMessagesByUserId(@RequestHeader("Authorization") String token) {
    return messageService.getMessagesByUserId(token);
  }

  /**
   * Retrieves all messages associated with a specific listing ID for a user.
   *
   * @param listingId the ID of the listing
   * @param token the JWT token containing user claims
   * @return a list of messages associated with the listing ID and user ID
   */
  @GetMapping("/{listingId}")
  public List<Message> getMessagesByListingIdAndUserId(
      @PathVariable String listingId, @RequestHeader("Authorization") String token) {
    return messageService.getMessagesByListingIdAndUserId(listingId, token);
  }

  /**
   * Recives a paginated list of messages associated with a specific user.
   *
   * @param token the JWT token containing user claims
   * @param page the page number to retrieve
   * @param offset the number of items per page
   * @return a paginated list of messages associated with the user ID
   */
  @GetMapping("/page")
  public ResponseEntity<List<Message>> getMessagesByUserIdPaginated(
      @RequestHeader("Authorization") String token, 
      @RequestParam int page, 
      @RequestParam int offset
  ) {
    List<Message> messages = messageService.getMessagesByUserIdPaginated(token, page, offset);
    return ResponseEntity.ok(messages);
  }

  /**
   * Recives a paginated list of messages associated with a specific listing ID for a user.
   *
   * @param listingId the ID of the listing
   * @param token the JWT token containing user claims
   * @param page the page number to retrieve
   * @param offset the number of items per page
   * @return a paginated list of messages associated with the listing ID and user ID
   */
  @GetMapping("/page/{listingId}")
  public ResponseEntity<List<Message>> getMessagesByListingIdAndUserIdPaginated(
      @PathVariable String listingId,
      @RequestHeader("Authorization") String token,
      @RequestParam int page,
      @RequestParam int offset
  ) {
    List<Message> messages = messageService.getMessagesByListingIdAndUserIdPaginated(
        listingId, token, page, offset);
    return ResponseEntity.ok(messages);
  }

}
