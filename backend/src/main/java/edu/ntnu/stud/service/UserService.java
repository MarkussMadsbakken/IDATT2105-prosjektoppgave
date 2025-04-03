package edu.ntnu.stud.service;

import edu.ntnu.stud.model.User;
import edu.ntnu.stud.model.UserResponse;
import edu.ntnu.stud.model.UserUpdate;
import edu.ntnu.stud.repo.UserRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing user-related operations.
 */
@Service
public class UserService {
  @Autowired
  private UserRepo userRepo;
  @Autowired
  private JWTService jwtService;

  /**
   * Retrieves a user by their ID.
   *
   * @param id the ID of the user to retrieve
   * @return the User object if found, null otherwise
   */
  public UserResponse getUserById(long id) {
    return new UserResponse(userRepo.getUserById(id));
  }

  /**
   * Retrieves a user by their username.
   *
   * @param username the username of the user to retrieve
   * @return the User object if found, null otherwise
   */
  public UserResponse getUserByUsername(String username) {
    return new UserResponse(userRepo.getUserByUsername(username));
  }

  /**
   * Retrives all users from the database.
   *
   * @return a list of all users
   */
  public List<UserResponse> getAllUsers() {
    List<User> users = userRepo.getAllUsers();
    return users.stream().map(UserResponse::new).toList();
  }

  /**
   * Updates the information of a user in the database.
   *
   * @param userUpdate the User object containing updated information
   * @param token the JWT token of the user making the request
   */
  public void updateUser(UserUpdate userUpdate, String token) {
    long userId = jwtService.extractUserId(token);
    User user = new User();
    user.setId(userId);
    user.setUsername(userUpdate.getUsername());
    user.setFirstName(userUpdate.getFirstName());
    user.setLastName(userUpdate.getLastName());
    userRepo.updateUser(user);
  }
}
