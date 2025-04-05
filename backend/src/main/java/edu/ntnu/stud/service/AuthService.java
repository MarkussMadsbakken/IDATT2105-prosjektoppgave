package edu.ntnu.stud.service;

import edu.ntnu.stud.model.LoginRequest;
import edu.ntnu.stud.model.LoginResponse;
import edu.ntnu.stud.model.RegisterRequest;
import edu.ntnu.stud.model.RegisterResponse;
import edu.ntnu.stud.model.User;
import edu.ntnu.stud.repo.UserRepo;
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
  private UserRepo repo;

  private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

  /**
   * Registers a new user in the system.
   */
  public RegisterResponse register(RegisterRequest registerRequest) throws Exception {
    if (repo.getUserByUsername(registerRequest.getUsername()) != null) {
      throw new Exception("User with username "
          + registerRequest.getUsername()
          + " already exists"
      );
    }

    User user = new User();

    user.setUsername(registerRequest.getUsername());
    user.setPassword(encoder.encode(registerRequest.getPassword()));
    repo.addUser(user);

    // Log the user in after registration
    LoginResponse loginResponse = authenticateUser(user.getUsername(), registerRequest.getPassword());

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

    if (authentication.isAuthenticated() && repo.getUserByUsername(username) != null) {
      User user = repo.getUserByUsername(username);
      String token = jwtService.generateToken(user.getUsername(), user.getId(), user.isAdmin());
      return new LoginResponse("Login successful!", token);
    } else {
      return new LoginResponse("Invalid username or password", null);
    }
  }
}