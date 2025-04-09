package edu.ntnu.stud.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.lenient;

import edu.ntnu.stud.model.LoginResponse;
import edu.ntnu.stud.model.base.User;
import edu.ntnu.stud.model.request.LoginRequest;
import edu.ntnu.stud.model.request.RegisterRequest;
import edu.ntnu.stud.repo.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

/**
 * Test class for AuthService.
 */
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

  @InjectMocks
  private AuthService authService;

  @Mock
  private UserRepo userRepo;

  @Mock
  private JWTService jwtService;

  @Mock
  private AuthenticationManager authManager;

  @Mock
  private BCryptPasswordEncoder encoder;

  /**
   * Sets up mock objects and their behaviors for the unit tests.
   * This method is executed before each test to ensure a consistent test
   * environment.
   * - Creates a mock `User` object with predefined username, password, and ID.
   * - Configures the `userRepo` mock to return the mock `User` object when
   * `getUserByUsername` is called.
   * - Configures the `userRepo` mock to do nothing when `addUser` is called.
   * - Configures the `encoder` mock to return a predefined encoded password.
   * - Configures the `jwtService` mock to return a predefined token.
   * - Configures the `authManager` mock to return a mock `Authentication` object
   * when `authenticate` is called.
   * - Configures the `authentication` mock to return `true` when
   * `isAuthenticated` is called.
   */
  @BeforeEach
  public void setUp() {
    User user = new User();
    user.setUsername("testuser");
    user.setPassword("encodedPassword");
    user.setId(1);

    Mockito.lenient().when(userRepo.getUserByUsername("testuser")).thenReturn(user);
    lenient().doNothing().when(userRepo).addUser(user);
    lenient().when(encoder.encode("password")).thenReturn("encodedPassword");
    lenient().when(jwtService.generateToken("testuser", 1L, false)).thenReturn("testToken");

    Authentication authentication = Mockito.mock(Authentication.class);
    lenient().when(authManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(authentication);
    lenient().when(authentication.isAuthenticated()).thenReturn(true);
  }

  @Test
  void register() {
    RegisterRequest registerRequest = new RegisterRequest("testuser1", "password1");

    try {
      authService.register(registerRequest);
    } catch (Exception e) {
      fail("Should not throw exception");
    }

  }

  @Test
  @DisplayName("Should fail registration with used username")
  void registerWithUsedUsername() {
    RegisterRequest registerRequest = new RegisterRequest("testuser", "password");

    Exception exception = assertThrows(
        Exception.class, () -> authService.register(registerRequest));

    assertThat(exception.getMessage()).isNotNull();
  }

  @Test
  @DisplayName("Should login successfully")
  void login() {
    LoginRequest loginRequest = new LoginRequest("testuser", "password");

    LoginResponse loginResponse = authService.login(loginRequest);

    assertThat(loginResponse.getToken()).isNotNull();
  }

  @Test
  @DisplayName("Should fail login with incorrect credentials")
  void loginWithIncorrectCredentials() {
    LoginRequest loginRequest = new LoginRequest("wronguser", "wrongpassword");

    LoginResponse loginResponse = authService.login(loginRequest);

    assertThat(loginResponse.getToken()).isNull();
  }
}