package edu.ntnu.stud.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

/**
 * Test class for JWTService.
 */
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

  private JWTService jwtService;

  @BeforeEach
  public void setUp() {
    jwtService = new JWTService();
  }

  @Test
  public void testGenerateToken() {
    String token = jwtService.generateToken("testuser", 1L, true);
    assertThat(token).isNotNull();
  }

  @Test
  public void testExtractUserName() {
    String token = jwtService.generateToken("testuser", 1L, true);
    String username = jwtService.extractUserName(token);
    assertThat(username).isEqualTo("testuser");
  }

  @Test
  public void testExtractUserId() {
    String token = jwtService.generateToken("testuser", 1L, true);
    Long userId = jwtService.extractUserId(token);
    assertThat(userId).isEqualTo(1L);
  }

  @Test
  public void testExtractIsAdmin() {
    String token = jwtService.generateToken("testuser", 1L, true);
    boolean isAdmin = jwtService.extractIsAdmin(token);
    assertThat(isAdmin).isTrue();
  }

  @Test
  public void testValidateToken() {
    String token = jwtService.generateToken("testuser", 1L, true);
    String invalidtoken = jwtService.generateToken("invalidUser", 2L, false);
    UserDetails userDetails = new org.springframework.security.core.userdetails.User(
        "testuser", "", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    UserDetails invalidUserDetails = new org.springframework.security.core.userdetails.User(
        "invaliduser", "", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    assertThat(jwtService.validateToken(token, userDetails)).isTrue();
    assertThat(jwtService.validateToken(invalidtoken, userDetails)).isFalse();
    assertThat(jwtService.validateToken(token, invalidUserDetails)).isFalse();
  }
}
