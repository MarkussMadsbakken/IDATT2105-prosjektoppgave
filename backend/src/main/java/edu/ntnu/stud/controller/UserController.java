package edu.ntnu.stud.controller;

import edu.ntnu.stud.model.UserImageResponse;
import edu.ntnu.stud.model.UserResponse;
import edu.ntnu.stud.model.UserUpdate;
import edu.ntnu.stud.service.UserService;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Controller class for managing user-related operations.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  private UserService userService;

  Logger logger = LoggerFactory.getLogger(UserController.class);

  /**
   * Retrieves a user by their ID.
   */
  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> getUserById(@PathVariable long id) {
    UserResponse userResponse = userService.getUserById(id);
    if (userResponse != null) {
      logger.info("User with id {} found successfully", id);
      return ResponseEntity.ok(userResponse);
    } else {
      logger.error("User with id {} not found", id);
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/{id}/image")
  public ResponseEntity<UserImageResponse> getUserImage(@PathVariable long id) {
    UserImageResponse userImageResponse = userService.getImageByUserId(id);
    if (userImageResponse != null) {
      logger.info("User image for user with id {} found successfully", id);
      return ResponseEntity.ok(userImageResponse);
    } else {
      logger.error("User image for user with id {} not found", id);
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Retrieves a user by their username.
   */
  @GetMapping("/username/{username}")
  public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
    UserResponse userResponse = userService.getUserByUsername(username);
    if (userResponse != null) {
      logger.info("User with username: {} found successfully", username);
      return ResponseEntity.ok(userResponse);
    } else {
      logger.error("User with username: {} not found", username);
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
      logger.info("Users found: {}", users.size());
      return ResponseEntity.ok(users);
    } else {
      logger.warn("No users found");
      return ResponseEntity.noContent().build();
    }
  }

  /**
   * Updates the information of a user in the database.
   *
   * @param user the User object containing updated information
   * @param token the JWT token of the user
   * @param userImage the users profile image (optional)
   * @return a ResponseEntity indicating the result of the update operation
   */
  @PostMapping("/update")
  public ResponseEntity<Void> updateUser(
      @RequestPart("userUpdate") UserUpdate user,
      @RequestPart(name = "userImage", required = false) MultipartFile userImage,
      @RequestHeader("Authorization") String token) {
    if (userService.updateUser(user, token, userImage)) {
      logger.info("user updated successfully");
      return ResponseEntity.ok().build();
    }
    logger.error("failed updating user");
    return ResponseEntity.internalServerError().build();
  }

  @ExceptionHandler(MissingServletRequestPartException.class)
  public ResponseEntity<String> handleMissingServletRequestPartException(MissingServletRequestPartException ex) {
    logger.error("Missing request part: {}", ex.getRequestPartName(), ex);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Required part is missing: " + ex.getRequestPartName());
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
    logger.error("Method argument type mismatch: {}", ex.getMessage(), ex);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid parameter type: " + ex.getName());
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<String> handleNoHandlerFoundException(NoHandlerFoundException ex) {
    logger.error("No handler found for request: {}", ex.getRequestURL(), ex);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endpoint not found: " + ex.getRequestURL());
  }
}
