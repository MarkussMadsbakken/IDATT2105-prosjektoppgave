package edu.ntnu.stud.repo;

import edu.ntnu.stud.model.base.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


/**
 * Repository class for managing User entities in the database.
 * This class provides methods to add, retrieve, and list users.
 */
@Repository
public class UserRepo {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  RowMapper<User> userRowMapper = (rs, rowNum) -> {
    User user = new User();
    user.setId(rs.getLong("id"));
    user.setUsername(rs.getString("username"));
    user.setPassword(rs.getString("password"));
    user.setFirstName(rs.getString("first_name"));
    user.setLastName(rs.getString("last_name"));
    user.setCreatedAt(rs.getTimestamp("created_at"));
    user.setAdmin(rs.getBoolean("is_admin"));
    user.setImageBlob(rs.getBlob("image_blob"));
    user.setImageFileType(rs.getString("image_file_type"));
    return user;
  };

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
    return users.isEmpty() ? null : users.getFirst();
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
    return users.isEmpty() ? null : users.getFirst();
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
   * Updates the user in the database.
   *
   * @param user the User object to be updated
   */
  public boolean updateUser(User user) {
    String query = "UPDATE users SET "
        + "first_name = ?, last_name = ?, image_blob = ?, image_file_type = ?"
        + " WHERE id = ?";
    int rowsAffected = jdbcTemplate.update(query,
        user.getFirstName(),
        user.getLastName(),
        user.getImageBlob(),
        user.getImageFileType(),
        user.getId());
    return rowsAffected > 0;
  }

  /**
   * Updates the user in the database. Without affecting the user image.
   *
   * @param user the User object to be updated
   */
  public boolean updateUserWithoutImage(User user) {
    String query = "UPDATE users SET "
        + "first_name = ?, last_name = ?"
        + " WHERE id = ?";
    int rowsAffected = jdbcTemplate.update(query,
        user.getFirstName(),
        user.getLastName(),
        user.getId());
    return rowsAffected > 0;
  }

  /**
   * Updates the users username and password.
   *
   * @param user the User object to be updated
   */
  public boolean updateUserCredentials(User user) {
    String query = "UPDATE users SET "
        + "username = ?, password = ?"
        + " WHERE id = ?";
    int rowsAffected = jdbcTemplate.update(query,
        user.getUsername(),
        user.getPassword(),
        user.getId());
    return rowsAffected > 0;
  }

}
