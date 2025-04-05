package edu.ntnu.stud.service;

import edu.ntnu.stud.model.LoginRequest;
import edu.ntnu.stud.model.LoginResponse;
import edu.ntnu.stud.model.RegisterRequest;
import edu.ntnu.stud.model.RegisterResponse;
import edu.ntnu.stud.model.User;
import edu.ntnu.stud.repo.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.lenient;

@SpringBootTest
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

    @BeforeEach
    public void setUp() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("encodedPassword");

        Mockito.lenient().when(userRepo.getUserByUsername("testuser")).thenReturn(user);
        lenient().doNothing().when(userRepo).addUser(user);
        lenient().when(encoder.encode("password")).thenReturn("encodedPassword");
        lenient().when(jwtService.generateToken("testuser", 1L, false)).thenReturn("testToken");

        Authentication authentication = Mockito.mock(Authentication.class);
        lenient().when(authManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        lenient().when(authentication.isAuthenticated()).thenReturn(true);
    }

    @Test
    void register() {
        RegisterRequest registerRequest = new RegisterRequest("testuser1", "password");

        try {
          RegisterResponse registerResponse = authService.register(registerRequest);
          System.out.println(registerResponse.getMessage());
        } catch (Exception e) {
          e.printStackTrace();
          fail("Should not throw exception");
        }

    }

  @Test
  @DisplayName("Should fail registration with used username")
  void registerWithUsedUsername() {
    RegisterRequest registerRequest = new RegisterRequest("testuser", "password");

    try {
      RegisterResponse registerResponse = authService.register(registerRequest);
      fail("Should throw exception");
    } catch (Exception e) {

    }

  }

  //TODO: fix failing test
    @Test
    @DisplayName("Should login successfully")
    void login() {
        LoginRequest loginRequest = new LoginRequest("testuser", "password");

      LoginResponse loginResponse = authService.login(loginRequest);

      assertThat(Objects.equals(loginResponse.getMessage(), "Login successful!"));
      assertThat(loginResponse.getToken()).isNotNull();
    }

    @Test
    @DisplayName("Should fail login with incorrect credentials")
    void loginWithIncorrectCredentials() {
        LoginRequest loginRequest = new LoginRequest("wronguser", "wrongpassword");

        LoginResponse loginResponse = authService.login(loginRequest);

        assertThat(Objects.equals(loginResponse.getMessage(), "Invalid username or password"));
        assertThat(loginResponse.getToken()).isNull();
    }
}