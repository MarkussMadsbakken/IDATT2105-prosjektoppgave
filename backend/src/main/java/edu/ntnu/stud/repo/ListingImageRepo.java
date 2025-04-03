package edu.ntnu.stud.repo;

import edu.ntnu.stud.dao.ListingImageDao;
import edu.ntnu.stud.model.ListingImage;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * Repository class for managing ListingImage entities in the database.
 * This class provides methods to add, retrieve and delete ListingImages
 */
@Repository
public class ListingImageRepo {

  @Autowired
  private ListingImageDao listingImageDao;

  /**
   * Retrieves a list of ListingImage entities associated with a specific listing UUID.
   *
   * @param listingUuid the UUID of the listing to retrieve images for
   * @return a list of ListingImage entities associated with the specified listing UUID
   */
  public List<ListingImage> getImagesByListingUuid(String listingUuid) {
    return listingImageDao.findByListingUuid(listingUuid);
  }

  /**
   * Saves a ListingImage entity to the database.
   *
   * @param listingImage the ListingImage entity to save
   * @return the number of rows affected by the save operation
   */
  public int saveListingImage(ListingImage listingImage) {
    return listingImageDao.save(listingImage);
  }

  /**
   * Deletes a ListingImage entity from the database by its UUID.
   *
   * @param uuid the UUID of the ListingImage entity to delete
   * @return the number of rows affected by the delete operation
   */
  public int deleteListingImageByUuid(String uuid) {
    return listingImageDao.deleteByUuid(uuid);
  }
}