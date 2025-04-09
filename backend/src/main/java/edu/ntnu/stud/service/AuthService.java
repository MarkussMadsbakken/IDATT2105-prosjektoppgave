package edu.ntnu.stud.service;

import edu.ntnu.stud.model.ChangeCredentialsRequest;
import edu.ntnu.stud.model.ChangeCredentialsResponse;
import edu.ntnu.stud.model.LoginRequest;
import edu.ntnu.stud.model.LoginResponse;
import edu.ntnu.stud.model.RegisterRequest;
import edu.ntnu.stud.model.RegisterResponse;
import edu.ntnu.stud.model.User;
import edu.ntnu.stud.repo.UserRepo;
import edu.ntnu.stud.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for handling auth-related operations such as registration and login.
 */
@Service
public class AuthService {

  @Autowired
  private JWTService jwtService;

  @Autowired
  AuthenticationManager authManager;

  @Autowired
  private UserRepo userRepo;

  private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

  /**
   * Registers a new user in the system.
   */
  public RegisterResponse register(RegisterRequest registerRequest) throws Exception {
    if (userRepo.getUserByUsername(registerRequest.getUsername()) != null) {
      throw new Exception("User with username "
          + registerRequest.getUsername()
          + " already exists"
      );
    }

    User user = new User();

    user.setUsername(registerRequest.getUsername());
    user.setPassword(encoder.encode(registerRequest.getPassword()));
    userRepo.addUser(user);

    // Log the user in after registration
    LoginResponse loginResponse = authenticateUser(
        user.getUsername(),
        registerRequest.getPassword()
    );

    return new RegisterResponse("Registration successful!", loginResponse.getToken());
  }

  /**
   * Verifies the login credentials of a user.
   */
  public LoginResponse login(LoginRequest loginRequest) {
    return authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
  }

  /**
   * Authenticates the user and generates a token.
   */
  private LoginResponse authenticateUser(String username, String password) {
    Authentication authentication = authManager
        .authenticate(new UsernamePasswordAuthenticationToken(username, password));

    if (authentication.isAuthenticated() && userRepo.getUserByUsername(username) != null) {
      User user = userRepo.getUserByUsername(username);
      String token = jwtService.generateToken(user.getUsername(), user.getId(), user.isAdmin());
      return new LoginResponse("Login successful!", token);
    } else {
      return new LoginResponse("Invalid username or password", null);
    }
  }

  /**
   * changes the credentials of a user.
   *
   * @param changeCredentialsRequest request containing old and new password
   *                                 along with desired username
   * @return response containing feedback message and new token
   */
  public ChangeCredentialsResponse changeCredentials(
      ChangeCredentialsRequest changeCredentialsRequest,
      String token
  ) {
    User user = userRepo.getUserByUsername(
        jwtService.extractUserName(token.substring(7))
    );

    // validations for username
    Validate.that(changeCredentialsRequest.getUsername(),
        Validate.isNotBlankOrNull(),
        "New username cannot be blank or null"
    );
    Validate.that(isUsernameAvailable(changeCredentialsRequest.getUsername(), token),
        Validate.isTrue(),
        "New username is not available"
    );

    // validations for password
    Validate.that(!encoder.matches(changeCredentialsRequest.getCurrentPassword(),
            user.getPassword()),
            Validate.isFalse(),
        "Current password is incorrect"
    );
    Validate.that(changeCredentialsRequest.getNewPassword(),
        Validate.isNotBlankOrNull(),
        "New password cannot be blank or null");
    Validate.that(isPasswordValid(changeCredentialsRequest.getNewPassword()),
        Validate.isTrue(),
        "New password must be at least 8 characters long, including both a letter and a digit"
    );
    Validate.that(
        changeCredentialsRequest.getCurrentPassword()
            .equals(changeCredentialsRequest.getNewPassword()),
        Validate.isFalse(),
        "New password cannot be the same as current password"
    );


    // saving the new credentials
    user.setPassword(encoder.encode(changeCredentialsRequest.getNewPassword()));
    user.setUsername(changeCredentialsRequest.getUsername());
    userRepo.updateUserCredentials(user);

    LoginResponse loginResponse = authenticateUser(
        changeCredentialsRequest.getUsername(),
        changeCredentialsRequest.getNewPassword()
    );
    return new ChangeCredentialsResponse("Password changed successfully", loginResponse.getToken());
  }

  /**
   * Verifies that a password is valid.
   *
   * @param password password to check validity of
   * @return true if the password is valid,
   *         meaning it is atleast 8 characters long and contains both a letter and digit,
   *         false if otherwise
   */
  private boolean isPasswordValid(String password) {
    if (password == null || password.length() < 8) {
      return false;
    }

    boolean hasLetter = false;
    boolean hasNumber = false;

    for (char c : password.toCharArray()) {
      if (Character.isLetter(c)) {
        hasLetter = true;
      } else if (Character.isDigit(c)) {
        hasNumber = true;
      }

      if (hasLetter && hasNumber) {
        return true;
      }
    }

    return false;
  }

  /**
   * Verfies that a username is available.
   *
   * @param username   the username to verify
   * @param token      the JWT token of the user making the request
   * @return true if the username is available or is already used by the current user,
   *         false otherwise
   */
  public boolean isUsernameAvailable(String username, String token) {
    long userId = jwtService.extractUserId(token.substring(7));
    User existingUserByUsername = userRepo.getUserByUsername(username);
    return existingUserByUsername == null || existingUserByUsername.getId() == userId;
  }
}