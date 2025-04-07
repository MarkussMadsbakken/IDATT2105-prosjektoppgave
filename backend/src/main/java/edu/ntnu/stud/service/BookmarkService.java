package edu.ntnu.stud.service;

import edu.ntnu.stud.factories.NotificationFactory;
import edu.ntnu.stud.model.Bookmark;
import edu.ntnu.stud.model.BookmarkUserRequest;
import edu.ntnu.stud.model.ListingResponse;
import edu.ntnu.stud.repo.BookmarkRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class handles the business logic for bookmarks.
 */
@Service
public class BookmarkService {

  @Autowired
  private BookmarkRepo bookmarkRepo;
  @Autowired
  private JWTService jwtService;
  @Autowired
  private ListingService listingService;
  @Autowired
  private NotificationService notificationService;

  /**
   * Adds a new bookmark to the database.
   *
   * @param bookmark the bookmark to be added
   */
  public void addBookmark(BookmarkUserRequest bookmark, String token) {

    // Extract userId
    Long userId = jwtService.extractUserId(token.substring(7));

    // Add bookmark to the database
    bookmarkRepo.addBookmark(
        new Bookmark(userId, bookmark.getListingId()));

    // Create a notification for the listing owner
    notificationService.addNotification(
        NotificationFactory.createBookmarkNotification(
            bookmark.getListingId(),
            listingService.getListingByUuid(bookmark.getListingId()).getOwnerId()));
  }

  /**
   * Removes a bookmark from the database.
   *
   * @param bookmark the bookmark to be removed
   */
  public void removeBookmark(BookmarkUserRequest bookmark, String token) {
    // Extract userId from the JWT token
    bookmarkRepo.removeBookmark(
        new Bookmark(jwtService.extractUserId(token.substring(7)), bookmark.getListingId()));
  }

  /**
   * Checks if a bookmark exists in the database.
   *
   * @param listingId the ID of the listing to check
   * @param token the JWT token of the user
   * @return true if the bookmark exists, false otherwise
   */
  public boolean bookmarkExists(String listingId, String token) {
    // Extract userId from the JWT token
    return bookmarkRepo.bookmarkExists(
        new Bookmark(jwtService.extractUserId(token.substring(7)), listingId));
  }

  /**
   * Gets a list of bookmarks for a user from the database.
   */
  public List<ListingResponse> getBookmarkedListingsFromUser(String token) {
    // Extract userId from the JWT token

    long userId = jwtService.extractUserId(token.substring(7));
    List<String> listingIds = bookmarkRepo.getBookmarksFromUser(userId);

    List<ListingResponse> listings = listingService.getListingsByUuids(listingIds);
    return listings;
  }

  /**
   * Gets a list of bookmarks for a listing from the database.
   */
  public List<Long> getBookmarksFromListing(String listingId) {
    return bookmarkRepo.getBookmarksFromListing(listingId);
  }

}
