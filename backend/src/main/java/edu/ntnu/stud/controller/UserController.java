package edu.ntnu.stud.controller;

import edu.ntnu.stud.model.User;
import edu.ntnu.stud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is a controller class for managing user-related operations.
 * It provides endpoints for user management, such as retrieving user details
 * and updating user information.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserService userService;

  /**
   * Initializes the UserController with a UserService instance.
   *
   * @param userService the UserService instance to be used
   */
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Retrieves a user by their ID.
   *
   * @param id the ID of the user to be retrieved
   * @return the User object with the specified ID, or null if not found
   */
  @GetMapping("/{id}")
  public User getUserById(@PathVariable long id) {
    return userService.getUserById(id);
  }

  /**
   * Retrieves a user by their username.
   *
   * @param username the username of the user to be retrieved
   * @return the User object with the specified username, or null if not found
   */
  @GetMapping("/username/{username}")
  public User getUserByUsername(@PathVariable String username) {
    return userService.getUserByUsername(username);
  }

  /**
   * Updates the details of a user.
   *
   * @param id the ID of the user to be updated
   * @param user the User object containing the updated details
   */
  @PatchMapping("/{id}")
  public void updateUser(@PathVariable long id, @RequestBody User user) {
    user.setId(id);
    userService.updateUser(user);
  }

}
