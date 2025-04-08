package edu.ntnu.stud.controller;

import edu.ntnu.stud.model.UserHistory;
import edu.ntnu.stud.model.UserHistoryRequest;
import edu.ntnu.stud.service.UserHistoryService;
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
@RestController
@RequestMapping("/api/history")
public class UserHistoryController {
  
  @Autowired
  private UserHistoryService userHistoryService;

  /**
   * Retrieves the user history for a specific user.
   */
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
  @PostMapping
  public ResponseEntity<String> addUserHistory(
      @RequestBody UserHistoryRequest userHistory, @RequestHeader("Authorization") String token) {
    userHistoryService.addUserHistory(userHistory, token);
    return ResponseEntity.ok("User history added successfully.");
  }
}
