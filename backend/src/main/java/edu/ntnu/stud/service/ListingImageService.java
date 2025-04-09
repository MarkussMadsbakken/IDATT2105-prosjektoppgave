package edu.ntnu.stud.service;

import edu.ntnu.stud.model.base.ListingImage;
import edu.ntnu.stud.repo.ListingImageRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Service class for managing listing images.
 */
@Service
public class ListingImageService {

  @Autowired
  private ListingImageRepo listingImageRepo;

  /**
   * Retrieves images by the listing UUID.
   *
   * @param listingUuid the UUID of the listing
   * @return a list of ListingImage objects
   */
  public List<ListingImage> getImagesByListingUuid(String listingUuid) {
    return listingImageRepo.getImagesByListingUuid(listingUuid);
  }

  /**
   * Saves a listing image.
   *
   * @param listingImage the ListingImage object to save
   * @return the number of rows affected
   */
  public int saveListingImage(ListingImage listingImage) {
    return listingImageRepo.saveListingImage(listingImage);
  }
}