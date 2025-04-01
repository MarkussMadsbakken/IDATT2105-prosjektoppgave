package edu.ntnu.stud.service;

import edu.ntnu.stud.model.User;
import edu.ntnu.stud.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for handling user-related operations.
 * This class provides methods to manage user data and interactions.
 */
@Service
public class UserService {

  @Autowired
  private UserRepo userRepo;
  
  /**
   * Initializes the UserService with a UserRepo instance.
   *
   * @param userRepo the UserRepo instance to be used
   */
  public UserService(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  /**
   * Retrieves a user by their username.
   *
   * @param username the username of the user to be retrieved
   * @return the User object with the specified username, or null if not found
   */
  public User getUserByUsername(String username) {
    return userRepo.getUserByUsername(username);
  }

  /**
   * Retrieves a user by their ID.
   *
   * @param id the ID of the user to be retrieved
   * @return the User object with the specified ID, or null if not found
   */
  public User getUserById(long id) {
    return userRepo.getUserById(id);
  }

  /**
   * Updates the details of a user.
   *
   * @param user the User object containing the updated details
   */
  public void updateUser(User user) {
    userRepo.updateUser(user);
  }
}
