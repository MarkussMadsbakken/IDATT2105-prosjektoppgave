package edu.ntnu.stud.controller;

import edu.ntnu.stud.model.Message;
import edu.ntnu.stud.model.MessageRequest;
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
      @RequestBody MessageRequest message, @RequestHeader("Authorization") String token) {

    try {
      messageService.validateMessageRequest(message);
      messageService.verifySender(message, token);
    } catch (Exception e) {
      logger.error("Validation or verification failed: {}", e.getMessage());
      return ResponseEntity.badRequest().body("Error: " + e.getMessage());
    }

    try {
      messageService.addMessage(message);
      logger.info("Adding message: {}", message);
      return ResponseEntity.ok("Message added successfully.");
    } catch (Exception e) {
      logger.error("Error adding message: {}", e.getMessage());
      return ResponseEntity.status(500).body("Error adding message: " + e.getMessage());
    }
  }

  /**
   * Retrieves a list of the latest messages in each converation associated with a
   * specific user.
   *
   * @param token the JWT token containing user claims
   * @return a ResponseEntity containing a list of messages associated with the
   *         user ID
   */
  @GetMapping
  public ResponseEntity<List<Message>> getMessagesByUserId(
      @RequestHeader("Authorization") String token) {
    try {
      List<Message> messages = messageService.getMessagesByUserId(token);
      logger.info("Retrieved messages for user: {}", messages);
      return ResponseEntity.ok(messages);
    } catch (Exception e) {
      logger.error("Error retrieving messages for user: {}", e.getMessage());
      return ResponseEntity.status(500).body(null);
    }
  }

  /**
   * Retrieves all messages associated with a specific listing ID for a user.
   *
   * @param listingId the ID of the listing
   * @param token     the JWT token containing user claims
   * @return a ResponseEntity containing a list of messages associated with the
   *         listing ID and user ID
   */
  @GetMapping("/{listingId}")
  public ResponseEntity<List<Message>> getMessagesByListingIdAndUserId(
      @PathVariable String listingId, @RequestHeader("Authorization") String token) {
    try {
      List<Message> messages = messageService.getMessagesByListingIdAndUserId(listingId, token);
      logger.info("Retrieved messages for listing ID {}: {}", listingId, messages);
      return ResponseEntity.ok(messages);
    } catch (Exception e) {
      logger.error("Error retrieving messages for listing ID {}: {}", listingId, e.getMessage());
      return ResponseEntity.status(500).body(null);
    }
  }

  /**
   * Retrieves a paginated lis of the latest messages in each converation
   * associated with a specific user.
   *
   * @param token  the JWT token containing user claims
   * @param page   the page number to retrieve
   * @param offset the number of items per page
   * @return a paginated list of messages associated with the user ID
   */
  @GetMapping("/page")
  public ResponseEntity<List<Message>> getMessagesByUserIdPaginated(
      @RequestHeader("Authorization") String token,
      @RequestParam int page,
      @RequestParam int offset) {
    try {
      List<Message> messages = messageService.getMessagesByUserIdPaginated(token, page, offset);
      logger.info("Retrieved paginated messages: {}", messages);
      return ResponseEntity.ok(messages);
    } catch (Exception e) {
      logger.error("Error retrieving paginated messages: {}", e.getMessage());
      return ResponseEntity.status(500).body(null);
    }
  }

  /**
   * Recives a paginated list of messages associated with a specific listing ID
   * for a user.
   *
   * @param listingId the ID of the listing
   * @param token     the JWT token containing user claims
   * @param page      the page number to retrieve
   * @param offset    the number of items per page
   * @return a paginated list of messages associated with the listing ID and user
   *         ID
   */
  @GetMapping("/page/{listingId}")
  public ResponseEntity<List<Message>> getMessagesByListingIdAndUserIdPaginated(
      @PathVariable String listingId,
      @RequestHeader("Authorization") String token,
      @RequestParam int page,
      @RequestParam int offset) {
    try {
      List<Message> messages = messageService.getMessagesByListingIdAndUserIdPaginated(
          listingId, token, page, offset);
      logger.info("Retrieved paginated messages for listing ID {}: {}", listingId, messages);
      return ResponseEntity.ok(messages);
    } catch (Exception e) {
      logger.error(
          "Error retrieving paginated messages for listing ID {}: {}", listingId, e.getMessage());
      return ResponseEntity.status(500).body(null);
    }
  }

}
