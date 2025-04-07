package edu.ntnu.stud.repo;


import edu.ntnu.stud.dao.ListingDao;
import edu.ntnu.stud.model.Listing;
import edu.ntnu.stud.model.ListingUpdate;
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
   * Retrieves a paginated list of listings from the database.
   *
   * @param pageable the pagination information, including page number, page size, and sorting
   * @return a page of listings
   */
  public Page<Listing> getListingsPage(Pageable pageable) {
    return listingDao.findPage(pageable);
  }

  /**
   * Retrives a paginated list of listings owned by a specific user from the database.
   *
   * @param userId the ID of the user whose listings to retrieve
   * @param pageable the pagination information, including page number, page size, and sorting
   * @return a page of listings owned by the specified user
   */
  public Page<Listing> getListingsByUserIdPage(long userId, Pageable pageable) {
    return listingDao.findPageByOwnerId(userId, pageable);
  }

  /**
   * Retrieves a paginated list of archived listings owned by a specific user from the database.
   *
   * @param userId the ID of the user whose archived listings to retrieve
   * @param pageable the pagination information, including page number, page size, and sorting
   * @return a page of archived listings owned by the specified user
   */
  public Page<Listing> getArchivedListingsByUserIdPage(long userId, Pageable pageable) {
    return listingDao.findArchivedPageByOwnerId(userId, pageable);
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
  public int updateListing(ListingUpdate listing) {
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

  /**
   * Retrieves a paginated list of listings based on search criteria.
   *
   * @param query the search query
   * @param category the category to filter by
   * @param subCategory the subcategory to filter by
   * @param minPrice the minimum price to filter by
   * @param maxPrice the maximum price to filter by
   * @param pageable the pagination information, including page number, page size, and sorting
   * @return a page of listings matching the search criteria
   */
  public Page<Listing> search(
      String query,
      Integer category,
      Integer subCategory,
      double minPrice,
      double maxPrice,
      Pageable pageable
  ) {
    return listingDao.search(query, category, subCategory, minPrice, maxPrice, pageable);
  }

  /**
   * Retrieves a paginated list of recommended listings for a user.
   *
   * @param userId the ID of the user for whom to retrieve recommendations
   * @param pageable the pagination information, including page number, page size, and sorting
   * @return a page of recommended listings for the specified user
   */
  public Page<Listing> getRecomendedListingsPage(long userId, Pageable pageable) {
    return listingDao.findRecomendedListingsPage(userId, pageable);
  }

  /**
   * Retrives a list of listings from a list of ids.
   *
   * @param ids the list of ids of the listings to retrieve
   * @return a list of listings with the specified ids 
   */
  public List<Listing> getListingsByUuids(List<String> ids) {
    return listingDao.findByIds(ids);
  }
}