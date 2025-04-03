package edu.ntnu.stud.controller;

import edu.ntnu.stud.model.UserResponse;
import edu.ntnu.stud.model.UserUpdate;
import edu.ntnu.stud.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for managing user-related operations.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
  @Autowired
  private UserService userService;

  /**
   * Retrieves a user by their ID.
   */
  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> getUserById(@RequestParam long id) {
    UserResponse userResponse = userService.getUserById(id);
    if (userResponse != null) {
      return ResponseEntity.ok(userResponse);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Retrieves a user by their username.
   */
  @GetMapping("/username/{username}")
  public ResponseEntity<UserResponse> getUserByUsername(@RequestParam String username) {
    UserResponse userResponse = userService.getUserByUsername(username);
    if (userResponse != null) {
      return ResponseEntity.ok(userResponse);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Retrieves all users.
   */
  @GetMapping
  public ResponseEntity<List<UserResponse>> getAllUsers() {
    List<UserResponse> users = userService.getAllUsers();
    if (!users.isEmpty()) {
      return ResponseEntity.ok(users);
    } else {
      return ResponseEntity.noContent().build();
    }
  }

  /**
   * Updates the information of a user in the database.
   *
   * @param user the User object containing updated information
   * @param token the JWT token of the user
   * @return a ResponseEntity indicating the result of the update operation
   */
  @GetMapping("/update")
  public ResponseEntity<Void> updateUser(
      @RequestParam UserUpdate user, @RequestParam String token) {
    userService.updateUser(user, token);
    return ResponseEntity.ok().build();
  }
}
