package edu.ntnu.stud.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import edu.ntnu.stud.model.User;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

/**
 * This is a test class for the UserRepo class.
 */
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepoTest {

  @Mock
  private UserRepo userRepo;

  public UserRepoTest() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void shouldFindAllUsers() {
    // Given
    List<User> mockUsers = List.of(new User(), new User(), new User());
    when(userRepo.getAllUsers()).thenReturn(mockUsers);

    // When
    List<User> users = userRepo.getAllUsers();

    // Then
    assertEquals(3, users.size(), "Should find three users from our mock data");
  }
}