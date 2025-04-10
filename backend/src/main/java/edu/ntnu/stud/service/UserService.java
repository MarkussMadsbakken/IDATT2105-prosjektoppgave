package edu.ntnu.stud.service;

import static edu.ntnu.stud.util.ImageUtil.convertBlobToBase64;
import static edu.ntnu.stud.util.ImageUtil.convertMultipartFileToBlob;

import edu.ntnu.stud.model.base.User;
import edu.ntnu.stud.model.response.UserImageResponse;
import edu.ntnu.stud.model.response.UserResponse;
import edu.ntnu.stud.model.update.UserUpdate;
import edu.ntnu.stud.repo.UserRepo;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
   * Retrieves the userResponse DTO of a user by their ID.
   *
   * @param id the ID of the user to retrieve
   * @return the UserResoponse object if found, null otherwise
   */
  public UserResponse getUserById(long id) {
    User user = userRepo.getUserById(id);
    if (user == null) {
      return null;
    }
    return new UserResponse(user);
  }

  /**
   * Retrieves the userResponse DTO of a user by their username.
   *
   * @param username the username of the user to retrieve
   * @return the UserResponse object if found, null otherwise
   */
  public UserResponse getUserResponseByUsername(String username) {
    User user = userRepo.getUserByUsername(username);
    return user == null ? null : new UserResponse(user);
  }

  /**
   * Retrieves a user by their username.
   *
   * @param username the username of the user to retrieve
   * @return the UserResponse object if found, null otherwise
   */
  public User getUserByUsername(String username) {
    return userRepo.getUserByUsername(username);
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
   * Verifies if a user exists in the database by their username.
   *
   * @param token the JWT token of the user making the request
   * @return true if the user exists, false otherwise
   */
  public boolean userExists(String token) {
    String username = jwtService.extractUserName(token.substring(7));
    return userRepo.getUserByUsername(username) != null;
  }

  /**
   * Adds a new user to the database.
   *
   * @param user the User object to add
   */
  public void addUser(User user) {
    userRepo.addUser(user);
  }

  /**
   * Updates the information of a user in the database.
   *
   * @param userUpdate the User object containing updated information
   * @param token      the JWT token of the user making the request
   * @param image      the MultipartFile image representing the users profile
   *                   image
   * @return the corresponding response object for the
   */
  public UserResponse updateUser(UserUpdate userUpdate, String token, MultipartFile image) {
    long userId = jwtService.extractUserId(token.substring(7));
    
    User user = new User();
    user.setId(userId);
    user.setFirstName(userUpdate.getFirstName());
    user.setLastName(userUpdate.getLastName());

    if (image != null) {
      try {
        user.setImageFileType(image.getContentType());
        user.setImageBlob(convertMultipartFileToBlob(image));
      } catch (IOException e) {
        throw new RuntimeException(e);
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }

      if (userRepo.updateUser(user)) {
        return new UserResponse(user);
      }
    }

    if (userRepo.updateUserWithoutImage(user)) {
      return new UserResponse(user);
    }

    return null;
  }

  /**
   * Updates the credentials of an existing user.
   *
   * @param user the User object with updated credentials
   */
  public void updateUserCredentials(User user) {
    userRepo.updateUserCredentials(user);
  }

  /**
   * Retrieves the image of a user by their ID.
   *
   * @param id the ID of the user whose image is to be retrieved
   * @return a UserImageResponse containing the Base64 encoded image and file
   *         type,
   *         or null if the user or image is not found
   * @throws RuntimeException if there is an error converting the image blob to
   *                          Base64
   */
  public UserImageResponse getImageByUserId(long id) {
    User user = userRepo.getUserById(id);
    if (user != null && user.getImageBlob() != null) {
      try {
        return new UserImageResponse(
            convertBlobToBase64(user.getImageBlob()), user.getImageFileType());
      } catch (SQLException e) {
        throw new RuntimeException("Error converting image blob to Base64", e);
      }
    }
    return null;
  }
}
