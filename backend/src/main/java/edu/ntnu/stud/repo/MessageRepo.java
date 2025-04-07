package edu.ntnu.stud.repo;

import edu.ntnu.stud.model.Message;
import edu.ntnu.stud.model.MessageRequest;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
    message.setSenderId(rs.getLong("sender_id"));
    message.setChatId(rs.getLong("chat_id"));
    message.setMessage(rs.getString("message"));
    message.setCreatedAt(rs.getTimestamp("created_at"));

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
   * Inserts a new message into the database, and returns its ID.
   *
   * @param message the message to be added
   * @return the inserted message
   */
  public Long addMessage(MessageRequest message) {
    String query = "INSERT INTO message (sender_id, chat_id, message) VALUES (?, ?, ?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      ps.setLong(1, message.getSenderId());
      ps.setLong(2, message.getChatId());
      ps.setString(3, message.getMessage());
      return ps;
    }, keyHolder);

    return keyHolder.getKey().longValue();
  }

  /**
   * Retrieves all messages of a specific chat.
   *
   * @param chatId the ID of the chat
   * @return a list of messages associated with the chat ID
   */
  public List<Message> getMessagesByChatId(long chatId) {
    String query = "SELECT * FROM message WHERE chat_id = ?";
    return jdbcTemplate.query(query, messageRowMapper, chatId);
  }
}
