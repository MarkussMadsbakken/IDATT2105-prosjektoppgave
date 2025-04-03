package edu.ntnu.stud.repo;

import edu.ntnu.stud.model.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


/**
 * Repository class for managing User entities in the database.
 * This class provides methods to add, retrieve, and list users.
 */
@Repository
public class UserRepo {

  @Value("${spring.datasource.url}")
  private String url;

  @Value("${spring.datasource.username}")
  private String user;

  @Value("${spring.datasource.password}")
  private String password;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  RowMapper<User> userRowMapper = (rs, rowNum) -> {
    User user = new User();
    user.setId(rs.getLong("id"));
    user.setUsername(rs.getString("username"));
    user.setPassword(rs.getString("password"));
    user.setFirstName(rs.getString("first_name"));
    user.setLastName(rs.getString("last_name"));
    user.setCreatedAt(rs.getString("created_at"));
    user.setAdmin(rs.getBoolean("is_admin"));
    return user;
  };

  /**
   * Initializes the UserRepo and loads the MySQL JDBC driver.
   */
  public UserRepo() {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Adds a new user to the database.
   *
   * @param user the User object to be added
   */
  public void addUser(User user) {
    String query = "INSERT INTO users (username, password, first_name, last_name, is_admin)"
        + " VALUES (?, ?, ?, ?, ?)";
    jdbcTemplate.update(query,
        user.getUsername(),
        user.getPassword(),
        user.getFirstName(),
        user.getLastName(),
        user.isAdmin());
  }

  /**
   * Retrieves a user from the database by their ID.
   *
   * @param id the ID of the user to retrieve
   * @return the User object, or null if not found
   */
  public User getUserById(long id) {
    String query = "SELECT * FROM users WHERE id = ?";
    List<User> users = jdbcTemplate.query(query, userRowMapper, id);
    return users.isEmpty() ? null : users.get(0);
  }

  /**
   * Retrieves a user from the database by their username.
   *
   * @param username the username of the user to retrieve
   * @return the User object, or null if not found
   **/
  public User getUserByUsername(String username) {
    String query = "SELECT * FROM users WHERE username = ?";
    List<User> users = jdbcTemplate.query(query, userRowMapper, username);
    return users.isEmpty() ? null : users.get(0);
  }

  /**
   * Retrieves all users from the database.
   *
   * @return a list of User objects
   */
  public List<User> getAllUsers() {
    String query = "SELECT * FROM users";
    return jdbcTemplate.query(query, userRowMapper);
  }

  /**
   * Updates the user in the database. This may only update the username, first name, and last name.
   *
   * @param user the User object to be updated
   */
  public void updateUser(User user) {
    String query = "UPDATE users SET username = ?, first_name = ?, last_name = ?,"
        + " WHERE id = ?";
    jdbcTemplate.update(query,
        user.getUsername(),
        user.getFirstName(),
        user.getLastName(),
        user.getId());
  }
}
