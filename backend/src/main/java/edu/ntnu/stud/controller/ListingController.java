package edu.ntnu.stud.controller;

import edu.ntnu.stud.model.ListingImageResponse;
import edu.ntnu.stud.model.ListingResponse;
import edu.ntnu.stud.model.ListingUpdate;
import edu.ntnu.stud.model.base.DefaultResponse;
import edu.ntnu.stud.model.request.ListingRequest;
import edu.ntnu.stud.service.ListingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller class for managing Listing entities.
 * This class provides endpoints to add, retrieve, and paginate listings.
 */
@Tag(name = "Listing", description = "Endpoints for managing listings")
@RestController
@RequestMapping("/api/listing")
public class ListingController {

  @Autowired
  private ListingService listingService;

  Logger logger = LoggerFactory.getLogger(ListingController.class);

  /**
   * Retrieves a listing by its UUID.
   *
   * @param uuid the UUID of the listing to retrieve
   * @return the listing with the specified UUID, or a 404 Not Found status if not
   *         found
   */
  @Operation(summary = "Retrieve a listing by UUID", 
      description = "Fetches a listing by its unique identifier.")
  @GetMapping("/{uuid}")
  public ResponseEntity<ListingResponse> getListingByUuid(@PathVariable String uuid) {
    logger.info("Fetching listing with UUID: {}", uuid);
    ListingResponse listingResponse = listingService.getListingByUuid(uuid);
    if (listingResponse != null) {
      logger.info("Listing found with UUID: {}", uuid);
      return ResponseEntity.ok(listingResponse);
    } else {
      logger.warn("Listing not found with UUID: {}", uuid);
      return ResponseEntity.noContent().build();
    }
  }

  /**
   * Retrieves the images of a listing by its UUID.
   *
   * @param uuid the UUID of the listing to retrieve images for
   * @return the images of the listing with the specified UUID,
   *         or a 404 Not Found status if not found
   */
  @Operation(summary = "Retrieve listing images by UUID", 
      description = "Fetches images associated with a listing by its unique identifier.")
  @GetMapping("/{uuid}/images")
  public ResponseEntity<List<ListingImageResponse>> getListingImagesByUuid(
      @PathVariable String uuid) {
    logger.info("Fetching listing with UUID: {}", uuid);
    List<ListingImageResponse> images = listingService.getImagesByUuid(uuid);
    if (images != null) {
      logger.info("Listing found with UUID: {}", uuid);
      return ResponseEntity.ok(images);
    } else {
      logger.warn("Listing not found with UUID: {}", uuid);
      return ResponseEntity.noContent().build();
    }
  }

  /**
   * Retrieves a paginated list of listings (not deleted, sold or archived).
   *
   * @param page   the page number to retrieve
   * @param offset the number of items per page
   * @return a page of listings
   */
  @Operation(summary = "Retrieve paginated listings", 
      description = "Fetches a paginated list of listings.")
  @GetMapping
  public ResponseEntity<Page<ListingResponse>> getListingsPage(
      @RequestParam int page,
      @RequestParam int offset) {
    logger.info("Fetching listings page: {}, offset: {}", page, offset);
    Page<ListingResponse> listingsPage = listingService.getListingsPage(page, offset);
    logger.info("Listings page fetched successfully");
    return ResponseEntity.ok(listingsPage);
  }

  /**
   * Retrieves all active listings (not deleted, sold or archived).
   *
   * @return a list of all active listings
   */
  @Operation(summary = "Retrieve all active listings", 
      description = "Fetches all active listings.")
  @GetMapping("/all")
  public ResponseEntity<List<ListingResponse>> getAllActiveListings() {
    logger.info("Fetching all listings");
    List<ListingResponse> listings = listingService.getAllActiveListings();
    logger.info("All listings fetched successfully");
    return ResponseEntity.ok(listings);
  }

  /**
   * Retrives a paginated of listing owned by a specific owner.
   *
   * @param userId the ID of the user whose listings to retrieve
   * @param page   the page number to retrieve
   * @param offset the number of items per page
   * @return a page of listings owned by the specified user
   */
  @Operation(summary = "Retrieve user listings", 
      description = "Fetches a paginated list of listings owned by a specific user.")
  @GetMapping("/user/{userId}")
  public ResponseEntity<Page<ListingResponse>> getListingsByUserId(
      @PathVariable long userId,
      @RequestParam int page,
      @RequestParam int offset) {
    logger.info(
        "Fetching listings for user with ID: {}, page: {}, offset: {}", userId, page, offset);
    Page<ListingResponse> listingsPage = 
        listingService.getListingsByUserIdPage(userId, page, offset);
    logger.info("Listings for user fetched successfully");
    return ResponseEntity.ok(listingsPage);
  }

  /**
   * Retrives a paginated list of archived listings owned by a specific owner.
   *
   * @param userId the ID of the user whose archived listings to retrieve
   * @param page   the page number to retrieve
   * @param offset the number of items per page
   * @return a page of archived listings owned by the specified user
   */
  @Operation(summary = "Retrieve archived user listings", 
      description = "Fetches a paginated list of archived listings owned by a specific user.")
  @GetMapping("/user/{userId}/archived")
  public ResponseEntity<Page<ListingResponse>> getArchivedListingsByUserId(
      @PathVariable long userId,
      @RequestParam int page,
      @RequestParam int offset) {
    logger.info(
        "Fetching archived listings for user with ID: {}, page: {}, offset: {}",
        userId,
        page,
        offset);
    Page<ListingResponse> listingsPage = 
        listingService.getArchivedListingsByUserIdPage(userId, page, offset);
    logger.info("Archived listings for user fetched successfully");
    return ResponseEntity.ok(listingsPage);
  }

  /**
   * Creates a new listing.
   *
   * @param listingRequest the ListingRequest to save as a Listing
   * @param token          the JWT token for authorization
   * @param images         the list of MultipartFile objects representing the
   *                       images
   * @return the created listings corresponding response object
   */
  @Operation(summary = "Create a new listing", description = "Creates a new listing with images.")
  @PostMapping
  public ResponseEntity<ListingResponse> createListing(
      @RequestPart("listingRequest") ListingRequest listingRequest,
      @RequestPart("images") List<MultipartFile> images,
      @RequestHeader("Authorization") String token) {
    logger.info("Creating new listing with name: {}", listingRequest.getName());
    ListingResponse listingResponse = listingService.saveListing(listingRequest, token);

    logger.info("Saving images for listing with name: {}", listingRequest.getName());
    logger.info("Listing created successfully with UUID: {}", listingResponse.getUuid());
    if (images == null || listingResponse.getUuid() == null) {
      logger.error("cant save listingimages");
    }
    listingService.saveListingImages(images, listingResponse.getUuid());
    logger.info("Images saved successfully for listing with UUID: {}", listingResponse.getUuid());
    return ResponseEntity.ok(listingResponse);
  }

  /**
   * Updates an existing listing.
   *
   * @param listingRequest the ListingRequest to update the Listing
   * @param token          the JWT token for authorization
   */
  @Operation(summary = "Update an existing listing", 
      description = "Updates an existing listing by its UUID.")
  @PutMapping
  public ResponseEntity<DefaultResponse> updateListing(
      @RequestBody ListingUpdate listingRequest,
      @RequestHeader("Authorization") String token) {
    logger.info("Updating listing with UUID: {}", listingRequest.getUuid());
    listingService.updateListing(listingRequest, token);
    logger.info("Listing updated successfully with UUID: {}", listingRequest.getUuid());
    return ResponseEntity.ok().body(
        new DefaultResponse("Listing updated successfully", "listingUpdated"));
  }

  /**
   * Deletes a listing by its UUID.
   *
   * @param uuid  the UUID of the listing to delete
   * @param token the JWT token for authorization
   */
  @Operation(summary = "Delete a listing", description = "Deletes a listing by its UUID.")
  @DeleteMapping("/{uuid}")
  public ResponseEntity<DefaultResponse> deleteListing(
      @PathVariable String uuid,
      @RequestHeader("Authorization") String token) {
    logger.info("Deleting listing with UUID: {}", uuid);
    listingService.deleteListing(uuid, token);
    logger.info("Listing deleted successfully with UUID: {}", uuid);
    return ResponseEntity.ok().body(
        new DefaultResponse("Listing deleted successfully", "listingDeleted"));
  }

  /**
   * Purcheses a listing by its UUID.
   *
   * @param uuid  the UUID of the listing to purchase
   * @param token the JWT token for authorization
   * @return a response indicating the success or failure of the purchase
   */
  @Operation(summary = "Purchase a listing", 
      description = "Marks a listing as purchased by its UUID.")
  @PostMapping("/{uuid}/purchase")
  public ResponseEntity<DefaultResponse> purchaseListing(
      @PathVariable String uuid,
      @RequestHeader("Authorization") String token) {
    logger.info("Purchasing listing with UUID: {}", uuid);
    listingService.purchaseListing(uuid, token);
    logger.info("Listing purchased successfully with UUID: {}", uuid);
    return ResponseEntity.ok().body(
        new DefaultResponse("Listing purchased successfully", "listingPurchased"));
  }

  /**
   * Archives a listing by its UUID.
   *
   * @param uuid  the UUID of the listing to archive
   * @param state the state to set for the listing
   * @param token the JWT token for authorization
   * @return a response indicating the success or failure of the archiving
   */
  @Operation(summary = "Archive a listing", 
      description = "Archives or unarchives a listing by its UUID.")
  @PostMapping("/{uuid}/archive")
  public ResponseEntity<DefaultResponse> archiveListing(
      @PathVariable String uuid,
      @RequestParam boolean state,
      @RequestHeader("Authorization") String token) {
    logger.info("Archiving listing with UUID: {}", uuid);
    listingService.archiveListing(uuid, state, token);
    logger.info("Listing aktive status set successfully with UUID: {}", uuid);
    return ResponseEntity.ok().body(
        new DefaultResponse("Listing archived successfully", "listingArchived"));
  }
}
