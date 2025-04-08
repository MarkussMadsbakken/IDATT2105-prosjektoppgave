package edu.ntnu.stud.service;

import static edu.ntnu.stud.util.ImageUtil.convertBlobToBase64;
import static edu.ntnu.stud.util.ImageUtil.convertMultipartFileToBlob;

import edu.ntnu.stud.model.User;
import edu.ntnu.stud.model.UserImageResponse;
import edu.ntnu.stud.model.UserResponse;
import edu.ntnu.stud.model.UserUpdate;
import edu.ntnu.stud.repo.UserRepo;
import edu.ntnu.stud.util.Validate;

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
   * Retrieves a user by their ID.
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
   * Retrieves a user by their username.
   *
   * @param username the username of the user to retrieve
   * @return the UserResponse object if found, null otherwise
   */
  public UserResponse getUserByUsername(String username) {
    User user = userRepo.getUserByUsername(username);
    return user == null ? null : new UserResponse(user);
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
   * Verfies the username og a user update request.
   *
   * @param userUpdate the UserUpdate object containing the username to verify
   * @param token      the JWT token of the user making the request
   * @return true if the username is valid, false otherwise
   */
  public boolean verifyUsername(UserUpdate userUpdate, String token) {
    long userId = jwtService.extractUserId(token.substring(7));
    User existingUserByUsername = userRepo.getUserByUsername(userUpdate.getUsername());
    if (existingUserByUsername != null && existingUserByUsername.getId() != userId) {
      // User with the same username already exists
      return false;
    }
    return true;
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
    UserResponse existingUsername = getUserByUsername(
        jwtService.extractUserName(token.substring(7)));
    Validate.that(existingUsername == null || existingUsername.getId() == userId,
        Validate.isTrue(), "Username already exists");
    Validate.that(userUpdate.getUsername(), Validate.isNotEmptyOrBlankOrNull(),
        "Username cannot be null or empty");
    
    User user = new User();
    user.setId(userId);
    user.setUsername(userUpdate.getUsername());
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
