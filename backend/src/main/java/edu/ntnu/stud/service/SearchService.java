package edu.ntnu.stud.service;

import edu.ntnu.stud.model.Listing;
import edu.ntnu.stud.model.ListingResponse;
import edu.ntnu.stud.repo.ListingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service class for handling search functionality.
 */
@Service
public class SearchService {
  @Autowired
  private ListingRepo listingRepo;
  @Autowired
  private ListingService listingService;

  /**
   * Retrieves a page of listings based on the search criteria.
   *
   * @param query the search query
   * @param category the category to filter by
   * @param subCategory the subcategory to filter by
   * @param minPrice the minimum price to filter by
   * @param maxPrice the maximum price to filter by
   * @param page the page number to retrieve
   * @param size the number of listings per page
   * @return a ListingResponse object containing the listings and pagination information
   */
  public Page<ListingResponse> searchPage(
      String query,
      Integer category,
      Integer subCategory,
      double minPrice,
      double maxPrice,
      int page,
      int size
  ) {
    // Create a Pageable object with the specified page and size
    Pageable pageable = PageRequest.of(page, size);

    // Call the listingService to perform the search and return the results
    Page<Listing> listings = 
        listingRepo.search(query, category, subCategory, minPrice, maxPrice, pageable);

    // Convert and return the page of listings
    return listings.map(listingService::convertToResponse);
  }
}
