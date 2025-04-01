package edu.ntnu.stud.repo;

import edu.ntnu.stud.model.Message;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * Repository class for managing Message entities in the database.
 * This class provides methods to add, retrieve and update.
 */
@Repository
public class MessageRepo {
  
  @Value("${spring.datasource.url}")
  private String url;
  @Value("${spring.datasource.username}")
  private String user;
  @Value("${spring.datasource.password}")
  private String password;

  /**
   * Initializes the MessageRepo and loads the MySQL JDBC driver.
   */
  public MessageRepo() {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Adds a new message to the database.
   *
   * @param message the Message object to be added
   */
  public void addMessage(Message message) {
    String query = "INSERT INTO messages " 
        + "(listing_id, seller_id, byer_id, message, created_at, sent_by_buyer)" 
        + " VALUES (?, ?, ?, ?, ?, ?)";
    try (Connection connection = DriverManager.getConnection(url, user, password);
         PreparedStatement statement = connection.prepareStatement(query)) {

      statement.setString(1, message.getListingId());
      statement.setLong(2, message.getByerId());
      statement.setString(3, message.getMessage());
      statement.setString(4, message.getCreatedAt());
      statement.setBoolean(5, message.isSentByBuyer());

      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Retrieves all messages for a specific listing with a spesific user involved from the database.
   *
   * @param listingId the ID of the listing
   * @return a list of Message objects associated with the listing
   */
  public List<Message> getMessagesByListingIdAndUserId(String listingId, long userId) {
    List<Message> messages = new ArrayList<>();
    String query = "SELECT * FROM messages WHERE listing_id = ? AND (seller_id = ? OR byer_id = ?)";
    try (Connection connection = DriverManager.getConnection(url, user, password);
         PreparedStatement statement = connection.prepareStatement(query)) {

      statement.setString(1, listingId);
      statement.setLong(2, userId);
      statement.setLong(3, userId);
      var resultSet = statement.executeQuery();

      while (resultSet.next()) {
        Message message = new Message();
        message.setId(resultSet.getLong("id"));
        message.setListingId(resultSet.getString("listing_id"));
        message.setByerId(resultSet.getLong("byer_id"));
        message.setMessage(resultSet.getString("message"));
        message.setCreatedAt(resultSet.getString("created_at"));
        message.setSentByBuyer(resultSet.getBoolean("sent_by_buyer"));
        messages.add(message);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return messages;
  }

  /**
   * Retrieves all messages for a specific user from the database.
   *
   * @param userId the ID of the user
   */
  public List<Message> getMessagesByUserId(long userId) {
    List<Message> messages = new ArrayList<>();
    String query = "SELECT * FROM messages WHERE seller_id = ? OR byer_id = ?";
    try (Connection connection = DriverManager.getConnection(url, user, password);
         PreparedStatement statement = connection.prepareStatement(query)) {

      statement.setLong(1, userId);
      statement.setLong(2, userId);
      var resultSet = statement.executeQuery();

      while (resultSet.next()) {
        Message message = new Message();
        message.setId(resultSet.getLong("id"));
        message.setListingId(resultSet.getString("listing_id"));
        message.setByerId(resultSet.getLong("byer_id"));
        message.setMessage(resultSet.getString("message"));
        message.setCreatedAt(resultSet.getString("created_at"));
        message.setSentByBuyer(resultSet.getBoolean("sent_by_buyer"));
        messages.add(message);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return messages;
  }
}
