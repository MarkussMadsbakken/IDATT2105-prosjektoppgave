package edu.ntnu.stud.controller;

import edu.ntnu.stud.model.Listing;
import edu.ntnu.stud.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



/**
 * Controller class for managing Listing entities.
 * This class provides endpoints to add, retrieve, and paginate listings.
 */
@RestController
@RequestMapping("/api/listing")
public class ListingController {

  @Autowired
  private ListingService listingService;

  /**
   * Retrieves a listing by its UUID.
   *
   * @param uuid the UUID of the listing to retrieve
   * @return the listing with the specified UUID, or a 404 Not Found status if not found
   */
  @GetMapping("/{uuid}")
  public ResponseEntity<Listing> getListingByUuid(@PathVariable String uuid) {
    Listing listing = listingService.getListingByUuid(uuid);
    if (listing != null) {
      return ResponseEntity.ok(listing);
    } else {
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
  public ResponseEntity<Page<Listing>> getListingsPage(
      @RequestParam int page,
      @RequestParam int offset) {
    Pageable pageable = PageRequest.of(page, offset);
    Page<Listing> listingsPage = listingService.getListingsPage(pageable);
    return ResponseEntity.ok(listingsPage);
  }

  /**
   * Creates a new listing.
   *
   * @param listing the listing to create
   * @return the created listing, or a 500 Internal Server Error status if the creation failed
   */
  @PostMapping
  public ResponseEntity<Listing> createListing(@RequestBody Listing listing) {
    int rowsAffected = listingService.saveListing(listing);
    if (rowsAffected > 0) {
      return ResponseEntity.ok(listing);
    } else {
      return ResponseEntity.status(500).build();
    }
  }
}
