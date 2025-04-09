package edu.ntnu.stud.repo;

import static org.assertj.core.api.Assertions.assertThat;

import edu.ntnu.stud.model.base.Listing;
import edu.ntnu.stud.model.base.UserHistory;
import java.sql.Timestamp;
import java.util.List;
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
 * Test class for UserHistoryRepo.
 */
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserHistoryRepoTest {

  @Autowired
  private UserHistoryRepo userHistoryRepo;
  @Autowired
  private ListingRepo listingRepo;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private UserHistory userHistory;
  private Listing listing;

  /**
   * Sets up the test environment by creating a test listing and user history.
   */
  @BeforeEach
  public void setUp() {
    listing = new Listing();
    listing.setName("Test Listing");
    listing.setDescription("This is a test listing.");
    listing.setPrice(100.0);
    listing.setOwnerId(2L);
    listingRepo.saveListing(listing);

    userHistory = new UserHistory();
    userHistory.setUserId(1L);
    userHistory.setListingId(listingRepo.getAllListings().get(0).getUuid());
    userHistory.setTimestamp(new Timestamp(System.currentTimeMillis()));
  }

  @AfterEach
  public void tearDown() {
    jdbcTemplate.execute("DELETE FROM user_history");
    jdbcTemplate.execute("DELETE FROM listings");
  }

  @Test
  public void testAddUserHistory() {
    userHistoryRepo.addUserHistory(userHistory);

    List<UserHistory> history = userHistoryRepo.getUserHistory(userHistory.getUserId());
    assertThat(history).hasSize(1);
    assertThat(history.get(0).getUserId()).isEqualTo(userHistory.getUserId());
    assertThat(history.get(0).getListingId()).isEqualTo(userHistory.getListingId());
  }

  @Test
  public void testGetUserHistory() {
    userHistoryRepo.addUserHistory(userHistory);

    List<UserHistory> history = userHistoryRepo.getUserHistory(userHistory.getUserId());
    assertThat(history).isNotEmpty();
    assertThat(history.get(0).getUserId()).isEqualTo(userHistory.getUserId());
    assertThat(history.get(0).getListingId()).isEqualTo(userHistory.getListingId());
  }
}
