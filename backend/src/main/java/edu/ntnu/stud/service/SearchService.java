package edu.ntnu.stud.service;

import edu.ntnu.stud.model.response.ListingResponse;
import edu.ntnu.stud.util.Validate;
import org.slf4j.Logger;
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
  private ListingService listingService;

  Logger logger = org.slf4j.LoggerFactory.getLogger(SearchService.class);

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
    logger.info(
        "Search inputs - Query: {}, Category: {}, SubCategory: {}, MinPrice: {}, MaxPrice: {}"
        + ", Page: {}, Size: {}",
        query, category, subCategory, minPrice, maxPrice, page, size);
    Validate.that(page, Validate.isNotNegative(), "Page number must be non-negative");
    Validate.that(size, Validate.isPositive(), "Page size must be positive");
    Validate.that(minPrice, Validate.isNotNegative(), "Minimum price must be non-negative");
    Validate.that(maxPrice, Validate.isNotNegative(), "Maximum price must be non-negative");
    Validate.that(minPrice <= maxPrice, Validate.isTrue(),
        "Minimum price must be less than or equal to maximum price");
    
    // Create a Pageable object with the specified page and size
    Pageable pageable = PageRequest.of(page, size);

    // Call the listingService to perform the search and return the results
    return listingService.search(query, category, subCategory, minPrice, maxPrice, pageable);
  }
}
