package edu.ntnu.stud.service;

import edu.ntnu.stud.model.Notification;
import edu.ntnu.stud.repo.NotificationRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This is a service class for managing notifications in the system.
 * It provides methods to create, retrieve, and list notifications.
 */
@Service
public class NotificationService {

  @Autowired
  private NotificationRepo notificationRepo;
  @Autowired
  private JWTService jwtService;

  /**
   * Initializes the NotificationService with a NotificationRepo instance.
   *
   * @param notificationRepo the NotificationRepo instance to be used
   */
  public NotificationService(NotificationRepo notificationRepo) {
    this.notificationRepo = notificationRepo;
  }

  /**
   * Adds a new notification to the system.
   *
   * @param notification the Notification object to be added
   */
  public void addNotification(Notification notification) {
    notificationRepo.addNotification(notification);
    // TODO: Send the notification to the WebSocket topic for real-time updates
  }

  /**
   * Retrieves a notification by its ID.
   *
   * @param id    the ID of the notification to be retrieved
   * @param token the token of the user who is retrieving the notification
   * @return the Notification object with the specified ID, or null if not found
   * @throws IllegalArgumentException if the notification is not found or if the
   */
  public Notification getNotificationById(long id, String token) {
    long userId = jwtService.extractUserId(token.substring(7));
    Notification notification = notificationRepo.getNotificationById(id);
    if (notification == null) {
      throw new IllegalArgumentException("Notification not found");
    }
    if (notification.getUserId() != userId) {
      throw new IllegalArgumentException(
          "You do not have permission to view this notification");
    }
    return notificationRepo.getNotificationById(id);
  }

  /**
   * Retrieves all notifications for a specific user.
   *
   * @param token  the token of the user who is retrieving the notifications
   * @return a list of Notification objects for the specified user
   */
  public List<Notification> getNotificationsByUserId(String token) {
    long tokenUserId = jwtService.extractUserId(token.substring(7));
    if (notificationRepo.getNotificationsByUserId(tokenUserId) == null) {
      throw new IllegalArgumentException("No notifications found for this user");
    }
    return notificationRepo.getNotificationsByUserId(tokenUserId);
  }

  /**
   * Retrieves all notifications in the system.
   *
   * @return a list of all Notification objects
   */
  public List<Notification> getAllNotifications() {
    return notificationRepo.getAllNotifications();
  }

  /**
   * Marks a notification as read.
   *
   * @param id    the ID of the notification to be marked as read
   * @param token the token of the user who is marking the notification as read
   */
  public void markNotificationAsRead(long id, String token) {
    long userId = jwtService.extractUserId(token);
    Notification notification = notificationRepo.getNotificationById(id);
    if (notification == null) {
      throw new IllegalArgumentException("Notification not found");
    }
    if (notification.getUserId() != userId) {
      throw new IllegalArgumentException(
          "You do not have permission to mark this notification as read");
    }
    notificationRepo.markNotificationAsRead(id);
  }
}
