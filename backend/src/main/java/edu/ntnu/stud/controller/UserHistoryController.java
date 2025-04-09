package edu.ntnu.stud.controller;

import edu.ntnu.stud.model.base.UserHistory;
import edu.ntnu.stud.model.request.UserHistoryRequest;
import edu.ntnu.stud.service.UserHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for managing user history in the system.
 */
@Tag(name = "User History", 
    description = "Endpoints for managing user history")
@RestController
@RequestMapping("/api/history")
public class UserHistoryController {
  
  @Autowired
  private UserHistoryService userHistoryService;

  /**
   * Retrieves the user history for a specific user.
   */
  @Operation(summary = "Retrieve user history", 
      description = "Fetches the history of a user based on the provided token.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved user history"),
  })
  @GetMapping
  public ResponseEntity<List<UserHistory>> getUserHistory(
      @RequestHeader("Authorization") String token) {
    List<UserHistory> userHistory = userHistoryService.getUserHistory(token);
    if (userHistory.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(userHistory);
  }

  /**
   * Adds a new user history entry to the system.
   */
  @Operation(summary = "Add user history", 
      description = "Adds a new entry to the user's history.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User history added successfully"),
  })
  @PostMapping
  public ResponseEntity<String> addUserHistory(
      @RequestBody UserHistoryRequest userHistory, @RequestHeader("Authorization") String token) {
    userHistoryService.addUserHistory(userHistory, token);
    return ResponseEntity.ok("User history added successfully.");
  }
}
