package edu.ntnu.stud.repo;

import edu.ntnu.stud.model.Chat;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * Repository class for managing chat-related database operations.
 */
@Repository
public class ChatRepo {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  private final RowMapper<Chat> chatRowMapper = (rs, rowNum) -> {
    Chat chat = new Chat();
    chat.setId(rs.getLong("id"));
    chat.setBuyerId(rs.getLong("buyer_id"));
    chat.setSellerId(rs.getLong("seller_id"));
    chat.setListingId(rs.getString("listing_id"));
    return chat;
  };

  /**
   * Creates a new chat with a buyer, seller and listing.
   *
   * @param buyerId   The ID of the buyer
   * @param sellerId  The ID of the seller
   * @param listingId The ID of the listing
   * @return the amount of rows affected (should be 1 if successful)
   */
  public long createChat(long buyerId, long sellerId, String listingId) {
    String query = "INSERT INTO chat (buyer_id, seller_id, listing_id) VALUES (?, ?, ?)";
    return jdbcTemplate.update(query, buyerId, sellerId, listingId);
  }

  /**
   * Checks if a chat exists by its ID.
   *
   * @param chatId The ID of the chat to check
   * @return true if the chat exists, false otherwise
   */
  public boolean chatExists(long chatId) {
    String query = "SELECT * FROM chat WHERE id = ?";
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
    String query = "SELECT id FROM chat "
        + "WHERE buyer_id = ? AND seller_id = ? AND listing_id = ?";
    List<Long> chatId = jdbcTemplate.query(
          query, (rs, rowNum) -> rs.getLong("id"), buyerId, sellerId, listingId);
    return chatId.isEmpty() 
        ? Optional.empty() 
        : Optional.of(chatId.get(0));
  }

  /**
   * Deletes a chat by its ID.
   *
   * @param chatId The ID of the chat to delete
   * @return true if the chat was deleted successfully, false otherwise
   */
  public boolean deleteChat(String chatId) {
    String query = "DELETE FROM chat WHERE id = ?";
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
    String query = "SELECT * FROM chat WHERE buyer_id = ? OR seller_id = ?";
    return jdbcTemplate.query(query, chatRowMapper, userId, userId);
  }

  /**
   * Retrieves a chat by its ID.
   *
   * @param chatId The ID of the chat to retrieve
   * @return the chat object if found, null otherwise
   */
  public Chat getChatById(Long chatId) {
    String query = "SELECT * FROM chat WHERE id = ?";
    List<Chat> chats = jdbcTemplate.query(query, chatRowMapper, chatId);
    return chats.isEmpty() ? null : chats.get(0);
  }

  /**
   * Checks if a user is a participant in a specific chat.
   *
   * @param userId The ID of the user to check
   * @param chatId The ID of the chat to check
   * @return true if the user is a participant, false otherwise
   */
  public boolean userIsParticipant(long userId, long chatId) {
    String query = "SELECT * FROM chat WHERE id = ? AND (buyer_id = ? OR seller_id = ?)";
    List<Chat> chats = jdbcTemplate.query(query, chatRowMapper, chatId, userId, userId);
    return !chats.isEmpty();
  }

  /**
   * Retrieves the ID of the other participant in a chat.
   *
   * @param userId The ID of the user
   * @param chatId The ID of the chat
   * @return the ID of the other participant, or null if not found
   */
  public Long getOtherParticipantId(long userId, long chatId) {
    String query = "SELECT * FROM chat WHERE id = ? AND (buyer_id = ? OR seller_id = ?)";
    List<Chat> chats = jdbcTemplate.query(query, chatRowMapper, chatId, userId, userId);
    if (chats.isEmpty()) {
      return null;
    }
    Chat chat = chats.get(0);
    return chat.getBuyerId() == userId ? chat.getSellerId() : chat.getBuyerId();
  }
}