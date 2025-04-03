package edu.ntnu.stud.service;

import edu.ntnu.stud.model.ListingImage;
import edu.ntnu.stud.repo.ListingImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListingImageService {

  @Autowired
  private ListingImageRepo listingImageRepo;

  public List<ListingImage> getImagesByListingUuid(String listingUuid) {
    return listingImageRepo.getImagesByListingUuid(listingUuid);
  }

  public int saveListingImage(ListingImage listingImage) {
    return listingImageRepo.saveListingImage(listingImage);
  }

  public int deleteListingImageByUuid(String uuid) {
    return listingImageRepo.deleteListingImageByUuid(uuid);
  }
}