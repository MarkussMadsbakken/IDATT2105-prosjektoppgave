package edu.ntnu.stud.controller;

import edu.ntnu.stud.model.ListingResponse;
import edu.ntnu.stud.service.RecomendedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class handles the API endpoints for recommended listings.
 */
@RestController
@RequestMapping("/api/recomended")
public class RecomendedController {
  @Autowired
  private RecomendedService recomendedService;

  /**
   * Retrives a paginated list of recommended listings for a user.
   *
   * @param page the page number to retrieve
   * @param size the number of listings per page
   * @param token the JWT token of the user
   * @return a paginated list of recommended listings
   */
  @GetMapping
  public ResponseEntity<Page<ListingResponse>> getRecomendedListingsPage(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestHeader("Authorization") String token) {
    Page<ListingResponse> recomendedListings = 
        recomendedService.getRecomendedListingsPage(page, size, token);
    if (recomendedListings.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(recomendedListings);
  }
}
