package edu.ntnu.stud.repo;

import edu.ntnu.stud.model.base.UserHistory;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


/**
 * Repository class for managing UserHistory entities in the database.
 */
@Repository
public class UserHistoryRepo {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  RowMapper<UserHistory> userHistoryRowMapper = (rs, rowNum) -> {
    UserHistory userHistory = new UserHistory();
    userHistory.setId(rs.getLong("id"));
    userHistory.setUserId(rs.getLong("user_id"));
    userHistory.setTimestamp(rs.getTimestamp("timestamp"));
    userHistory.setListingId(rs.getString("listing_id"));
    return userHistory;
  };

  /**
   * Adds a new user history entry to the database.
   *
   * @param userHistory the UserHistory object to be added
   */
  public void addUserHistory(UserHistory userHistory) {
    String query = "INSERT INTO user_history (user_id, listing_id) VALUES (?, ?)";
    jdbcTemplate.update(query,
        userHistory.getUserId(),
        userHistory.getListingId());
  }

  /**
   * Retrieves the user history for a specific user from the database.
   *
   * @param userId the ID of the user whose history is to be retrieved
   * @return a list of UserHistory objects for the specified user
   */
  public List<UserHistory> getUserHistory(long userId) {
    String query = "SELECT * FROM user_history WHERE user_id = ?";
    return jdbcTemplate.query(query, userHistoryRowMapper, userId);
  }

}
