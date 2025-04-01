package edu.ntnu.stud.service;

import edu.ntnu.stud.model.Listing;
import edu.ntnu.stud.repo.ListingRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



/**
* Service class for managing Listing entities.
* This class provides methods to add, retrieve, update, and delete listings.
*/
@Service
public class ListingService {

  @Autowired
  private ListingRepo listingRepo;

  /**
   * Retrieves all listings from the database.
   *
   * @return a list of all listings
   */
  public List<Listing> getAllListings() {
    return listingRepo.getAllListings();
  }

  /**
   * Retrieves a listing by its uuid.
   *
   * @param uuid the uuid of the listing to retrieve
   * @return the listing with the specified uuid
   */
  public Listing getListingByUuid(String uuid) {
    return listingRepo.getListingByUuid(uuid);
  }

  /**
   * Retrieves listings from the database within a specified range.
   *
   * @param start the starting index of the range (inclusive)
   * @param end the ending index of the range (inclusive)
   * @return a list of listings within the specified range
   */
  public List<Listing> getListingsInRange(int start, int end) {
    return listingRepo.getListingsInRange(start, end);
  }

  /**
   * Saves a new listing to the database.
   *
   * @param listing the listing to save
   * @return the number of rows affected
   */
  public int saveListing(Listing listing) {
    return listingRepo.saveListing(listing);
  }

  /**
   * Updates an existing listing in the database.
   *
   * @param listing the listing to update
   * @return the number of rows affected
   */
  public int updateListing(Listing listing) {
    return listingRepo.updateListing(listing);
  }

  /**
   * Retrieves a paginated list of listings from the database.
   *
   * @param pageable the pagination information, including page number, page size, and sorting
   * @return a page of listings
   */
  public Page<Listing> getListingsPage(Pageable pageable) {
    return listingRepo.getListingsPage(pageable);
  }
}