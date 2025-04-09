package edu.ntnu.stud.controller;

import edu.ntnu.stud.model.ChangeCredentialsRequest;
import edu.ntnu.stud.model.ChangeCredentialsResponse;
import edu.ntnu.stud.model.LoginRequest;
import edu.ntnu.stud.model.LoginResponse;
import edu.ntnu.stud.model.RegisterRequest;
import edu.ntnu.stud.model.RegisterResponse;
import edu.ntnu.stud.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is a controller class responsible for authentication and security-related operations,
 * like login, registration, token management and changing a user's password.
 * It provides endpoints for user registration, login and changing of password.
 */
@Tag(name = "Authentication", 
    description = "Endpoints for user authentication and registration")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthService service;

  Logger logger = LoggerFactory.getLogger(AuthController.class);

  /**
   * This method handles user registration requests. It creates a new user in the
   * system and returns a response indicating success or failure.
   *
   * @param registerRequest the registration request containing user details
   * @return a ResponseEntity containing the registration response or an error message
   */
  @Operation(summary = "Register a new user", 
      description = "Create a new user account with the provided details.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User registered successfully"),
  })
  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
    try {
      RegisterResponse registerResponse = service.register(registerRequest);
      logger.info("User registered successfully: {}", registerRequest.getUsername());
      return ResponseEntity.ok(registerResponse);

    } catch (Exception e) {
      Map<String, String> errorResponse = new HashMap<>();
      errorResponse.put("error", e.getMessage());
      logger.error("Error registering user: {}", e.getMessage());
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
  @Operation(summary = "Login a user", 
      description = "Authenticate a user and return a JWT token.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User logged in successfully"),
  })
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    try {
      LoginResponse loginResponse = service.login(loginRequest);
      logger.info("User logged in successfully: {}", loginRequest.getUsername());
      return ResponseEntity.ok(loginResponse);

    } catch (Exception e) {
      Map<String, String> errorResponse = new HashMap<>();
      errorResponse.put("error", e.getMessage());
      logger.error("Error logging in user: {}", e.getMessage());
      return ResponseEntity.badRequest().body(errorResponse);
    }
  }

  /**
   * This method handles requests to change a user's password.
   * It verifies the user's identity and updates the password if valid.
   *
   * @param changeCredentialsRequest the request containing the current and new passwords
   * @return a ResponseEntity indicating success or failure
   */
  @Operation(summary = "Change user credentials", 
      description = "Update the user's password after verifying their identity.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User credentials changed successfully"),
  })
  @PostMapping("/change-credentials")
  public ResponseEntity<?> changeCredentials(
      @RequestBody ChangeCredentialsRequest changeCredentialsRequest,
      @RequestHeader("Authorization") String token
  ) {
    ChangeCredentialsResponse changeCredentialsResponse = service
        .changeCredentials(changeCredentialsRequest, token);
    logger.info("User credentials changed successfully");
    return ResponseEntity.ok(changeCredentialsResponse);
  }
}