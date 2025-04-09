package edu.ntnu.stud.service;

import edu.ntnu.stud.factory.NotificationFactory;
import edu.ntnu.stud.model.ListingResponse;
import edu.ntnu.stud.model.base.Bookmark;
import edu.ntnu.stud.model.request.BookmarkUserRequest;
import edu.ntnu.stud.repo.BookmarkRepo;
import edu.ntnu.stud.util.Validate;
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
   * Validates a bookmark request.
   *
   * @param bookmarkRequest the bookmark request to be validated
   * @param token the JWT token of the user
   * @throws IllegalArgumentException if the request is invalid
   */
  public void validateBookmarkRequest(BookmarkUserRequest bookmarkRequest, String token) {
    Validate.that(bookmarkRepo.bookmarkExists(
        new Bookmark(jwtService.extractUserId(token.substring(7)), bookmarkRequest.getListingId())),
        Validate.isFalse(), 
        "Bookmark already exists");
    Validate.that(listingService.getListingByUuid(bookmarkRequest.getListingId()),
        Validate.isNotNull(),
        "Listing does not exist");
  }

  /**
   * Validate bookmark deletion request.
   *
   * @param bookmarkRequest the bookmark request to be validated
   * @param token the JWT token of the user
   */
  public void validateBookmarkDeletionRequest(BookmarkUserRequest bookmarkRequest, String token) {
    Validate.that(bookmarkRequest.getListingId(), Validate.isNotBlankOrNull(),
        "Listing ID cannot be null or empty");
    Validate.that(bookmarkRepo.bookmarkExists(
        new Bookmark(jwtService.extractUserId(token.substring(7)), bookmarkRequest.getListingId())),
        Validate.isTrue(), 
        "Bookmark does not exist");
  }

  /**
   * Adds a new bookmark to the database.
   *
   * @param bookmark the bookmark to be added
   */
  public void addBookmark(BookmarkUserRequest bookmark, String token) {
    validateBookmarkRequest(bookmark, token);

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
    validateBookmarkDeletionRequest(bookmark, token);

    // Extract userId from the JWT token
    bookmarkRepo.removeBookmark(
        new Bookmark(jwtService.extractUserId(token.substring(7)), bookmark.getListingId()));
  }

  /**
   * Checks if a bookmark exists in the database.
   *
   * @param listingId the ID of the listing to check
   * @param token     the JWT token of the user
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
