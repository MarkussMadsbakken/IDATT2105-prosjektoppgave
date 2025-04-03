package edu.ntnu.stud.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import edu.ntnu.stud.model.UserResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

/**
 * This is a test class for the UserService class.
 */
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserServiceTest {


  @Mock
  private UserService userService;

  /**
   * Constructor for UserServiceTest.
   */
  public UserServiceTest() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void shouldGetAllUsers() {
    // Given
    List<UserResponse> mockUsers = List.of(new UserResponse(), new UserResponse(), new UserResponse());
    when(userService.getAllUsers()).thenReturn(mockUsers);

    // When
    List<UserResponse> users = userService.getAllUsers();

    // Then
    assertEquals(3, users.size(), "Should return 3 users");
  }
}