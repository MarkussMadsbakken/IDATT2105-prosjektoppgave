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
 * Service class for handling user-related operations such as registration and login.
 */
@Service
public class UserService {

  @Autowired
  private JwtServices jwtService;

  @Autowired
  AuthenticationManager authManager;

  @Autowired
  private UserRepo repo;

  private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

  /**
   * Constructor that initializes the UserService with a UserRepo instance.
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

    String message = "Registration successful!";
    String token = jwtService.generateToken(user.getUsername(), user.getId(), user.isAdmin());

    return new RegisterResponse(message, token);
  }

  /**
   * Verifies the login credentials of a user.
   */
  public LoginResponse verify(LoginRequest loginRequest) {
    Authentication authentication = authManager
        .authenticate(new UsernamePasswordAuthenticationToken(
          loginRequest.getUsername(), 
          loginRequest.getPassword()
        ));

    User user = repo.getUserByUsername(loginRequest.getUsername());
    if (authentication.isAuthenticated()) {

      String message = "Login successful!";
      String token = jwtService.generateToken(user.getUsername(), user.getId(), user.isAdmin());

      return new LoginResponse(message, token);

    } else {
      return new LoginResponse("Invalid username or password", null);
    }
  }
}