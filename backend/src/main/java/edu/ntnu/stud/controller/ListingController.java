package edu.ntnu.stud.controller;

import edu.ntnu.stud.model.ListingRequest;
import edu.ntnu.stud.model.ListingResponse;
import edu.ntnu.stud.service.ListingService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
   * @return the listing with the specified UUID, or a 404 Not Found status if not found
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
   * Retrieves a paginated list of listings.
   *
   * @param page the page number to retrieve
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
   * Creates a new listing.
   *
   * @param listingRequest the ListingRequest to save as a Listing
   * @param token the JWT token for authorization
   * @param images the list of MultipartFile objects representing the pictures
   * @return the created listings corresponding response object
   */
  @PostMapping
  public ResponseEntity<ListingResponse> createListing(
      @RequestPart("listingRequest") ListingRequest listingRequest,
      @RequestPart("pictures") List<MultipartFile> images,
      @RequestHeader("Authorization") String token) {
    logger.info("Creating new listing with name: {}", listingRequest.getName());
    listingRequest.setPictures(images);
    ListingResponse listingResponse = listingService.saveListing(listingRequest, token);
    logger.info("Listing created successfully with UUID: {}", listingResponse.getUuid());
    return ResponseEntity.ok(listingResponse);
  }
}
