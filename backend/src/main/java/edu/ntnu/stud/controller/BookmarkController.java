package edu.ntnu.stud.controller;

import edu.ntnu.stud.model.request.BookmarkUserRequest;
import edu.ntnu.stud.model.response.ListingResponse;
import edu.ntnu.stud.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for managing bookmarks.
 */
@Tag(name = "Bookmark", 
    description = "Endpoints for managing bookmarks")
@RestController
@RequestMapping("/api/bookmark")
public class BookmarkController {

  @Autowired
  private BookmarkService bookmarkService;

  /**
   * Retrieves a list of bookmarked listings for a user from the database.
   */
  @Operation(summary = "Get bookmarked listings", 
      description = "Retrieves a list of bookmarked listings for a user.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successfully retrieved bookmarks"),
  })
  @GetMapping("/user")
  public ResponseEntity<List<ListingResponse>> getBookmarks(
      @RequestHeader("Authorization") String token) {
    List<ListingResponse> bookmarks = bookmarkService.getBookmarkedListingsFromUser(token);
    return ResponseEntity.ok(bookmarks);
  }

  /**
   * Adds a new bookmark to the database.
   */
  @Operation(summary = "Add a bookmark", 
      description = "Adds a new bookmark to the database.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successfully added bookmark"),
  })
  @PostMapping
  public ResponseEntity<Void> addBookmark(
      @RequestBody BookmarkUserRequest bookmark,
      @RequestHeader("Authorization") String token) {
    bookmarkService.addBookmark(bookmark, token);
    return ResponseEntity.ok().build();
  }

  /**
   * Removes a bookmark from the database.
   */
  @Operation(summary = "Remove a bookmark", 
      description = "Removes a bookmark from the database.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successfully removed bookmark"),
  })
  @DeleteMapping
  public ResponseEntity<Void> removeBookmark(
      @RequestBody BookmarkUserRequest bookmark,
      @RequestHeader("Authorization") String token) {
    bookmarkService.removeBookmark(bookmark, token);
    return ResponseEntity.ok().build();
  }

  /**
   * Checks if a bookmark exists in the database.
   */
  @Operation(summary = "Check bookmark existence", 
      description = "Checks if a bookmark exists in the database for a user on a listing.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successfully checked bookmark existence"),
  })
  @GetMapping("/{listingId}/exists")
  public ResponseEntity<Boolean> bookmarkExists(
      @Parameter(description = "Listing ID") @PathVariable String listingId,
      @RequestHeader("Authorization") String token) {
    boolean exists = bookmarkService.bookmarkExists(listingId, token);
    return ResponseEntity.ok(exists);
  }

  /**
   * Retrieves the number of bookmarks for a specific listing.
   */
  @Operation(summary = "Get bookmark count", 
      description = "Retrieves the number of bookmarks for a specific listing.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successfully retrieved bookmark count"),
  })
  @GetMapping("/{listingId}/count")
  public ResponseEntity<Long> getNumberOfBookmarks(
      @Parameter(description = "Listing ID") @PathVariable String listingId) {
    long numberOfBookmarks = bookmarkService.getBookmarksFromListing(listingId).size();
    return ResponseEntity.ok(numberOfBookmarks);
  }

}
