package edu.ntnu.stud.repo;

import edu.ntnu.stud.model.Message;
import edu.ntnu.stud.model.MessageRequest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private final RowMapper<Message> messageRowMapper = (rs, rowNum) -> {
    Message message = new Message();
    message.setId(rs.getLong("id"));
    message.setListingId(rs.getString("listing_id"));
    message.setByerId(rs.getLong("byer_id"));
    message.setMessage(rs.getString("message"));
    message.setCreatedAt(rs.getTimestamp("created_at"));
    message.setSentByBuyer(rs.getBoolean("sent_by_buyer"));
    return message;
  };

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
  public void addMessage(MessageRequest message) {
    String query = "INSERT INTO messages "
        + "(listing_id, seller_id, byer_id, message, sent_by_buyer)"
        + " VALUES (?, ?, ?, ?, ?, ?)";
    jdbcTemplate.update(query,
        message.getListingId(),
        message.getSellerId(),
        message.getByerId(),
        message.getMessage(),
        message.isSentByBuyer());
  }

  /**
   * Retrieves a list of the latest messages in each conversation associated with
   * a
   * specific user from the database.
   *
   * @param userId the ID of the user
   */
  public List<Message> getMessagesByUserId(long userId) {
    String query = "SELECT * FROM ("
        + "SELECT *, ROW_NUMBER() OVER " 
        + "(PARTITION BY seller_id, byer_id, listing_id ORDER BY created_at DESC) AS rn "
        + "FROM messages WHERE seller_id = ? OR byer_id = ?) subquery WHERE rn = 1";
    return jdbcTemplate.query(query, messageRowMapper, userId, userId);
  }

  /**
   * Retrieves all messages for a specific listing with a spesific user involved
   * from the database.
   *
   * @param listingId the ID of the listing
   * @return a list of Message objects associated with the listing
   */
  public List<Message> getMessagesByListingIdAndUserId(String listingId, long userId) {
    String query = "SELECT * FROM messages WHERE listing_id = ? AND (seller_id = ? OR byer_id = ?)";
    return jdbcTemplate.query(query, messageRowMapper, listingId, userId, userId);
  }

  /**
   * Retrieves a paginated list of the latest messages in each conversation
   * associated with a specific user.
   *
   * @param userId the ID of the user
   * @param page   the page number to retrieve
   * @param offset the number of items per page
   * @return a list of Message objects associated with the user
   */
  public List<Message> getMessagesByUserIdPaginated(long userId, int page, int offset) {
    String query = "SELECT * FROM ("
        + "SELECT *, ROW_NUMBER() OVER "
        + "(PARTITION BY seller_id, byer_id, listing_id ORDER BY created_at DESC) AS rn "
        + "FROM messages WHERE seller_id = ? OR byer_id = ?) subquery "
        + "WHERE rn = 1 LIMIT ? OFFSET ?";
    return jdbcTemplate.query(query, messageRowMapper, userId, userId, offset, page * offset);
  }

  /**
   * Retrieves a paginated list of messages for a specific listing with a spesific
   * user involved from the database.
   *
   * @param listingId the ID of the listing
   * @param userId    the ID of the user
   * @param page      the page number to retrieve
   * @param offset    the number of items per page
   * @return a list of Message objects associated with the listing
   */
  public List<Message> getMessagesByListingIdAndUserIdPaginated(
      String listingId, long userId, int page, int offset) {
    String query = "SELECT * FROM messages "
        + "WHERE listing_id = ? AND (seller_id = ? OR byer_id = ?) LIMIT ? OFFSET ?";
    return jdbcTemplate.query(
        query, messageRowMapper, listingId, userId, userId, page * offset, offset);
  }
}
