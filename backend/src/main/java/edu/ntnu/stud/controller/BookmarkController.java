package edu.ntnu.stud.controller;

import edu.ntnu.stud.model.BookmarkUserRequest;
import edu.ntnu.stud.service.BookmarkService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  public ResponseEntity<List<String>> getBookmarks(@RequestHeader("Authorization") String token) {
    List<String> bookmarks = bookmarkService.getBookmarksFromUser(token.substring(7));
    return ResponseEntity.ok(bookmarks);
  }

  /**
   * Adds a new bookmark to the database.
   */
  @PostMapping
  public ResponseEntity<Void> addBookmark(
      BookmarkUserRequest bookmark, @RequestHeader("Authorization") String token) {
    bookmarkService.addBookmark(bookmark, token);
    return ResponseEntity.ok().build();
  }

  /**
   * Removes a bookmark from the database.
   */
  @PostMapping("/remove")
  public ResponseEntity<Void> removeBookmark(
      BookmarkUserRequest bookmark, @RequestHeader("Authorization") String token) {
    bookmarkService.removeBookmark(bookmark, token);
    return ResponseEntity.ok().build();
  }

  /**
   * Checks if a bookmark exists in the database.
   */
  @PostMapping("/exists")
  public ResponseEntity<Boolean> bookmarkExists(
      BookmarkUserRequest bookmark, @RequestHeader("Authorization") String token) {
    boolean exists = bookmarkService.bookmarkExists(bookmark, token);
    return ResponseEntity.ok(exists);
  }
}
