package edu.ntnu.stud.controller;

import edu.ntnu.stud.model.LoginRequest;
import edu.ntnu.stud.model.LoginResponse;
import edu.ntnu.stud.model.RegisterRequest;
import edu.ntnu.stud.model.RegisterResponse;
import edu.ntnu.stud.service.UserService;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is a controller class for managing user authentication and registration.
 * It provides endpoints for user registration and login, as well as a test
 * endpoint.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private UserService service;

  Logger logger = LoggerFactory.getLogger(AuthController.class);

  /**
   * This method handles user registration requests. It creates a new user in the
   * system and returns a response indicating success or failure.
   *
   * @param registerRequest the registration request containing user details
   * @return a ResponseEntity containing the registration response or an error message
   */
  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
    try {
      RegisterResponse registerResponse = service.register(registerRequest);
      return ResponseEntity.ok(registerResponse);

    } catch (Exception e) {
      Map<String, String> errorResponse = new HashMap<>();
      errorResponse.put("error", e.getMessage());
      return ResponseEntity.badRequest().body(errorResponse);
    }
  }

  /**
   * This method handles user login requests. It verifies the user's credentials
   * and returns a JWT token if successful.
   *
   * @param loginRequest the login request containing the user's credentials
   * @return a ResponseEntity containing the login response or an error message
   */
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    try {
      LoginResponse loginResponse = service.verify(loginRequest);
      return ResponseEntity.ok(loginResponse);

    } catch (Exception e) {
      Map<String, String> errorResponse = new HashMap<>();
      errorResponse.put("error", e.getMessage());
      return ResponseEntity.badRequest().body(errorResponse);
    }
  }
}