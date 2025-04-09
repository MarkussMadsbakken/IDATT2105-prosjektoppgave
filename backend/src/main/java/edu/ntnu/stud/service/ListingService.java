package edu.ntnu.stud.service;

import static edu.ntnu.stud.util.ImageUtil.convertBlobToBase64;
import static edu.ntnu.stud.util.ImageUtil.convertMultipartFileToBlob;

import edu.ntnu.stud.model.base.Listing;
import edu.ntnu.stud.model.base.ListingImage;
import edu.ntnu.stud.model.base.Notification;
import edu.ntnu.stud.model.request.ListingRequest;
import edu.ntnu.stud.model.response.ListingImageResponse;
import edu.ntnu.stud.model.response.ListingResponse;
import edu.ntnu.stud.model.update.ListingUpdate;
import edu.ntnu.stud.repo.ListingRepo;
import edu.ntnu.stud.util.Validate;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
  @Autowired
  private NotificationService notificationService;

  /**
   * Retrieves all listings from the database.
   *
   * @return a list of all listings
   */
  public List<Listing> getAllListings() {
    return listingRepo.getAllListings();
  }

  /**
   * Retrieves all active listings from the database.
   *
   * @return a list of all active listings
   */
  public List<ListingResponse> getAllActiveListings() {
    return listingRepo.getAllActiveListings()
        .stream()
        .map(this::convertToResponse)
        .toList();
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
   * Validates a listing request.
   *
   * @param listingRequest the listingRequest to validate
   */
  public void validateListingRequest(ListingRequest listingRequest) {
    Validate.that(listingRequest.getName(), Validate.isNotEmpty(), "Name must not be empty");
    Validate.that(listingRequest.getPrice(), Validate.isNotNegative(), "Price must be positive");
    Validate.that(listingRequest.getDescription(), Validate.isNotEmpty(), 
        "Description must not be empty");
    Validate.that(listingRequest.getCategory(), Validate.isPositive(), "Category must be positive");
    Validate.that(listingRequest.getLongitude(), Validate.isNotNegative(),
        "Longitude must be positive");
    Validate.that(listingRequest.getLatitude(), Validate.isNotNegative(), 
        "Latitude must be positive");
  }

  /**
   * Saves a new listing to the database.
   *
   * @param listingRequest the listingRequest to save as listing
   * @return the ListingResponse of the listing
   */
  public ListingResponse saveListing(ListingRequest listingRequest, String token) {
    validateListingRequest(listingRequest);
    long ownerId = jwtService.extractUserId(token.substring(7));
    Listing listing = convertToListing(listingRequest);
    listing.setOwnerId(ownerId);
    listingRepo.saveListing(listing);
    return convertToResponse(listing);
  }

  /**
   * Validates a listing update request.
   *
   * @param listingUpdate the listingUpdate to validate
   * @param token         the JWT token of the user making the request
   */
  public void validateListingUpdate(ListingUpdate listingUpdate, String token) {
    long ownerId = jwtService.extractUserId(token.substring(7));
    Listing existingListing = listingRepo.getListingByUuid(listingUpdate.getUuid());
    Validate.that(existingListing, Validate.isNotNull(), 
        "Listing not found with UUID: " + listingUpdate.getUuid());
    Validate.that((ownerId == existingListing.getOwnerId()), Validate.isTrue(), 
        "User does not own the listing with UUID: " + listingUpdate.getUuid());
    Validate.that((!existingListing.isSold() && !listingUpdate.isSold()), Validate.isTrue(),
        "Cannot update a listing that has already been sold.");
    Validate.that((listingUpdate.getBuyerId() == null), Validate.isTrue(),
        "Cannot set buyerId when updating a listing.");
    Validate.that(listingUpdate.getName(), Validate.isNotEmpty(), "Name must not be empty");
    Validate.that(listingUpdate.getPrice(), Validate.isNotNegative(), "Price must be positive");
    Validate.that(listingUpdate.getDescription(), Validate.isNotEmpty(),
        "Description must not be empty");
    Validate.that(listingUpdate.getCategory(), Validate.isPositive(),
        "Category must be positive");
    Validate.that(listingUpdate.getLongitude(), Validate.isNotNegative(), 
        "Longitude must be positive");
    Validate.that(listingUpdate.getLatitude(), Validate.isNotNegative(), 
        "Latitude must be positive");
  }

  /**
   * Updates an existing listing in the database.
   *
   * @param listing the listing to update
   * @param token   the JWT token of the user making the request
   * @return the number of rows affected
   */
  public int updateListing(ListingUpdate listing, String token) {
    validateListingUpdate(listing, token);
    return listingRepo.updateListing(listing);
  }



  /**
   * Deletes a listing.
   *
   * @param uuid  the uuid of the listing to delete
   * @param token the JWT token of the user making the request
   */
  public void deleteListing(String uuid, String token) {
    long ownerId = jwtService.extractUserId(token.substring(7));
    boolean isAdmin = jwtService.extractIsAdmin(token.substring(7));
    Listing existingListing = listingRepo.getListingByUuid(uuid);
    Validate.that(existingListing, Validate.isNotNull(), "Listing not found with UUID: " + uuid);
    Validate.that((ownerId == existingListing.getOwnerId() || isAdmin), Validate.isTrue(),
        "User does not own the listing with UUID: " + uuid);
    ListingUpdate listingUpdate = convertToListingUpdate(existingListing);
    listingUpdate.setDeleted(true);
    listingRepo.updateListing(listingUpdate);
  }

  /**
   * Retrieves a paginated list of listings from the database.
   *
   * @param page the page number to retrieve
   * @param size the number of listings per page
   * @return a page of listings
   */
  public Page<ListingResponse> getListingsPage(int page, int size) {
    Validate.that(page, Validate.isNotNegative(), "Page must be positive");
    Validate.that(size, Validate.isPositive(), "Size must be positive");
    Pageable pageable = Pageable.ofSize(size).withPage(page);
    Page<Listing> listingsPage = listingRepo.getListingsPage(pageable);
    return listingsPage.map(this::convertToResponse);
  }

  /**
   * Retrieves a paginated list of listings owned by a specific user.
   *
   * @param userId   the ID of the user whose listings to retrieve
   * @param page    the page number to retrieve
   * @param size    the number of listings per page
   * @return a page of listings owned by the specified user
   */
  public Page<ListingResponse> getListingsByUserIdPage(long userId, int page, int size) {
    Validate.that(page, Validate.isNotNegative(), "Page must be positive");
    Validate.that(size, Validate.isPositive(), "Size must be positive");
    Pageable pageable = PageRequest.of(page, size);
    Page<Listing> listingsPage = listingRepo.getListingsByUserIdPage(userId, pageable);
    return listingsPage.map(this::convertToResponse);
  }

  /**
   * Retrieves a paginated list of archived listings owned by a specific user.
   *
   * @param userId   the ID of the user whose archived listings to retrieve
   * @param page    the page number to retrieve
   * @param offset  the number of listings per page
   * @return a page of archived listings owned by the specified user
   */
  public Page<ListingResponse> getArchivedListingsByUserIdPage(long userId, int page, int offset) {
    Validate.that(page, Validate.isNotNegative(), "Page must be positive");
    Validate.that(offset, Validate.isPositive(), "Size must be positive");
    Pageable pageable = PageRequest.of(page, offset);
    Page<Listing> listingsPage = listingRepo.getArchivedListingsByUserIdPage(userId, pageable);
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
    listing.setLongitude(listingRequest.getLongitude());
    listing.setLatitude(listingRequest.getLatitude());
    listing.setLatitude(listingRequest.getLatitude());
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
  public ListingResponse convertToResponse(Listing listing) {
    ListingResponse response = new ListingResponse();
    response.setUuid(listing.getUuid());
    response.setName(listing.getName());
    response.setPrice(listing.getPrice());
    response.setDescription(listing.getDescription());
    response.setCategory(listing.getCategory());
    response.setSubcategory(listing.getSubcategory());
    response.setLongitude(listing.getLongitude());
    response.setLatitude(listing.getLatitude());
    response.setActive(listing.isActive());
    response.setDeleted(listing.isDeleted());
    response.setSold(listing.isSold());
    response.setBuyerId(listing.getBuyerId());
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
    listingUpdate.setLongitude(listing.getLongitude());
    listingUpdate.setLatitude(listing.getLatitude());
    listingUpdate.setActive(listing.isActive());
    listingUpdate.setDeleted(listing.isDeleted());
    listingUpdate.setSold(listing.isSold());
    listingUpdate.setBuyerId(listing.getBuyerId());

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

  /**
   * Searches for listings based on the provided criteria.
   *
   * @param query       the search query to match against listing names or
   *                    descriptions
   * @param category    the category to filter listings by
   * @param subCategory the subcategory to filter listings by
   * @param minPrice    the minimum price of listings to include
   * @param maxPrice    the maximum price of listings to include
   * @param pageable    the pagination information, including page number, page
   *                    size, and sorting
   * @return a page of listings matching the search criteria
   */
  public Page<ListingResponse> search(
      String query,
      Integer category,
      Integer subCategory,
      Double minPrice,
      Double maxPrice,
      Pageable pageable) {
    Page<Listing> listingsPage = listingRepo.search(
        query, category, subCategory, minPrice, maxPrice, pageable);
    return listingsPage.map(this::convertToResponse);
  }

  /**
   * Retives a paginated list of recommended listings for a user.
   *
   * @param page   the page number to retrieve
   * @param size   the number of listings per page
   * @param userId the ID of the user to retrieve recommendations for
   * @return a paginated list of recommended listings
   */
  public Page<ListingResponse> getRecomendedListingsPage(int page, int size, long userId) {
    Validate.that(page, Validate.isNotNegative(), "Page must be positive");
    Validate.that(size, Validate.isPositive(), "Size must be positive");
    Pageable pageable = Pageable.ofSize(size).withPage(page);
    Page<Listing> listingsPage = listingRepo.getRecomendedListingsPage(userId, pageable);
    return listingsPage.map(this::convertToResponse);
  }

  /**
   * Purchases a listing by its UUID.
   *
   * @param uuid  the UUID of the listing to purchase
   * @param token the JWT token of the user making the purchase
   */
  public void purchaseListing(String uuid, String token) {
    // Fetch values
    Listing listing = listingRepo.getListingByUuid(uuid);
    Validate.that(listing, Validate.isNotNull(), "Listing not found with UUID: " + uuid);
    Validate.that(listing.isSold(), Validate.isFalse(), "Listing already sold: " + uuid);
    long userId = jwtService.extractUserId(token.substring(7));

    // Create and do the listing update
    listing.setSold(true);
    listing.setBuyerId(userId);
    listingRepo.updateListing(convertToListingUpdate(listing));

    // Notify the owner of the listing about the purchase
    Notification notification = new Notification();
    notification.setUserId(listing.getOwnerId());
    notification.setMessage("userPurchasedYourListing");
    notification.setLink("/listing/" + uuid);
    notificationService.addNotification(notification);
  }

  /**
   * Archives a listing by its UUID.
   *
   * @param uuid  the UUID of the listing to archive
   * @param state the state to set for the listing (active or inactive)
   * @param token the JWT token of the user making the request
   */
  public void archiveListing(String uuid, boolean state, String token) {
    // Fetch values
    Listing listing = listingRepo.getListingByUuid(uuid);
    Validate.that(listing, Validate.isNotNull(), "Listing not found with UUID: " + uuid);
    long userId = jwtService.extractUserId(token.substring(7));
    Validate.that(listing.getOwnerId() == userId, Validate.isTrue(),
        "User does not own the listing with UUID: " + uuid);

    // Create and do the listing update
    listing.setActive(state);
    listingRepo.updateListing(convertToListingUpdate(listing));
  }

  /**
   * Retrieves a list of listings by their UUIDs.
   */
  public List<ListingResponse> getListingsByUuids(List<String> uuids) {
    Validate.that(uuids, Validate.isNotEmptyCollection(), "UUIDs must not be empty");
    List<Listing> listings = listingRepo.getListingsByUuids(uuids);
    return listings.stream()
        .map(this::convertToResponse)
        .collect(Collectors.toList());
  }
}