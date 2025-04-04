package edu.ntnu.stud.controller;

import edu.ntnu.stud.model.DefaultResponse;
import edu.ntnu.stud.model.ListingImageResponse;
import edu.ntnu.stud.model.ListingRequest;
import edu.ntnu.stud.model.ListingResponse;
import edu.ntnu.stud.model.ListingUpdate;
import edu.ntnu.stud.service.ListingService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
  @GetMapping("/{uuid}")
  public ResponseEntity<ListingResponse> getListingByUuid(@PathVariable String uuid) {
    logger.info("Fetching listing with UUID: {}", uuid);
    ListingResponse listingResponse = listingService.getListingByUuid(uuid);
    if (listingResponse != null) {
      logger.info("Listing found with UUID: {}", uuid);
      return ResponseEntity.ok(listingResponse);
    } else {
      logger.warn("Listing not found with UUID: {}", uuid);
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Retrieves the images of a listing by its UUID.
   *
   * @param uuid the UUID of the listing to retrieve images for
   * @return the images of the listing with the specified UUID,
   *         or a 404 Not Found status if not found
   */
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
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Retrieves a paginated list of listings.
   *
   * @param page   the page number to retrieve
   * @param offset the number of items per page
   * @return a page of listings
   */
  @GetMapping
  public ResponseEntity<Page<ListingResponse>> getListingsPage(
      @RequestParam int page,
      @RequestParam int offset) {
    logger.info("Fetching listings page: {}, offset: {}", page, offset);
    Pageable pageable = PageRequest.of(page, offset);
    Page<ListingResponse> listingsPage = listingService.getListingsPage(pageable);
    logger.info("Listings page fetched successfully");
    return ResponseEntity.ok(listingsPage);
  }

  /**
   * Retrives a paginated of listing owned by a specific owner.
   *
   * @param userId the ID of the user whose listings to retrieve
   * @param page   the page number to retrieve
   * @param offset the number of items per page
   * @return a page of listings owned by the specified user
   */
  @GetMapping("/user/{userId}")
  public ResponseEntity<Page<ListingResponse>> getListingsByUserId(
      @PathVariable long userId,
      @RequestParam int page,
      @RequestParam int offset) {
    logger.info(
        "Fetching listings for user with ID: {}, page: {}, offset: {}", userId, page, offset);
    Pageable pageable = PageRequest.of(page, offset);
    Page<ListingResponse> listingsPage = listingService.getListingsByUserIdPage(userId, pageable);
    logger.info("Listings for user fetched successfully");
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
  @PostMapping("/{uuid}")
  public ResponseEntity<DefaultResponse> updateListing(
      @RequestPart("listingRequest") ListingUpdate listingRequest,
      @RequestHeader("Authorization") String token) {
    logger.info("Updating listing with UUID: {}", listingRequest.getUuid());
    listingService.updateListing(listingRequest, token);
    logger.info("Listing updated successfully with UUID: {}", listingRequest.getUuid());
    return ResponseEntity.ok().body(new DefaultResponse("Listing updated successfully", "listingUpdated"));
  }

  /**
   * Deletes a listing by its UUID.
   *
   * @param uuid  the UUID of the listing to delete
   * @param token the JWT token for authorization
   */
  @DeleteMapping("/{uuid}")
  public ResponseEntity<DefaultResponse> deleteListing(
      @PathVariable String uuid,
      @RequestHeader("Authorization") String token) {
    logger.info("Deleting listing with UUID: {}", uuid);
    listingService.deleteListing(uuid, token);
    logger.info("Listing deleted successfully with UUID: {}", uuid);
    return ResponseEntity.ok().body(new DefaultResponse("Listing deleted successfully", "listingDeleted"));
  }
}
