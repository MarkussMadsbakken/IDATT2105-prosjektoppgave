package edu.ntnu.stud.repo;

import edu.ntnu.stud.model.Bookmark;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * This class handles the database operations related to bookmarks.
 * It provides methods to add, remove, check existence, and retrieve bookmarks.
 */
@Repository
public class BookmarkRepo {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  private final RowMapper<Bookmark> bookmarkRowMapper = (resultSet, rowNum) -> {
    Bookmark bookmark = new Bookmark();
    bookmark.setUserId(resultSet.getLong("user_id"));
    bookmark.setListingId(resultSet.getString("listing_id"));
    return bookmark;
  };

  /**
   * Adds a new bookmark to the database.
   *
   * @param bookmark the bookmark to be added
   */
  public void addBookmark(Bookmark bookmark) {
    String query = "INSERT INTO bookmarks (user_id, listing_id) VALUES (?, ?)";
    jdbcTemplate.update(query, bookmark.getUserId(), bookmark.getListingId());
  }

  /**
   * Removes a bookmark from the database.
   *
   * @param bookmark the bookmark to be removed
   */
  public void removeBookmark(Bookmark bookmark) {
    String query = "DELETE FROM bookmarks WHERE user_id = ? AND listing_id = ?";
    jdbcTemplate.update(query, bookmark.getUserId(), bookmark.getListingId());
  }

  /**
   * Checks if a bookmark exists in the database.
   *
   * @param bookmark the bookmark to be checked
   * @return true if the bookmark exists, false otherwise
   */
  public boolean bookmarkExists(Bookmark bookmark) {
    String query = "SELECT COUNT(*) FROM bookmarks WHERE user_id = ? AND listing_id = ?";
    return jdbcTemplate.query(
        query, bookmarkRowMapper, bookmark.getUserId(), bookmark.getListingId()).size() > 0;
  }

  /**
   * Retrieves all bookmarks for a user from the database.
   *
   * @param userId the ID of the user whose bookmarks are to be retrieved
   * @return a list of bookmarks for the user in the form of ListingUuids
   */
  public List<String> getBookmarksFromUser(long userId) {
    String query = "SELECT listing_id FROM bookmarks WHERE user_id = ?";
    return jdbcTemplate.query(
        query,
        bookmarkRowMapper,
        userId
    ).stream()
        .map(Bookmark::getListingId)
        .toList();
  }

  /**
   * Retrieves all bookmarks for a listing from the database.
   *
   * @param listingId the ID of the listing whose bookmarks are to be retrieved
   * @return a list of bookmarks for the listing in the form of userIds
   */
  public List<Long> getBookmarksFromListing(String listingId) {
    String query = "SELECT user_id FROM bookmarks WHERE listing_id = ?";
    return jdbcTemplate.queryForList(query, Long.class, listingId);
  }
}
