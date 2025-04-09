package edu.ntnu.stud.repo;

import static org.assertj.core.api.Assertions.assertThat;

import edu.ntnu.stud.model.Bookmark;
import edu.ntnu.stud.model.Listing;
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
 * Test class for BookmarkRepo.
 */
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class BookmarkRepoTest {

  private Listing listing;
  private Listing listing2;

  @Autowired
  private BookmarkRepo bookmarkRepo;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private ListingRepo listingRepo;

  /**
   * Sets up the test environment by creating a test listing.
   */
  @BeforeEach
  public void setUp() {
    listing = new Listing("test listing for bookmarkRepoTest", 100.0,
        "test listing for bookmarkRepoTest", 1, 1, 0, 0, 1L);
    listing2 = new Listing("test listing 2for bookmarkRepoTest", 100.0,
        "test listing for bookmarkRepoTest", 2, 2,
        0, 0, 2L);
    listingRepo.saveListing(listing);
    listingRepo.saveListing(listing2);
  }

  @AfterEach
  public void tearDown() {
    jdbcTemplate.execute("DELETE FROM bookmarks");
    jdbcTemplate.execute("DELETE FROM listings");
  }

  @Test
  public void testAddBookmark() {
    Bookmark bookmark = new Bookmark(1L, listing.getUuid());

    bookmarkRepo.addBookmark(bookmark);

    boolean exists = bookmarkRepo.bookmarkExists(bookmark);
    assertThat(exists).isTrue();
  }

  @Test
  public void testRemoveBookmark() {
    Bookmark bookmark = new Bookmark(1L, listing.getUuid());

    bookmarkRepo.addBookmark(bookmark);
    bookmarkRepo.removeBookmark(bookmark);

    boolean exists = bookmarkRepo.bookmarkExists(bookmark);
    assertThat(exists).isFalse();
  }

  @Test
  public void testBookmarkExists() {
    Bookmark bookmark = new Bookmark(1L, listing.getUuid());

    bookmarkRepo.addBookmark(bookmark);

    boolean exists = bookmarkRepo.bookmarkExists(bookmark);
    assertThat(exists).isTrue();
  }

  @Test
  public void testGetBookmarksFromUser() {
    Bookmark bookmark1 = new Bookmark(1L, listing.getUuid());
    Bookmark bookmark2 = new Bookmark(1L, listing2.getUuid());

    bookmarkRepo.addBookmark(bookmark1);
    bookmarkRepo.addBookmark(bookmark2);

    List<String> bookmarks = bookmarkRepo.getBookmarksFromUser(1L);
    assertThat(bookmarks).hasSize(2);
    assertThat(bookmarks).contains(listing.getUuid(), listing2.getUuid());
  }

  @Test
  public void testGetBookmarksFromListing() {
    Bookmark bookmark1 = new Bookmark(1L, listing.getUuid());
    Bookmark bookmark2 = new Bookmark(2L, listing.getUuid());

    bookmarkRepo.addBookmark(bookmark1);
    bookmarkRepo.addBookmark(bookmark2);

    List<Long> bookmarks = bookmarkRepo.getBookmarksFromListing(listing.getUuid());
    assertThat(bookmarks).hasSize(2);
    assertThat(bookmarks).contains(1L, 2L);
  }
}