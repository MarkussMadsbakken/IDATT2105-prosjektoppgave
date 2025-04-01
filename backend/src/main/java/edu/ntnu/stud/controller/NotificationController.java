package edu.ntnu.stud.controller;

import edu.ntnu.stud.model.Notification;
import edu.ntnu.stud.service.NotificationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * This is a controller class for managing notifications in the system.
 * It provides endpoints to retrieve, and list notifications, but does not include
 * endpoints for creating notifications as these are handled internally.
 */
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

  @Autowired
  private NotificationService notificationService;

  /**
   * Initializes the NotificationController with a NotificationService instance.
   *
   * @param notificationService the NotificationService instance to be used
   */
  public NotificationController(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  /**
   * Retrieves all notifications in the system.
   *
   * @return a list of all Notification objects
   */
  @GetMapping
  public List<Notification> getAllNotifications() {
    return notificationService.getAllNotifications();
  }

  /**
   * Retrieves all notifications for a specific user.
   *
   * @param userId the ID of the user whose notifications are to be retrieved
   * @return a list of Notification objects for the specified user
   */
  @GetMapping("/user")
  public List<Notification> getNotificationsByUserId(long userId) {
    return notificationService.getNotificationsByUserId(userId);
  }

  /**
   * Retrieves a notification by its ID.
   *
   * @param id the ID of the notification to be retrieved
   * @return the Notification object with the specified ID, or null if not found
   */
  @GetMapping("/{id}")
  public Notification getNotificationById(@PathVariable long id) {
    return notificationService.getNotificationById(id);
  }

  /**
   * Marks a notification as read.
   *
   * @param id the ID of the notification to be marked as read
   */
  @PatchMapping("/{id}/read")
  public void markNotificationAsRead(@PathVariable long id) {
    notificationService.markNotificationAsRead(id);
  }
  
}
