package edu.ntnu.stud.repo;

import static org.assertj.core.api.Assertions.assertThat;

import edu.ntnu.stud.model.base.Listing;
import edu.ntnu.stud.model.update.ListingUpdate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

/**
 * Test class for ListingRepo.
 */
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ListingRepoTest {

  @Autowired
  private ListingRepo listingRepo;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @BeforeEach
  public void tearDown() {
    jdbcTemplate.execute("DELETE FROM user_history");
    jdbcTemplate.execute("DELETE FROM listings");
  }

  @Test
  public void testSaveListing() {
    Listing listing = new Listing("Test Listing", 100.0,
        "test listing description", 0, 0, 0, 0, 1L);

    int rowsChanged = listingRepo.saveListing(listing);

    assertThat(rowsChanged).isEqualTo(1);
  }

  @Test
  public void testFindById() {
    Listing listing = new Listing("Test Listing", 100.0,
        "test listing description", 0, 0, 0, 0, 1L);

    listingRepo.saveListing(listing);
    Listing foundListing = listingRepo.getListingByUuid(listing.getUuid());
    assertThat(foundListing).isNotNull();
    assertThat(foundListing.getName()).isEqualTo("Test Listing");
  }

  @Test
  public void testFindAll() {
    Listing listing1 = new Listing("Test Listing 1", 100.0,
        "This is the first test listing", 0, 0, 0, 0, 1L);
    Listing listing2 = new Listing("Test Listing 2", 200.0,
        "This is the second test listing", 0, 0, 0, 0, 1L);

    listingRepo.saveListing(listing1);
    listingRepo.saveListing(listing2);

    List<Listing> listings = listingRepo.getAllListings();

    assertThat(listings).hasSize(2);
  }

  @Test
  public void testDeleteListing() {
    Listing listing = new Listing("Test Listing 2", 100.0,
        "This is a test listing", 0, 0, 0, 0, 1L);

    listingRepo.saveListing(listing);
    listingRepo.deleteListingByUuid(listing.getUuid());

    Listing foundListing = listingRepo.getListingByUuid(listing.getUuid());

    assertThat(foundListing).isNull();
  }

  @Test
  public void testGetListingsPage() {
    Listing listing1 = new Listing("Test Listing 1", 100.0,
        "This is the first test listing", 0, 0, 0, 0, 1L);
    Listing listing2 = new Listing("Test Listing 2", 200.0,
        "This is the second test listing", 0, 0, 0, 0, 1L);

    listingRepo.saveListing(listing1);
    listingRepo.saveListing(listing2);

    Pageable pageable = PageRequest.of(0, 10);
    Page<Listing> listingsPage = listingRepo.getListingsPage(pageable);

    assertThat(listingsPage.getTotalElements()).isEqualTo(2);
  }

  @Test
  public void testGetListingsByUserIdPage() {
    Listing listing1 = new Listing("Test Listing 1", 100.0,
        "This is the first test listing", 0, 0, 0, 0, 1L);
    Listing listing2 = new Listing("Test Listing 2", 200.0,
        "This is the second test listing", 0, 0, 0, 0, 1L);

    listingRepo.saveListing(listing1);
    listingRepo.saveListing(listing2);

    Pageable pageable = PageRequest.of(0, 10);
    Page<Listing> listingsPage = listingRepo.getListingsByUserIdPage(1L, pageable);

    assertThat(listingsPage.getTotalElements()).isEqualTo(2);
  }

  @Test
  public void testUpdateListing() {
    Listing listing = new Listing("Test Listing", 100.0,
        "test listing description", 0, 0, 0, 0, 1L);
    listingRepo.saveListing(listing);

    ListingUpdate listingUpdate = new ListingUpdate();
    listingUpdate.setUuid(listing.getUuid());
    listingUpdate.setName("Updated Listing");
    listingUpdate.setPrice(150.0);
    listingUpdate.setDescription("updated description");
    listingUpdate.setCategory(0);
    listingUpdate.setSubcategory(0);
    listingUpdate.setLongitude(0);
    listingUpdate.setLatitude(0);
    listingUpdate.setActive(true);
    listingUpdate.setDeleted(false);
    listingUpdate.setSold(false);

    int rowsChanged = listingRepo.updateListing(listingUpdate);

    assertThat(rowsChanged).isEqualTo(1);

    Listing updatedListing = listingRepo.getListingByUuid(listing.getUuid());
    assertThat(updatedListing.getName()).isEqualTo("Updated Listing");
    assertThat(updatedListing.getPrice()).isEqualTo(150.0);
  }

  @Test
  public void testSearch() {
    Listing listing1 = new Listing("Test Listing 1", 100.0,
        "This is the first test listing", 0, 0, 0, 0, 1L);
    Listing listing2 = new Listing("Test Listing 2", 200.0,
        "This is the second test listing", 0, 0, 0, 0, 1L);

    listingRepo.saveListing(listing1);
    listingRepo.saveListing(listing2);

    Pageable pageable = PageRequest.of(0, 10);
    Page<Listing> listingsPage = listingRepo.search("Test", null, null, 50.0, 250.0, pageable);

    assertThat(listingsPage.getTotalElements()).isEqualTo(2);
  }

  @Test
  public void testGetListingsByUuids() {
    Listing listing1 = new Listing("Test Listing 1", 100.0,
        "This is the first test listing", 0, 0, 0, 0, 1L);
    Listing listing2 = new Listing("Test Listing 2", 200.0,
        "This is the second test listing", 0, 0, 0, 0, 1L);

    listingRepo.saveListing(listing1);
    listingRepo.saveListing(listing2);

    List<String> uuids = List.of(listing1.getUuid(), listing2.getUuid());

    List<Listing> listings = listingRepo.getListingsByUuids(uuids);

    assertThat(listings.size()).isEqualTo(2);
  }

  @Test
  public void testGetAllActiveListings() {
    Listing listing1 = new Listing("Test Listing 1", 100.0,
        "This is the first test listing", 0, 0, 0, 0, 1L);
    Listing listing2 = new Listing("Test Listing 2", 200.0,
        "This is the second test listing", 0, 0, 0, 0, 1L);

    listingRepo.saveListing(listing1);
    listingRepo.saveListing(listing2);

    // have to update listing to save it as inactive
    ListingUpdate listingUpdate = new ListingUpdate();

    listingUpdate.setUuid(listing2.getUuid());
    listingUpdate.setName("Test listing 2 (inactive)");
    listingUpdate.setActive(false);
    listingRepo.updateListing(listingUpdate);

    List<Listing> activeListings = listingRepo.getAllActiveListings();

    assertThat(activeListings).hasSize(1);
    assertThat(activeListings.getFirst().getName()).isEqualTo("Test Listing 1");
  }

  @Test
  public void testGetArchivedListingsByUserIdPage() {
    Listing listing1 = new Listing("Test Listing 1", 100.0,
        "This is the first test listing", 0, 0, 0, 0, 1L);
    Listing listing2 = new Listing("Test Listing 2", 200.0,
        "This is the second test listing", 0, 0, 0, 0, 1L);

    listingRepo.saveListing(listing1);
    listingRepo.saveListing(listing2);

    // have to update listing to save it as inactive
    ListingUpdate listingUpdate = new ListingUpdate();

    listingUpdate.setUuid(listing2.getUuid());
    listingUpdate.setName("Test listing 2 (inactive)");
    listingUpdate.setActive(false);
    listingRepo.updateListing(listingUpdate);

    Pageable pageable = PageRequest.of(0, 10);
    Page<Listing> inActiveListingsPage = listingRepo.getArchivedListingsByUserIdPage(1L, pageable);

    assertThat(inActiveListingsPage).hasSize(1);
    assertThat(inActiveListingsPage.getContent().getFirst().getName())
        .isEqualTo("Test listing 2 (inactive)");
  }

  @Test
  public void testGetRecomendedListingsPage() {
    Listing listing1 = new Listing("Recommended Listing 1", 100.0,
        "This is a recommended listing", 1, 1, 0, 0, 2L); // Matches criteria
    Listing listing2 = new Listing("Recommended Listing 2", 200.0,
        "This is another recommended listing", 1, 2, 0, 0, 2L); // Matches criteria
    Listing listing3 = new Listing("Non-Recommended Listing", 300.0,
        "This listing does not match", 2, 3, 0, 0, 2L); // Does not match criteria
    Listing listing4 = new Listing("listing owned by user", 300.0,
        "This listing does not match", 1, 2, 0, 0, 1L); // Does not match (owned by user)

    listingRepo.saveListing(listing1);
    listingRepo.saveListing(listing2);
    listingRepo.saveListing(listing3);
    listingRepo.saveListing(listing4);

    jdbcTemplate.execute("INSERT INTO user_history (user_id, listing_id)" 
        + " VALUES (1, '" + listing1.getUuid() + "')");

    Pageable pageable = PageRequest.of(0, 10);
    Page<Listing> recommendedListingsPage = listingRepo.getRecomendedListingsPage(1L, pageable);

    assertThat(recommendedListingsPage.getTotalElements()).isEqualTo(2);
    assertThat(recommendedListingsPage.getContent())
        .extracting(Listing::getName)
        .containsExactlyInAnyOrder("Recommended Listing 1", "Recommended Listing 2");
  }

}