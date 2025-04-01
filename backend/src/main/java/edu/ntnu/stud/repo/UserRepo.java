package edu.ntnu.stud.repo;

import edu.ntnu.stud.model.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
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
    String query = "INSERT INTO users "
        + "(username, password, first_name, last_name, is_admin, image_path) "
        + "VALUES (?, ?, ?, ?, ?, ?)";
    try (Connection connection = DriverManager.getConnection(url, this.user, this.password);
        PreparedStatement statement = 
            connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

      statement.setString(1, user.getUsername());
      statement.setString(2, user.getPassword());
      statement.setString(3, user.getFirstName());
      statement.setString(4, user.getLastName());
      statement.setBoolean(5, user.isAdmin());
      statement.setString(6, user.getImagePath());

      int affectedRows = statement.executeUpdate();
      if (affectedRows > 0) {
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
          if (generatedKeys.next()) {
            user.setId(generatedKeys.getLong(1));
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Retrieves a user from the database by their ID.
   *
   * @param id the ID of the user to retrieve
   * @return the User object, or null if not found
   */
  public User getUserById(long id) {
    String query = "SELECT * FROM users WHERE id = ?";
    try (Connection connection = DriverManager.getConnection(url, this.user, this.password);
        PreparedStatement statement = connection.prepareStatement(query)) {

      statement.setLong(1, id);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return new User(
              resultSet.getString("username"),
              resultSet.getString("password"),
              resultSet.getString("first_name"),
              resultSet.getString("last_name"),
              resultSet.getString("image_path"));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Retrieves a user from the database by their username.
   *
   * @param username the username of the user to retrieve
   * @return the User object, or null if not found
   **/
  public User getUserByUsername(String username) {
    String query = "SELECT * FROM users WHERE username = ?";
    try (Connection connection = DriverManager.getConnection(url, this.user, this.password);
        PreparedStatement statement = connection.prepareStatement(query)) {

      statement.setString(1, username);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          User user = new User(
              resultSet.getString("username"),
              resultSet.getString("password"),
              resultSet.getString("first_name"),
              resultSet.getString("last_name"),
              resultSet.getString("image_path"));
          user.setId(resultSet.getLong("id"));
          user.setCreatedAt(resultSet.getString("created_at"));
          user.setAdmin(resultSet.getBoolean("is_admin"));
          return user;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Retrieves all users from the database.
   *
   * @return a list of User objects
   */
  public List<User> getAllUsers() {
    List<User> users = new ArrayList<>();
    String query = "SELECT * FROM users";
    try (Connection connection = DriverManager.getConnection(url, this.user, this.password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query)) {

      while (resultSet.next()) {
        User user = new User(
            resultSet.getString("username"),
            resultSet.getString("password"),
            resultSet.getString("first_name"),
            resultSet.getString("last_name"),
            resultSet.getString("image_path"));
        user.setId(resultSet.getLong("id"));
        user.setCreatedAt(resultSet.getString("created_at"));
        user.setAdmin(resultSet.getBoolean("is_admin"));
        users.add(user);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return users;
  }

  /**
   * Updates the details of an existing user in the database.
   *
   * @param user the User object with updated details
   */
  public void updateUser(User user) {
    String query = "UPDATE users SET username = ?, password = ?, first_name = ?, last_name = ?, "
        + "is_admin = ?, image_path = ? WHERE id = ?";
    try (Connection connection = DriverManager.getConnection(url, this.user, this.password);
        PreparedStatement statement = connection.prepareStatement(query)) {

      statement.setString(1, user.getUsername());
      statement.setString(2, user.getPassword());
      statement.setString(3, user.getFirstName());
      statement.setString(4, user.getLastName());
      statement.setBoolean(5, user.isAdmin());
      statement.setString(6, user.getImagePath());
      statement.setLong(7, user.getId());

      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}