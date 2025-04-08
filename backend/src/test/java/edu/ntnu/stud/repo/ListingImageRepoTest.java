package edu.ntnu.stud.repo;

import edu.ntnu.stud.model.Listing;
import edu.ntnu.stud.model.ListingImage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ListingImageRepoTest {

  private Listing listing;
  @Autowired
  private ListingImageRepo listingImageRepo;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private ListingRepo listingRepo;

  @BeforeEach
  public void setUp() {
    listing = new Listing("test listing for bookmarkRepoTest", 100.0,
        "test listing for bookmarkRepoTest", 1, 1, 0, 0, 1L);
    listingRepo.saveListing(listing);
  }

  @AfterEach
  public void tearDown() {
    jdbcTemplate.execute("DELETE FROM listing_images");
    jdbcTemplate.execute("DELETE FROM listings");
  }

  @Test
  public void testSaveListingImage() throws SQLException {
    Blob imageBlob = jdbcTemplate.getDataSource().getConnection().createBlob();
    imageBlob.setBytes(1, new byte[] { 1, 2, 3 }); // Example binary data
    ListingImage listingImage = new ListingImage(imageBlob, listing.getUuid(), "image/jpeg");

    int rowsChanged = listingImageRepo.saveListingImage(listingImage);

    assertThat(rowsChanged).isEqualTo(1);
  }

  @Test
  public void testGetImagesByListingUuid() throws SQLException {
    Blob imageBlob1 = jdbcTemplate.getDataSource().getConnection().createBlob();
    imageBlob1.setBytes(1, new byte[] { 1, 2, 3 });
    ListingImage listingImage1 = new ListingImage(imageBlob1, listing.getUuid(), "image/jpeg");

    Blob imageBlob2 = jdbcTemplate.getDataSource().getConnection().createBlob();
    imageBlob2.setBytes(1, new byte[] { 4, 5, 6 });
    ListingImage listingImage2 = new ListingImage(imageBlob2, listing.getUuid(), "image/png");

    listingImageRepo.saveListingImage(listingImage1);
    listingImageRepo.saveListingImage(listingImage2);

    List<ListingImage> images = listingImageRepo.getImagesByListingUuid(listing.getUuid());

    assertThat(images).hasSize(2);
    assertThat(images).extracting("imageFormat").contains("image/jpeg", "image/png");
  }

  @Test
  public void testGetImagesByListingUuid_NoImages() {
    List<ListingImage> images = listingImageRepo.getImagesByListingUuid("non-existent-uuid");

    assertThat(images).isEmpty();
  }
}