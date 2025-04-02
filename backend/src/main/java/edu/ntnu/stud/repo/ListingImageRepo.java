package edu.ntnu.stud.repo;

import edu.ntnu.stud.dao.ListingImageDao;
import edu.ntnu.stud.model.ListingImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ListingImageRepo {

  @Autowired
  private ListingImageDao listingImageDao;

  public List<ListingImage> getImagesByListingUuid(String listingUuid) {
    return listingImageDao.findByListingUuid(listingUuid);
  }

  public int saveListingImage(ListingImage listingImage) {
    return listingImageDao.save(listingImage);
  }

  public int deleteListingImageByUuid(String uuid) {
    return listingImageDao.deleteByUuid(uuid);
  }
}