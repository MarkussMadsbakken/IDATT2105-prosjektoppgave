package edu.ntnu.stud.repo;


import edu.ntnu.stud.dao.ListingDao;
import edu.ntnu.stud.model.Listing;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


/**
 * Repository class for managing Listing entities in the database.
 * This class provides methods to add, retrieve, update, and delete listings.
 */
@Repository
public class ListingRepo {

  @Autowired
  private ListingDao listingDao;

  /**
   * Retrieves all listings from the database.
   *
   * @return a list of all listings
   */
  public List<Listing> getAllListings() {
    return listingDao.findAll();
  }

  /**
   * Retrieves a listing by its uuid.
   *
   * @param uuid the uuid of the listing to retrieve
   * @return the listing with the specified uuid
   */
  public Listing getListingByUuid(String uuid) {
    return listingDao.findByUuid(uuid);
  }

  /**
   * Retrieves listings from the database within a specified range.
   *
   * @param start the starting index of the range (inclusive)
   * @param end the ending index of the range (inclusive)
   * @return a list of listings within the specified range
   */
  public List<Listing> getListingsInRange(int start, int end) {
    return listingDao.findInRange(start, end);
  }

  /**
   * Retrieves a paginated list of listings from the database.
   *
   * @param pageable the pagination information, including page number, page size, and sorting
   * @return a page of listings
   */
  public Page<Listing> getListingsPage(Pageable pageable) {
    return listingDao.findPage(pageable);
  }

  /**
   * Saves a new listing to the database.
   *
   * @param listing the listing to save
   * @return the number of rows affected
   */
  public int saveListing(Listing listing) {
    return listingDao.save(listing);
  }

  /**
   * Updates an existing listing in the database.
   *
   * @param listing the listing to update
   * @return the number of rows affected
   */
  public int updateListing(Listing listing) {
    return listingDao.update(listing);
  }

  /**
   * Deletes a listing by its uuid.
   *
   * @param uuid the uuid of the listing to delete
   * @return the number of rows affected
   */
  public int deleteListingByUuid(String uuid) {
    return listingDao.deleteByUuid(uuid);
  }
}