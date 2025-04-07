package edu.ntnu.stud.controller;

import edu.ntnu.stud.model.BookmarkUserRequest;
import edu.ntnu.stud.service.BookmarkService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import edu.ntnu.stud.model.ListingResponse;

/**
 * Controller class for managing bookmarks.
 */
@RestController
@RequestMapping("/api/bookmark")
public class BookmarkController {

  @Autowired
  private BookmarkService bookmarkService;

  /**
   * Retrieves a list of bookmarks for a user from the database.
   */
  @GetMapping("/user")
  public ResponseEntity<List<ListingResponse>> getBookmarks(
      @RequestHeader("Authorization") String token) {
    List<ListingResponse> bookmarks = bookmarkService.getBookmarkedListingsFromUser(token);
    return ResponseEntity.ok(bookmarks);
  }

  /**
   * Adds a new bookmark to the database.
   */
  @PostMapping
  public ResponseEntity<Void> addBookmark(
      @RequestBody BookmarkUserRequest bookmark, @RequestHeader("Authorization") String token) {
    bookmarkService.addBookmark(bookmark, token);
    return ResponseEntity.ok().build();
  }

  /**
   * Removes a bookmark from the database.
   */
  @DeleteMapping
  public ResponseEntity<Void> removeBookmark(
      @RequestBody BookmarkUserRequest bookmark, @RequestHeader("Authorization") String token) {
    bookmarkService.removeBookmark(bookmark, token);
    return ResponseEntity.ok().build();
  }

  /**
   * Checks if a bookmark exists in the database.
   */
  @GetMapping("/{listingId}/exists")
  public ResponseEntity<Boolean> bookmarkExists(
      @PathVariable String listingId, @RequestHeader("Authorization") String token) {
    boolean exists = bookmarkService.bookmarkExists(listingId, token);
    return ResponseEntity.ok(exists);
  }

  @GetMapping("/{listingId}/count")
  public ResponseEntity<Long> getNumberOfBookmarks(
      @RequestHeader("Authorization") String token, @PathVariable String listingId) {
    long numberOfBookmarks = bookmarkService.getBookmarksFromListing(listingId).size();
    return ResponseEntity.ok(numberOfBookmarks);
  }

}
