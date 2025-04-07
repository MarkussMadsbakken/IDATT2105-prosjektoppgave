package edu.ntnu.stud.factories;

import java.sql.Timestamp;
import edu.ntnu.stud.model.Notification;

/**
 * Factory class for creating notifications.
 */
public class NotificationFactory {
  private NotificationFactory() {
  }

  /**
   * Creates a new notification for when a listing is bookmarked.
   *
   * @param listingId The ID of the listing that was bookmarked
   * @param userId    The ID of the user who owns the listing
   * @return A new Notification object
   */
  public static Notification createBookmarkNotification(
      String listingId, Long userId) {
    return new Notification(userId, "newBookmark", "/listing/" + listingId, false, null);
  }
}
