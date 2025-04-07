package edu.ntnu.stud.service;

import edu.ntnu.stud.model.ListingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * This class handles the business logic for recommended listings.
 */
@Service
public class RecomendedService {
  @Autowired
  private ListingService listingService;
  @Autowired
  private JWTService jwtService;

  /**
   * Retrieves a paginated list of recommended listings for a user.
   *
   * @param page the page number to retrieve
   * @param size the number of listings per page
   * @param token the JWT token of the user
   * @return a paginated list of recommended listings
   */
  public Page<ListingResponse> getRecomendedListingsPage(
      int page,
      int size,
      String token) {
    long userId = jwtService.extractUserId(token);
    return listingService.getRecomendedListingsPage(page, size, userId);
  }
}
