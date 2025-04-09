package edu.ntnu.stud.repo;

import static org.assertj.core.api.Assertions.assertThat;

import edu.ntnu.stud.model.base.User;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;


/**
 * Test class for UserRepo.
 */
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserRepoTest {

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private User user;

  /** 
   * Sets up the test environment by creating a test user.
   */
  @BeforeEach
  public void setUp() {
    user = new User();
    user.setId(10L);
    user.setUsername("testuser10");
    user.setPassword("password");
  }

  @AfterEach
  public void tearDown() {
    jdbcTemplate.execute("DELETE FROM users");
  }

  /**
   * Inserts test users into the database for other tests to use.
   *
   * @param jdbcTemplate the JdbcTemplate to execute SQL commands
   */
  @AfterAll
  public static void insertTestUsers(@Autowired JdbcTemplate jdbcTemplate) {
    jdbcTemplate.execute(
        "INSERT INTO users (id, username, password) VALUES (1, 'testuser', 'password');");
    jdbcTemplate.execute(
        "INSERT INTO users (id, username, password) VALUES (2, 'testuser2', 'password');");
  }

  @Test
  public void testAddUser() {
    userRepo.addUser(user);

    User retrieved = userRepo.getUserById(user.getId());
    assertThat(retrieved).isNotNull();
    assertThat(retrieved.getUsername()).isEqualTo(user.getUsername());
  }

  @Test
  public void testGetUserById() {
    userRepo.addUser(user);

    User retrieved = userRepo.getUserById(user.getId());
    assertThat(retrieved).isNotNull();
    assertThat(retrieved.getId()).isEqualTo(user.getId());
  }

  @Test
  public void testGetAllUsers() {
    userRepo.addUser(user);

    List<User> users = userRepo.getAllUsers();
    assertThat(users).isNotEmpty();
    assertThat(users.get(0).getUsername()).isEqualTo(user.getUsername());
  }

  @Test
  public void testGetUserByUsername() {
    userRepo.addUser(user);

    User retrieved = userRepo.getUserByUsername(user.getUsername());
    assertThat(retrieved).isNotNull();
    assertThat(retrieved.getUsername()).isEqualTo(user.getUsername());
  }

  @Test
  public void testUpdateUser() {
    userRepo.addUser(user);

    user.setFirstName("UpdatedFirstName");
    user.setLastName("UpdatedLastName");
    boolean updated = userRepo.updateUser(user);

    assertThat(updated).isTrue();

    User retrieved = userRepo.getUserById(user.getId());
    assertThat(retrieved.getFirstName()).isEqualTo("UpdatedFirstName");
    assertThat(retrieved.getLastName()).isEqualTo("UpdatedLastName");
  }

  @Test
  public void testUpdateUserWithoutImage() {
    userRepo.addUser(user);

    user.setFirstName("UpdatedFirstName");
    user.setLastName("UpdatedLastName");
    boolean updated = userRepo.updateUserWithoutImage(user);

    assertThat(updated).isTrue();

    User retrieved = userRepo.getUserById(user.getId());
    assertThat(retrieved.getFirstName()).isEqualTo("UpdatedFirstName");
    assertThat(retrieved.getLastName()).isEqualTo("UpdatedLastName");
  }
}
