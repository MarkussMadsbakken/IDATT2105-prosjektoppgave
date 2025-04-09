package edu.ntnu.stud.service;

import edu.ntnu.stud.model.ChangeCredentialsRequest;
import edu.ntnu.stud.model.ChangeCredentialsResponse;
import edu.ntnu.stud.model.LoginRequest;
import edu.ntnu.stud.model.LoginResponse;
import edu.ntnu.stud.model.RegisterRequest;
import edu.ntnu.stud.model.RegisterResponse;
import edu.ntnu.stud.model.base.User;
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
  public RegisterResponse register(RegisterRequest registerRequest) {

    Validate.that(registerRequest.getUsername(),
        Validate.isNotBlankOrNull(),
        "Username cannot be blank or null"
    );
    Validate.that(isUsernameAvailable(registerRequest.getUsername()),
        Validate.isTrue(),
        "Username is not available"
    );

    Validate.that(registerRequest.getPassword(),
        Validate.isNotBlankOrNull(),
        "New password cannot be blank or null");
    Validate.that(isPasswordValid(registerRequest.getPassword()),
        Validate.isTrue(),
        "New password must be at least 8 characters long, including both a letter and a digit"
    );

    User user = new User();

    user.setUsername(registerRequest.getUsername());
    user.setPassword(encoder.encode(registerRequest.getPassword()));
    userRepo.addUser(user);

    String token = authenticateUser(registerRequest.getUsername(), registerRequest.getPassword());

    return new RegisterResponse("Registration successful!", token);
  }

  /**
   * Verifies the login credentials of a user.
   */
  public LoginResponse login(LoginRequest loginRequest) {
    String token = authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
    return new LoginResponse("Login successful!", token);
  }

  /**
   * Authenticates the user and generates a token.
   */
  private String authenticateUser(String username, String password) {
    Authentication authentication = authManager
        .authenticate(new UsernamePasswordAuthenticationToken(username, password));

    if (authentication.isAuthenticated() && userRepo.getUserByUsername(username) != null) {
      User user = userRepo.getUserByUsername(username);
      return jwtService.generateToken(user.getUsername(), user.getId(), user.isAdmin());
    } else {
      return null;
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
    Validate.that(changeCredentialsRequest.getUsername(),
        Validate.isNotBlankOrNull(),
        "New username cannot be blank or null"
    );
    // check if new username is availanle if the request includes a new username,
    // skip if user is keeping old username
    if (!user.getUsername().equals(changeCredentialsRequest.getUsername())) {
      Validate.that(isUsernameAvailable(changeCredentialsRequest.getUsername()),
          Validate.isTrue(),
          "New username is not available"
      );
    }
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

    String newToken = authenticateUser(
        changeCredentialsRequest.getUsername(),
        changeCredentialsRequest.getNewPassword()
    );
    return new ChangeCredentialsResponse("Password changed successfully", newToken);
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
   * Checks if a username is available.
   *
   * @param username   the username to check availability of
   * @return true if the username is available or false if otherwise
   */
  public boolean isUsernameAvailable(String username) {
    return userRepo.getUserByUsername(username) == null;
  }
}