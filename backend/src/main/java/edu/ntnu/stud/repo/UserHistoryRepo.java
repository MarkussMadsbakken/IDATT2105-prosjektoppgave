package edu.ntnu.stud.repo;

import edu.ntnu.stud.model.UserHistory;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * Repository class for managing UserHistory entities in the database.
 */
@Repository
public class UserHistoryRepo {

  @Value("${spring.datasource.url}")
  private String url;
  @Value("${spring.datasource.username}")
  private String user;
  @Value("${spring.datasource.password}")
  private String password;

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
   * Initializes the UserHistoryRepo and loads the MySQL JDBC driver.
   */
  public UserHistoryRepo() {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

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
