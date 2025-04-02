package edu.ntnu.stud.service;

import edu.ntnu.stud.model.Listing;
import edu.ntnu.stud.model.ListingImage;
import edu.ntnu.stud.model.ListingRequest;
import edu.ntnu.stud.model.ListingResponse;
import edu.ntnu.stud.repo.ListingRepo;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;


/**
* Service class for managing Listing entities.
* This class provides methods to add, retrieve, update, and delete listings.
*/
@Service
public class ListingService {

  @Autowired
  private ListingRepo listingRepo;

  @Autowired
  private JWTService jwtService;

  @Autowired
  private ListingImageService listingImageService;

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
   * @return the listingResponse of the listing with the specified uuid
   */
  public ListingResponse getListingByUuid(String uuid) {
    return convertToResponse(listingRepo.getListingByUuid(uuid));
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
   * @param listingRequest the listingRequest to save as listing
   * @return the ListingResponse of the listing
   */
  public ListingResponse saveListing(ListingRequest listingRequest, String token) {
    long ownerId = jwtService.extractUserId(token.substring(7));
    Listing listing = convertToListing(listingRequest);
    listing.setOwnerId(ownerId);

    System.out.println("saving listing with ownerid: " + ownerId);

    listingRepo.saveListing(listing);

    System.out.println("saving listing images with listingId: " + listing.getUuid());

    saveListingImages(listingRequest,listing.getUuid());

    return convertToResponse(listing);
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
  public Page<ListingResponse> getListingsPage(Pageable pageable) {
    Page<Listing> listingsPage = listingRepo.getListingsPage(pageable);
    return listingsPage.map(this::convertToResponse);
  }

  private Listing convertToListing(ListingRequest listingRequest) {
    Listing listing = new Listing();
    listing.setName(listingRequest.getName());
    listing.setPrice(listingRequest.getPrice());
    listing.setDescription(listingRequest.getDescription());
    // TODO: fix setting pictures on listing from request
//    listing.setPictures(listingRequest.getPictures().stream()
//        .map(Base64::getDecoder)
//        .map(decoder -> decoder.decode(picture))
//        .collect(Collectors.toList()));
    listing.setCategory(listingRequest.getCategory());
    listing.setSubcategories(listingRequest.getSubcategories());
    listing.setPostalCode(listingRequest.getPostalCode());
    listing.setActive(listingRequest.isActive());
    listing.setDeleted(listingRequest.isDeleted());
    listing.setSold(listingRequest.isSold());

    return listing;
  }

  private ListingResponse convertToResponse(Listing listing) {
    ListingResponse response = new ListingResponse();
    response.setUuid(listing.getUuid());
    response.setName(listing.getName());
    response.setPrice(listing.getPrice());
    response.setDescription(listing.getDescription());
    //TODO: fix setting pictures on response from listing
    //    response.setPictures(listing.getPictures());
    response.setCategory(listing.getCategory());
    response.setSubcategories(listing.getSubcategories());
    response.setPostalCode(listing.getPostalCode());
    response.setActive(listing.isActive());
    response.setDeleted(listing.isDeleted());
    response.setSold(listing.isSold());

    // get images
    List<ListingImage> images = listingImageService.getImagesByListingUuid(listing.getUuid());
    List<String> base64Images = images.stream()
        .map(image -> {
          try {
            return convertBlobToBase64(image.getImageBlob());
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        })
        .collect(Collectors.toList());
    response.setPictures(base64Images);

    return response;
  }

  private String convertBlobToBase64(Blob blob) throws SQLException {
    byte[] bytes = blob.getBytes(1, (int) blob.length());
    return Base64.getEncoder().encodeToString(bytes);
  }

  private Blob convertMultipartFileToBlob(MultipartFile file) throws IOException, SQLException {
    byte[] fileBytes = file.getBytes();
    return new SerialBlob(fileBytes);
  }

  private void saveListingImages(ListingRequest listingRequest, String listingUuid) {
    listingRequest.getPictures().forEach(image -> {
      ListingImage listingImage = new ListingImage();
      listingImage.setListingUuid(listingUuid);
      try {
        listingImage.setImageBlob(convertMultipartFileToBlob(image));
      } catch (IOException e) {
        throw new RuntimeException(e);
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
      listingImageService.saveListingImage(listingImage);
    });
  }
}