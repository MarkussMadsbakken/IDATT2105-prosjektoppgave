package edu.ntnu.stud.repo;

import edu.ntnu.stud.model.Notification;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * This is a repository class for managing notifications in the database.
 * It provides methods to add, retrieve, and list notifications.
 */
@Repository
public class NotificationRepo {

  @Value("${spring.datasource.url}")
  private String url;
  @Value("${spring.datasource.username}")
  private String user;
  @Value("${spring.datasource.password}")
  private String password;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  RowMapper<Notification> notificationRowMapper = (rs, rowNum) -> {
    Notification notification = new Notification();
    notification.setId(rs.getLong("id"));
    notification.setUserId(rs.getLong("user_id"));
    notification.setMessage(rs.getString("message"));
    notification.setLink(rs.getString("link"));
    notification.setRead(rs.getBoolean("is_read"));
    notification.setTime(rs.getTimestamp("timestamp"));
    return notification;
  };

  /**
   * Adds a new notification to the database.
   *
   * @param notification the Notification object to be added
   */
  public void addNotification(Notification notification) {
    String query = "INSERT INTO notifications (user_id, message, link) VALUES (?, ?, ?)";
    jdbcTemplate.update(
        query, notification.getUserId(), notification.getMessage(), notification.getLink());
  }

  /**
   * Retrieves a notification by its ID.
   *
   * @param id the ID of the notification to retrieve
   * @return the Notification object, or null if not found
   */
  public Notification getNotificationById(long id) {
    String query = "SELECT * FROM notifications WHERE id = ?";
    return jdbcTemplate.queryForObject(query, notificationRowMapper, id);
  }

  /**
   * Retrieves all notifications for a specific user.
   *
   * @param userId the ID of the user whose notifications to retrieve
   * @return a list of Notification objects
   */
  public List<Notification> getNotificationsByUserId(long userId) {
    String query = "SELECT * FROM notifications WHERE user_id = ?";
    return jdbcTemplate.query(query, notificationRowMapper, userId);
  }

  /**
   * Retrieves all notifications from the database.
   *
   * @return a list of Notification objects
   */
  public List<Notification> getAllNotifications() {
    String query = "SELECT * FROM notifications";
    return jdbcTemplate.query(query, notificationRowMapper);
  }

  /**
   * Marks a notification as read in the database.
   *
   * @param id the ID of the notification to mark as read
   */
  public void markNotificationAsRead(long id) {
    String query = "UPDATE notifications SET is_read = ? WHERE id = ?";
    jdbcTemplate.update(query, true, id);
  }
}
