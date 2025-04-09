package edu.ntnu.stud.controller;

import edu.ntnu.stud.model.ListingResponse;
import edu.ntnu.stud.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling search requests.
 */
@Tag(name = "Search", 
    description = "Endpoints for searching listings")
@RestController
@RequestMapping("/api/search")
public class SearchController {
  @Autowired
  private SearchService searchService;

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
   * @return a ResponseEntity containing the page of listings or no content if none found
   */
  @Operation(summary = "Search listings", 
      description = "Retrieves a page of listings based on the search criteria.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successfully retrieved listings"),
      @ApiResponse(responseCode = "204", description = "No content found")
  })
  @GetMapping
  public ResponseEntity<Page<ListingResponse>> searchPage(
      @RequestParam(defaultValue = "") String query,
      @RequestParam(required = false) Integer category,
      @RequestParam(required = false) Integer subCategory,
      @RequestParam(defaultValue = "0") double minPrice,
      @RequestParam(defaultValue = "100000000") double maxPrice,
      @RequestParam(defaultValue = "0") int page, 
      @RequestParam(defaultValue = "10") int size
  ) {
    Page<ListingResponse> listingResponsePage = 
        searchService.searchPage(query, category, subCategory, minPrice, maxPrice, page, size);
    if (listingResponsePage.hasContent()) {
      return ResponseEntity.ok(listingResponsePage);
    } else {
      return ResponseEntity.noContent().build();
    }
  }
}
