package edu.ntnu.stud.repo;

import java.util.List;
import java.util.ArrayList;

import edu.ntnu.stud.model.Notification;
import java.sql.*;
import org.springframework.beans.factory.annotation.Value;

/**
 * This is a repository class for managing notifications in the database.
 * It provides methods to add, retrieve, and list notifications.
 */
public class NotificationRepo {

  @Value("${spring.datasource.url}")
  private String url;
  @Value("${spring.datasource.username}")
  private String user;
  @Value("${spring.datasource.password}")
  private String password;

  /**
   * Initializes the NotificationRepo and loads the MySQL JDBC driver.
   */
  public NotificationRepo() {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Adds a new notification to the database.
   *
   * @param notification the Notification object to be added
   */
  public void addNotification(Notification notification) {
    String query = 
        "INSERT INTO notifications (user_id, message, link, is_read, time) VALUES (?, ?, ?, ?, ?)";
    try (Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement statement = 
            connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

      statement.setLong(1, notification.getUserId());
      statement.setString(2, notification.getMessage());
      statement.setString(3, notification.getLink());
      statement.setBoolean(4, notification.isRead());
      statement.setTimestamp(5, new Timestamp(notification.getTime().getTime()));

      int affectedRows = statement.executeUpdate();
      if (affectedRows > 0) {
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
          if (generatedKeys.next()) {
            notification.setId(generatedKeys.getLong(1));
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Retrieves a notification by its ID.
   *
   * @param id the ID of the notification to retrieve
   * @return the Notification object, or null if not found
   */
  public Notification getNotificationById(long id) {
    String query = "SELECT * FROM notifications WHERE id = ?";
    try (Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement statement = connection.prepareStatement(query)) {

      statement.setLong(1, id);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        Notification notification = new Notification();
        notification.setId(resultSet.getLong("id"));
        notification.setUserId(resultSet.getLong("user_id"));
        notification.setMessage(resultSet.getString("message"));
        notification.setLink(resultSet.getString("link"));
        notification.setRead(resultSet.getBoolean("is_read"));
        notification.setTime(resultSet.getTimestamp("time"));
        return notification;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Retrieves all notifications for a specific user.
   *
   * @param userId the ID of the user whose notifications to retrieve
   * @return an array of Notification objects
   */
  public List<Notification> getNotificationsByUserId(long userId) {
    List<Notification> notifications = new ArrayList<>();
    String query = "SELECT * FROM notifications WHERE user_id = ?";
    try (Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement statement = connection.prepareStatement(query)) {

      statement.setLong(1, userId);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        Notification notification = new Notification();
        notification.setId(resultSet.getLong("id"));
        notification.setUserId(resultSet.getLong("user_id"));
        notification.setMessage(resultSet.getString("message"));
        notification.setLink(resultSet.getString("link"));
        notification.setRead(resultSet.getBoolean("is_read"));
        notification.setTime(resultSet.getTimestamp("time"));
        notifications.add(notification);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return notifications;
  }

  /**
   * Retrieves all notifications from the database.
   *
   * @return a list of Notification objects
   */
  public List<Notification> getAllNotifications() {
    List<Notification> notifications = new ArrayList<>();
    String query = "SELECT * FROM notifications";
    try (Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement statement = connection.prepareStatement(query)) {

      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        Notification notification = new Notification();
        notification.setId(resultSet.getLong("id"));
        notification.setUserId(resultSet.getLong("user_id"));
        notification.setMessage(resultSet.getString("message"));
        notification.setLink(resultSet.getString("link"));
        notification.setRead(resultSet.getBoolean("is_read"));
        notification.setTime(resultSet.getTimestamp("time"));
        notifications.add(notification);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return notifications;
  }

  /**
   * Marks a notification as read in the database.
   *
   * @param id the ID of the notification to mark as read
   */
  public void markNotificationAsRead(long id) {
    String query = "UPDATE notifications SET is_read = ? WHERE id = ?";
    try (Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement statement = connection.prepareStatement(query)) {

      statement.setBoolean(1, true);
      statement.setLong(2, id);
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
