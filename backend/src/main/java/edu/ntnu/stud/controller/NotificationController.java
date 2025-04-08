package edu.ntnu.stud.controller;

import edu.ntnu.stud.model.Notification;
import edu.ntnu.stud.service.NotificationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is a controller class for managing notifications in the system.
 * It provides endpoints to retrieve, and list notifications, but does not
 * include
 * endpoints for creating notifications as these are handled internally.
 */
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

  @Autowired
  private NotificationService notificationService;

  /**
   * Retrieves all notifications for a specific user.
   *
   * @param token the JWT token of the user
   * @return a ResponseEntity containing a list of Notification objects for the
   *         specified user or a no content status if no notifications are found
   *         for the user
   */
  @GetMapping("/user")
  public ResponseEntity<List<Notification>> getNotificationsByUserId(
      @RequestHeader("Authorization") String token) {
    List<Notification> notifications = notificationService.getNotificationsByUserId(token);
    if (notifications.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(notifications);
  }

  /**
   * Retrieves a notification by its ID.
   *
   * @param id the ID of the notification to be retrieved
   * @return a ResponseEntity containing the Notification object with the
   *         specified ID, or a not found status
   */
  @GetMapping("/{id}")
  public ResponseEntity<Notification> getNotificationById(@PathVariable long id,
      @RequestHeader("Authorization") String token) {
    Notification notification = notificationService.getNotificationById(id, token);
    if (notification != null) {
      return ResponseEntity.ok(notification);
    } else {
      return ResponseEntity.noContent().build();
    }
  }

  /**
   * Marks a notification as read.
   *
   * @param id    the ID of the notification to be marked as read
   * @param token the JWT token of the user
   * @return a ResponseEntity indicating the result of the operation
   */
  @PatchMapping("/{id}/read")
  public ResponseEntity<Void> markNotificationAsRead(
      @PathVariable long id, @RequestHeader("Authorization") String token) {
    notificationService.markNotificationAsRead(id, token);
    return ResponseEntity.ok().build();
  }
}
