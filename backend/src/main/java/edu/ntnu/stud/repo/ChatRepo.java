package edu.ntnu.stud.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import edu.ntnu.stud.model.Chat;

@Repository
public class ChatRepo {

  @Value("${spring.datasource.url}")
  private String url;
  @Value("${spring.datasource.username}")
  private String user;
  @Value("${spring.datasource.password}")
  private String password;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private final RowMapper<Chat> chatRowMapper = (rs, rowNum) -> {
    Chat chat = new Chat();
    chat.setId(rs.getLong("chat_id"));
    chat.setBuyerId(rs.getLong("buyer_id"));
    chat.setSellerId(rs.getLong("seller_id"));
    chat.setListingId(rs.getString("listing_id"));
    return chat;
  };

  /**
   * Initializes the MessageRepo and loads the MySQL JDBC driver.
   */
  public ChatRepo() {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Creates a new chat with a buyer, seller, and listing, and returns the chat
   * ID.
   *
   * @param buyerId   The ID of the buyer
   * @param sellerId  The ID of the seller
   * @param listingId The ID of the listing
   * @return the ID of the chat created, or -1 if the chat could not be created
   */
  public long createChat(long buyerId, long sellerId, String listingId) {
    String query = "INSERT INTO chats (buyer_id, seller_id, listing_id) VALUES (?, ?, ?)";
    int rowsAffected = jdbcTemplate.update(query, buyerId, sellerId, listingId);
    if (rowsAffected > 0) {
      String selectQuery = "SELECT chat_id FROM chats"
          + "WHERE buyer_id = ? AND seller_id = ? AND listing_id = ?";
      return jdbcTemplate.queryForObject(selectQuery, Long.class, buyerId, sellerId, listingId);
    }
    return -1;
  }

  /**
   * Checks if a chat exists by its ID.
   *
   * @param chatId The ID of the chat to check
   * @return true if the chat exists, false otherwise
   */
  public boolean chatExists(long chatId) {
    String query = "SELECT * FROM chats WHERE chat_id = ?";
    List<Chat> chats = jdbcTemplate.query(query, chatRowMapper, chatId);
    return !chats.isEmpty();
  }

  /**
   * Checks if a chat exists between a buyer and seller for a specific listing,
   * and if so, returns the id of the chat.
   *
   * @param buyerId   The ID of the buyer
   * @param sellerId  The ID of the seller
   * @param listingId The ID of the listing
   * @return an Optional containing the chat ID if it exists, or an empty Optional
   *         otherwise
   */
  public Optional<Long> chatAlreadyExists(long buyerId, long sellerId, String listingId) {
    String query = "SELECT chat_id FROM chats"
        + "WHERE buyer_id = ? AND seller_id = ? AND listing_id = ?";

    List<Chat> chats = jdbcTemplate.query(query, chatRowMapper, buyerId, sellerId, listingId);
    if (chats.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(chats.get(0).getChatId());
    }
  }

  /**
   * Deletes a chat by its ID.
   *
   * @param chatId The ID of the chat to delete
   * @return true if the chat was deleted successfully, false otherwise
   */
  public boolean deleteChat(String chatId) {
    String query = "DELETE FROM chats WHERE chat_id = ?";
    int rowsAffected = jdbcTemplate.update(query, chatId);
    return rowsAffected > 0;
  }

  /**
   * Retrieves all chats for a specific user (buyer or seller).
   *
   * @param userId The ID of the user (buyer or seller)
   * @return a list of chats associated with the user
   */
  public List<Chat> getAllUserChats(long userId) {
    String query = "SELECT * FROM chats WHERE buyer_id = ? OR seller_id = ?";
    return jdbcTemplate.query(query, chatRowMapper, userId, userId);
  }

  /**
   * Retrieves a chat by its ID.
   *
   * @param chatId The ID of the chat to retrieve
   * @return the chat object if found, null otherwise
   */
  public Chat getChatById(String chatId) {
    String query = "SELECT * FROM chats WHERE chat_id = ?";
    return jdbcTemplate.queryForObject(query, chatRowMapper, chatId);
  }

  /**
   * Checks if a user is a participant in a specific chat.
   *
   * @param userId The ID of the user to check
   * @param chatId The ID of the chat to check
   * @return true if the user is a participant, false otherwise
   */
  public boolean userIsParticipant(long userId, long chatId) {
    String query = "SELECT * FROM chats WHERE chat_id = ? AND (buyer_id = ? OR seller_id = ?)";
    List<Chat> chats = jdbcTemplate.query(query, chatRowMapper, chatId, userId, userId);
    return !chats.isEmpty();
  }
}