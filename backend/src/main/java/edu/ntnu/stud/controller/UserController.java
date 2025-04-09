package edu.ntnu.stud.controller;

import edu.ntnu.stud.model.response.UserImageResponse;
import edu.ntnu.stud.model.response.UserResponse;
import edu.ntnu.stud.model.update.UserUpdate;
import edu.ntnu.stud.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller class for managing user-related operations.
 */
@Tag(name = "User", description = "Endpoints for managing users")
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
  @Operation(summary = "Retrieve a user by their ID",
      description = "Fetches a user based on their unique ID."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User found",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = UserResponse.class))),
      @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
  })
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
  @Operation(summary = "Retrieve a user's image by their ID",
      description = "Fetches the profile image of a user based on their unique ID."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User image found",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = UserImageResponse.class))),
      @ApiResponse(responseCode = "404", description = "User image not found", content = @Content)
  })
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
  @Operation(summary = "Retrieve a user by their username",
      description = "Fetches a user based on their unique username.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User found",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = UserResponse.class))),
      @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
  })
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
  @Operation(summary = "Retrieve all users",
      description = "Fetches a list of all users in the system."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Users found",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = UserResponse.class))),
      @ApiResponse(responseCode = "204", description = "No users found", content = @Content)
  })
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
  @Operation(summary = "Update user information",
      description = "Updates the details of a user in the system."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User updated successfully",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = UserResponse.class))),
      @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
      @ApiResponse(responseCode = "409", description = "Username conflict", content = @Content),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
  })
  @PutMapping("/update")
  public ResponseEntity<UserResponse> updateUser(
      @RequestPart("userUpdate") UserUpdate user,
      @RequestPart(name = "userImage", required = false) MultipartFile userImage,
      @RequestHeader("Authorization") String token) {
    if (!userService.userExists(token)) {
      logger.error("User does not exist");
      return ResponseEntity.notFound().build();
    }
    UserResponse updatedUser = userService.updateUser(user, token, userImage);
    if (updatedUser != null) {
      logger.info("user updated successfully");
      return ResponseEntity.ok(updatedUser);
    }
    logger.error("failed updating user");
    return ResponseEntity.internalServerError().build();
  }
}
