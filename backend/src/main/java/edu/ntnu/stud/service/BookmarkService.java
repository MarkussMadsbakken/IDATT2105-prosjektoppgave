package edu.ntnu.stud.service;

import edu.ntnu.stud.model.Bookmark;
import edu.ntnu.stud.model.BookmarkUserRequest;
import edu.ntnu.stud.repo.BookmarkRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ntnu.stud.model.ListingResponse;
import java.util.ArrayList;


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

  /**
   * Adds a new bookmark to the database.
   *
   * @param bookmark the bookmark to be added
   */
  public void addBookmark(BookmarkUserRequest bookmark, String token) {
    // Add notification to listing owner
    bookmarkRepo.addBookmark(
        new Bookmark(jwtService.extractUserId(token.substring(7)), bookmark.getListingId()));
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
   * @param bookmark the bookmark to be checked
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
  public List<ListingResponse> getBookmarksFromUser(String token) {
    // Extract userId from the JWT token

    long userId = jwtService.extractUserId(token.substring(7));
    List<String> listingIds =bookmarkRepo.getBookmarksFromUser(userId);

    List<ListingResponse> listings = new ArrayList<>();


    for (String listingId : listingIds) {
      ListingResponse listing = listingService.getListingByUuid(listingId);

      if (listing == null){
        System.out.println("Feiling!!");
      }
      else {
        listings.add(listing);
      }
    }
    return listings;
  }

  /**
   * Gets a list of bookmarks for a listing from the database.
   */
  public List<Long> getBookmarksFromListing(String listingId) {
    return bookmarkRepo.getBookmarksFromListing(listingId);
  }

}
