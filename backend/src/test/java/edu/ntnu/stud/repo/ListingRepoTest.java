package edu.ntnu.stud.repo;

import edu.ntnu.stud.model.Listing;
import edu.ntnu.stud.model.ListingUpdate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ListingRepoTest {


  @Autowired
  private ListingRepo listingRepo;

  @Autowired
  private JdbcTemplate jdbcTemplate;

    @AfterEach
    public void tearDown() {
      jdbcTemplate.execute("TRUNCATE TABLE listings");
    }

    @Test
    public void testSaveListing() {
        Listing listing = new Listing("Test Listing", 100.0, "test listing description", 0, 0, 1388, 1L);

        int rowsChanged = listingRepo.saveListing(listing);

        assertThat(rowsChanged).isEqualTo(1);
    }


    @Test
    public void testFindById() {
      Listing listing = new Listing("Test Listing", 100.0, "test listing description", 0, 0, 1388, 1L);

      listingRepo.saveListing(listing);
      Listing foundListing = listingRepo.getListingByUuid(listing.getUuid());
      assertThat(foundListing).isNotNull();
      assertThat(foundListing.getName()).isEqualTo("Test Listing");
    }


    @Test
    public void testFindAll() {
        Listing listing1 = new Listing("Test Listing 1", 100.0, "This is the first test listing", 0, 0, 1388, 1L);
        Listing listing2 = new Listing("Test Listing 2", 200.0, "This is the second test listing", 0, 0, 1388, 1L);

        listingRepo.saveListing(listing1);
        listingRepo.saveListing(listing2);

        List<Listing> listings = listingRepo.getAllListings();

        assertThat(listings).hasSize(2);
    }

    @Test
    public void testDeleteListing() {
        Listing listing = new Listing("Test Listing 2", 100.0, "This is a test listing", 0, 0, 1388, 1L);

        listingRepo.saveListing(listing);
        listingRepo.deleteListingByUuid(listing.getUuid());

        Listing foundListing = listingRepo.getListingByUuid(listing.getUuid());

      assertThat(foundListing).isNull();
    }

  @Test
  public void testGetListingsPage() {
    Listing listing1 = new Listing("Test Listing 1", 100.0, "This is the first test listing", 0, 0, 1388, 1L);
    Listing listing2 = new Listing("Test Listing 2", 200.0, "This is the second test listing", 0, 0, 1388, 1L);

    listingRepo.saveListing(listing1);
    listingRepo.saveListing(listing2);

    Pageable pageable = PageRequest.of(0, 10);
    Page<Listing> listingsPage = listingRepo.getListingsPage(pageable);

    assertThat(listingsPage.getTotalElements()).isEqualTo(2);
  }

  @Test
  public void testGetListingsByUserIdPage() {
    Listing listing1 = new Listing("Test Listing 1", 100.0, "This is the first test listing", 0, 0, 1388, 1L);
    Listing listing2 = new Listing("Test Listing 2", 200.0, "This is the second test listing", 0, 0, 1388, 1L);

    listingRepo.saveListing(listing1);
    listingRepo.saveListing(listing2);

    Pageable pageable = PageRequest.of(0, 10);
    Page<Listing> listingsPage = listingRepo.getListingsByUserIdPage(1L, pageable);

    assertThat(listingsPage.getTotalElements()).isEqualTo(2);
  }

  @Test
  public void testUpdateListing() {
    Listing listing = new Listing("Test Listing", 100.0, "test listing description", 0, 0, 1388, 1L);
    listingRepo.saveListing(listing);

    ListingUpdate listingUpdate = new ListingUpdate();
    listingUpdate.setUuid(listing.getUuid());
    listingUpdate.setName("Updated Listing");
    listingUpdate.setPrice(150.0);
    listingUpdate.setDescription("updated description");
    listingUpdate.setCategory(0);
    listingUpdate.setSubcategory(0);
    listingUpdate.setPostalCode(1388);
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
    Listing listing1 = new Listing("Test Listing 1", 100.0, "This is the first test listing", 0, 0, 1388, 1L);
    Listing listing2 = new Listing("Test Listing 2", 200.0, "This is the second test listing", 0, 0, 1388, 1L);

    listingRepo.saveListing(listing1);
    listingRepo.saveListing(listing2);

    Pageable pageable = PageRequest.of(0, 10);
    Page<Listing> listingsPage = listingRepo.search("Test", null, null, 50.0, 250.0, pageable);

    assertThat(listingsPage.getTotalElements()).isEqualTo(2);
  }
}