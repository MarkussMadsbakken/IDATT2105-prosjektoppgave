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
   * @param id the ID of the notification to be retrieved
   * @return the Notification object with the specified ID, or null if not found
   */
  public Notification getNotificationById(long id) {
    return notificationRepo.getNotificationById(id);
  }

  /**
   * Retrieves all notifications for a specific user.
   *
   * @param userId the ID of the user whose notifications are to be retrieved
   * @return a list of Notification objects for the specified user
   */
  public List<Notification> getNotificationsByUserId(long userId) {
    return notificationRepo.getNotificationsByUserId(userId);
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
   * @param id the ID of the notification to be marked as read
   */
  public void markNotificationAsRead(long id) {
    notificationRepo.markNotificationAsRead(id);
  }
}
