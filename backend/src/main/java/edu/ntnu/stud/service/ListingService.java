package edu.ntnu.stud.service;

import static edu.ntnu.stud.util.ImageUtil.convertBlobToBase64;
import static edu.ntnu.stud.util.ImageUtil.convertMultipartFileToBlob;

import edu.ntnu.stud.model.Listing;
import edu.ntnu.stud.model.ListingImage;
import edu.ntnu.stud.model.ListingImageResponse;
import edu.ntnu.stud.model.ListingRequest;
import edu.ntnu.stud.model.ListingResponse;
import edu.ntnu.stud.model.ListingUpdate;
import edu.ntnu.stud.repo.ListingRepo;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
   * @param end   the ending index of the range (inclusive)
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
    listingRepo.saveListing(listing);
    return convertToResponse(listing);
  }

  /**
   * Updates an existing listing in the database.
   *
   * @param listing the listing to update
   * @param token   the JWT token of the user making the request
   * @return the number of rows affected
   */
  public int updateListing(ListingUpdate listing, String token) {
    long ownerId = jwtService.extractUserId(token.substring(7));
    Listing existingListing = listingRepo.getListingByUuid(listing.getUuid());
    if (existingListing == null) {
      throw new IllegalArgumentException("Listing not found with UUID: " + listing.getUuid());
    }
    if (existingListing.getOwnerId() != ownerId) {
      throw new IllegalArgumentException(
        "User does not own the listing with UUID: " + listing.getUuid());
    }
    return listingRepo.updateListing(listing);
  }

  /**
   * Deletes a listing.
   *
   * @param uuid the uuid of the listing to delete
   * @param token the JWT token of the user making the request
   */
  public void deleteListing(String uuid, String token) {
    long ownerId = jwtService.extractUserId(token.substring(7));
    Listing existingListing = listingRepo.getListingByUuid(uuid);
    if (existingListing == null) {
      throw new IllegalArgumentException("Listing not found with UUID: " + uuid);
    }
    if (existingListing.getOwnerId() != ownerId) {
      throw new IllegalArgumentException(
        "User does not own the listing with UUID: " + uuid);
    }
    ListingUpdate listingUpdate = convertToListingUpdate(existingListing);
    listingUpdate.setDeleted(true);
    listingRepo.updateListing(listingUpdate);
  }

  /**
   * Retrieves a paginated list of listings from the database.
   *
   * @param pageable the pagination information, including page number, page size,
   *                 and sorting
   * @return a page of listings
   */
  public Page<ListingResponse> getListingsPage(Pageable pageable) {
    Page<Listing> listingsPage = listingRepo.getListingsPage(pageable);
    return listingsPage.map(this::convertToResponse);
  }

  /**
   * Retrieves a paginated list of listings owned by a specific user.
   *
   * @param userId  the ID of the user whose listings to retrieve
   * @param pageable the pagination information, including page number, page size,
   *                 and sorting
   * @return a page of listings owned by the specified user
   */
  public Page<ListingResponse> getListingsByUserIdPage(long userId, Pageable pageable) {
    Page<Listing> listingsPage = listingRepo.getListingsByUserIdPage(userId, pageable);
    return listingsPage.map(this::convertToResponse);
  }

  /**
   * Converts a ListingRequest object to a Listing entity.
   *
   * @param listingRequest the ListingRequest object to convert
   * @return the converted Listing entity
   */
  private Listing convertToListing(ListingRequest listingRequest) {
    Listing listing = new Listing();
    listing.setName(listingRequest.getName());
    listing.setPrice(listingRequest.getPrice());
    listing.setDescription(listingRequest.getDescription());
    listing.setCategory(listingRequest.getCategory());
    listing.setSubcategory(listingRequest.getSubcategory());
    listing.setPostalCode(listingRequest.getPostalCode());
    listing.setActive(listingRequest.isActive());
    listing.setDeleted(listingRequest.isDeleted());
    listing.setSold(listingRequest.isSold());

    return listing;
  }

  /**
   * Converts a Listing entity to a ListingResponse object.
   *
   * @param listing the Listing entity to convert
   * @return the converted ListingResponse object
   */
  private ListingResponse convertToResponse(Listing listing) {
    ListingResponse response = new ListingResponse();
    response.setUuid(listing.getUuid());
    response.setName(listing.getName());
    response.setPrice(listing.getPrice());
    response.setDescription(listing.getDescription());
    response.setCategory(listing.getCategory());
    response.setSubcategory(listing.getSubcategory());
    response.setPostalCode(listing.getPostalCode());
    response.setActive(listing.isActive());
    response.setDeleted(listing.isDeleted());
    response.setSold(listing.isSold());
    response.setOwnerId(listing.getOwnerId());
    response.setCreatedAt(listing.getCreatedAt());
    response.setUpdatedAt(listing.getUpdatedAt());
    return response;
  }

  /**
   * Converts a listing to a listing update object.
   *
   * @param listing the listing to convert
   * @return the converted listing update object
   */
  private ListingUpdate convertToListingUpdate(Listing listing) {
    ListingUpdate listingUpdate = new ListingUpdate();
    listingUpdate.setUuid(listing.getUuid());
    listingUpdate.setName(listing.getName());
    listingUpdate.setPrice(listing.getPrice());
    listingUpdate.setDescription(listing.getDescription());
    listingUpdate.setCategory(listing.getCategory());
    listingUpdate.setSubcategory(listing.getSubcategory());
    listingUpdate.setPostalCode(listing.getPostalCode());
    listingUpdate.setActive(listing.isActive());
    listingUpdate.setDeleted(listing.isDeleted());
    listingUpdate.setSold(listing.isSold());

    return listingUpdate;
  }

  /**
   * Saves the images associated with a listing.
   *
   * @param images      the images for the listing
   * @param listingUuid the UUID of the listing to associate the images with
   */
  public void saveListingImages(List<MultipartFile> images, String listingUuid) {
    images.forEach(image -> {
      ListingImage listingImage = new ListingImage();
      listingImage.setListingUuid(listingUuid);
      listingImage.setImageFormat(image.getContentType());
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

  /**
   * Retrieves a list of ListingImageResponse objects by the listing UUID.
   * This method fetches the ListingImage entities associated with the given UUID,
   * converts each image to a Base64 encoded string along with its file type,
   * and returns a list of ListingImageResponse objects.
   *
   * @param uuid the UUID of the listing to retrieve images for
   * @return a list of ListingImageResponse objects
   *         containing the Base64 encoded images and their file types
   */
  public List<ListingImageResponse> getImagesByUuid(String uuid) {
    List<ListingImage> images = listingImageService.getImagesByListingUuid(uuid);

    return images.stream()
        .map(image -> {
          try {
            String base64Image = convertBlobToBase64(image.getImageBlob());
            String fileType = image.getImageFormat();
            return new ListingImageResponse(base64Image, fileType);
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        })
        .collect(Collectors.toList());
  }
}