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
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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
   *
   * @param id the ID of the user to retrieve
   * @return the ResponseEntity containing the UserResponse if found, or a 404
   *         status if not found
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

  /**
   * Retrieves the image of a user by their ID.
   *
   * @param id the ID of the user whose image is to be retrieved
   * @return the ResponseEntity containing the UserImageResponse if found,
   *         or a 404 status if not found
   */
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
   *
   * @param username the username of the user to retrieve
   * @return the ResponseEntity containing the UserResponse if found, or a 404
   *         status if not found
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
   *
   * @return the ResponseEntity containing the list of UserResponse if found,
   *         or a 204 status if not found
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
   * @param user      the User object containing updated information
   * @param token     the JWT token of the user
   * @param userImage the users profile image (optional)
   * @return a ResponseEntity indicating the result of the update operation
   */
  @PutMapping("/update")
  public ResponseEntity<UserResponse> updateUser(
      @RequestPart("userUpdate") UserUpdate user,
      @RequestPart(name = "userImage", required = false) MultipartFile userImage,
      @RequestHeader("Authorization") String token) {
    if (!userService.userExists(token)) {
      logger.error("User does not exist");
      return ResponseEntity.notFound().build();
    }
    if (!userService.verifyUsername(user, token)) {
      logger.error("Username already exists");
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
    UserResponse updatedUser = userService.updateUser(user, token, userImage);
    if (updatedUser != null) {
      logger.info("user updated successfully");
      return ResponseEntity.ok(updatedUser);
    }
    logger.error("failed updating user");
    return ResponseEntity.internalServerError().build();
  }

  /**
   * Handles MissingServletRequestPartException.
   *
   * @param ex the MissingServletRequestPartException
   * @return the ResponseEntity with a 400 status and error message
   */
  @ExceptionHandler(MissingServletRequestPartException.class)
  public ResponseEntity<String> handleMissingServletRequestPartException(
      MissingServletRequestPartException ex) {
    logger.error("Missing request part: {}", ex.getRequestPartName(), ex);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body("Required part is missing: " + ex.getRequestPartName());
  }

  /**
   * Handles MethodArgumentTypeMismatchException.
   *
   * @param ex the MethodArgumentTypeMismatchException
   * @return the ResponseEntity with a 400 status and error message
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<String> handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException ex) {
    logger.error("Method argument type mismatch: {}", ex.getMessage(), ex);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body("Invalid parameter type: " + ex.getName());
  }

  /**
   * Handles NoHandlerFoundException.
   *
   * @param ex the NoHandlerFoundException
   * @return the ResponseEntity with a 404 status and error message
   */
  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<String> handleNoHandlerFoundException(
      NoHandlerFoundException ex) {
    logger.error("No handler found for request: {}", ex.getRequestURL(), ex);
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body("Endpoint not found: " + ex.getRequestURL());
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<String> handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException ex) {
    logger.error("Request method not supported: {}", ex.getMethod(), ex);
    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
        .body("Request method " + ex.getMethod() + " not supported for this endpoint.");
  }
}
