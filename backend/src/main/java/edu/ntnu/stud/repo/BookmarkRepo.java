package edu.ntnu.stud.repo;

import edu.ntnu.stud.model.Bookmark;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * This class handles the database operations related to bookmarks.
 * It provides methods to add, remove, check existence, and retrieve bookmarks.
 */
@Repository
public class BookmarkRepo {
  @Value("${spring.datasource.url}")
  private String url;

  @Value("${spring.datasource.username}")
  private String user;

  @Value("${spring.datasource.password}")
  private String password;

  /**
   * Initializes the repo and loads the MySQL JDBC driver.
   */
  public BookmarkRepo() {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Adds a new bookmark to the database.
   *
   * @param bookmark the bookmark to be added
   */
  public void addBookmark(Bookmark bookmark) {
    String query = "INSERT INTO bookmarks (user_id, listing_id) VALUES (?, ?)";
    try (Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setLong(1, bookmark.getUserId());
      statement.setString(2, bookmark.getListingId());
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Removes a bookmark from the database.
   *
   * @param bookmark the bookmark to be removed
   */
  public void removeBookmark(Bookmark bookmark) {
    String query = "DELETE FROM bookmarks WHERE user_id = ? AND listing_id = ?";
    try (Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setLong(1, bookmark.getUserId());
      statement.setString(2, bookmark.getListingId());
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Checks if a bookmark exists in the database.
   *
   * @param bookmark the bookmark to be checked
   * @return true if the bookmark exists, false otherwise
   */
  public boolean bookmarkExists(Bookmark bookmark) {
    String query = "SELECT COUNT(*) FROM bookmarks WHERE user_id = ? AND listing_id = ?";
    try (Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setLong(1, bookmark.getUserId());
      statement.setString(2, bookmark.getListingId());

      var resultSet = statement.executeQuery();
      if (resultSet.next()) {
        int count = resultSet.getInt(1);
        return count > 0;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Retrieves all bookmarks for a user from the database.
   *
   * @param userId the ID of the user whose bookmarks are to be retrieved
   * @return a list of bookmarks for the user in the form of ListingUuids
   */
  public List<String> getBookmarksFromUser(long userId) {
    String query = "SELECT * FROM bookmarks WHERE user_id = ?";
    List<String> bookmarks = new ArrayList<>();
    try (Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setLong(1, userId);
      var resultSet = statement.executeQuery();
      while (resultSet.next()) {
        String listingId = resultSet.getString("listing_id");
        bookmarks.add(listingId);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return bookmarks;
  }

  /**
   * Retrieves all bookmarks for a listing from the database.
   *
   * @param listingId the ID of the listing whose bookmarks are to be retrieved
   * @return a list of bookmarks for the listing in the form of userIds
   */
  public List<Long> getBookmarksFromListing(String listingId) {
    String query = "SELECT * FROM bookmarks WHERE listing_id = ?";
    List<Long> bookmarks = new ArrayList<>();
    try (Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setString(1, listingId);
      var resultSet = statement.executeQuery();
      while (resultSet.next()) {
        long userId = resultSet.getLong("user_id");
        bookmarks.add(userId);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return bookmarks;
  }

}
