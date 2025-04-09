package edu.ntnu.stud.repo;

import edu.ntnu.stud.model.Listing;
import edu.ntnu.stud.model.ListingUpdate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * Repository class for managing Listing entities in the database.
 * This class provides methods to add, retrieve, update, and delete listings.
 */
@Repository
public class ListingRepo {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private final RowMapper<Listing> listingRowMapper = (rs, rowNum) -> {
    Listing listing = new Listing();
    listing.setUuid(rs.getString("uuid"));
    listing.setName(rs.getString("name"));
    listing.setPrice(rs.getDouble("price"));
    listing.setDescription(rs.getString("description"));
    listing.setCreatedAt(rs.getTimestamp("created_at"));
    listing.setUpdatedAt(rs.getTimestamp("updated_at"));
    listing.setCategory(rs.getInt("category"));
    listing.setSubcategory(rs.getInt("subcategory"));
    listing.setLongitude(rs.getDouble("longitude"));
    listing.setLatitude(rs.getDouble("latitude"));
    listing.setActive(rs.getBoolean("active"));
    listing.setDeleted(rs.getBoolean("deleted"));
    listing.setSold(rs.getBoolean("sold"));
    listing.setBuyerId(rs.getLong("buyer_id"));
    listing.setOwnerId(rs.getLong("owner_id"));
    return listing;
  };

  /**
   * Retrieves all listings from the database.
   *
   * @return a list of all listings
   */
  public List<Listing> getAllListings() {
    String sql = "SELECT * FROM listings";
    return jdbcTemplate.query(sql, listingRowMapper);
  }

  /**
   * Retrieves all active listings from the database.
   *
   * @return a list of all active listings
   */
  public List<Listing> getAllActiveListings() {
    String sql = "SELECT * FROM listings WHERE deleted = false AND active = true";
    return jdbcTemplate.query(sql, listingRowMapper);
  }

  /**
   * Retrieves a listing by its uuid.
   *
   * @param uuid the uuid of the listing to retrieve
   * @return the listing with the specified uuid
   */
  public Listing getListingByUuid(String uuid) {
    String sql = "SELECT * FROM listings WHERE uuid = ?";
    List<Listing> listings = jdbcTemplate.query(sql, listingRowMapper, uuid);
    return listings.isEmpty() ? null : listings.get(0);
  }

  /**
   * Retrieves a paginated list of listings from the database.
   *
   * @param pageable the pagination information, including page number, page size,
   *                 and sorting
   * @return a page of listings
   */
  public Page<Listing> getListingsPage(Pageable pageable) {
    int limit = pageable.getPageSize();
    long offset = pageable.getOffset();
    String sql = "SELECT * FROM listings WHERE deleted = false AND active = true LIMIT ? OFFSET ?";
    List<Listing> listings = jdbcTemplate.query(sql, listingRowMapper, limit, offset);

    // Calculate the total number of listings
    Integer totalResult = jdbcTemplate.queryForObject(
        "SELECT COUNT(*) FROM listings WHERE deleted = false AND active = true", Integer.class);
    int total = totalResult != null ? totalResult : 0;

    return new PageImpl<>(listings, pageable, total);
  }

  /**
   * Retrives a paginated list of listings owned by a specific user from the
   * database.
   *
   * @param userId   the ID of the user whose listings to retrieve
   * @param pageable the pagination information, including page number, page size,
   *                 and sorting
   * @return a page of listings owned by the specified user
   */
  public Page<Listing> getListingsByUserIdPage(long userId, Pageable pageable) {
    int limit = pageable.getPageSize();
    long offset = pageable.getOffset();
    String sql = "SELECT * FROM listings WHERE owner_id = ? AND deleted = false AND active = true "
        + "LIMIT ? OFFSET ?";
    List<Listing> listings = jdbcTemplate.query(sql, listingRowMapper, userId, limit, offset);
    int total = listings.size();
    return new PageImpl<>(listings, pageable, total);
  }

  /**
   * Retrieves a paginated list of archived listings owned by a specific user from
   * the database.
   *
   * @param userId   the ID of the user whose archived listings to retrieve
   * @param pageable the pagination information, including page number, page size,
   *                 and sorting
   * @return a page of archived listings owned by the specified user
   */
  public Page<Listing> getArchivedListingsByUserIdPage(long userId, Pageable pageable) {
    int limit = pageable.getPageSize();
    long offset = pageable.getOffset();
    String sql = "SELECT * FROM listings WHERE owner_id = ? AND deleted = false AND active = false "
        + "LIMIT ? OFFSET ?";
    List<Listing> listings = jdbcTemplate.query(sql, listingRowMapper, userId, limit, offset);
    int total = listings.size();
    return new PageImpl<>(listings, pageable, total);
  }

  /**
   * Saves a new listing to the database.
   *
   * @param listing the listing to save
   * @return the number of rows affected
   */
  public int saveListing(Listing listing) {
    String sql = "INSERT INTO listings "
        + "(uuid, name, price, description, category, subcategory, longitude, latitude, owner_id)"
        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    return jdbcTemplate.update(
        sql,
        listing.getUuid(),
        listing.getName(),
        listing.getPrice(),
        listing.getDescription(),
        listing.getCategory(),
        listing.getSubcategory(),
        listing.getLongitude(),
        listing.getLatitude(),
        listing.getOwnerId());
  }

  /**
   * Updates an existing listing in the database.
   *
   * @param listing the listing to update
   * @return the number of rows affected
   */
  public int updateListing(ListingUpdate listing) {
    String sql = "UPDATE listings SET name = ?, price = ?, description = ?, "
        + "category = ?, subcategory = ?, longitude = ?, latitude = ?, active = ?, "
        + "deleted = ?, sold = ?, buyer_id = ? WHERE uuid = ?";
    return jdbcTemplate.update(
        sql,
        listing.getName(),
        listing.getPrice(),
        listing.getDescription(),
        listing.getCategory(),
        listing.getSubcategory(),
        listing.getLongitude(),
        listing.getLatitude(),
        listing.isActive(),
        listing.isDeleted(),
        listing.isSold(),
        listing.getBuyerId(),
        listing.getUuid());
  }

  /**
   * Deletes a listing by its uuid.
   *
   * @param uuid the uuid of the listing to delete
   * @return the number of rows affected
   */
  public int deleteListingByUuid(String uuid) {
    String sql = "DELETE FROM listings WHERE uuid = ?";
    return jdbcTemplate.update(sql, uuid);
  }

  /**
   * Retrieves a paginated list of listings based on search criteria.
   *
   * @param query       the search query
   * @param category    the category to filter by
   * @param subCategory the subcategory to filter by
   * @param minPrice    the minimum price to filter by
   * @param maxPrice    the maximum price to filter by
   * @param pageable    the pagination information, including page number, page
   *                    size, and sorting
   * @return a page of listings matching the search criteria
   */
  public Page<Listing> search(
      String query,
      Integer category,
      Integer subCategory,
      double minPrice,
      double maxPrice,
      Pageable pageable) {
    int limit = pageable.getPageSize();
    long offset = pageable.getOffset();
    String sql = "SELECT * FROM listings WHERE deleted = false AND active = true AND "
        + "(LOWER(name) LIKE LOWER(?) OR LOWER(description) LIKE LOWER(?)) AND "
        + "(category = ? OR ? IS NULL) AND "
        + "(subcategory = ? OR ? IS NULL) AND "
        + "(price BETWEEN ? AND ?) "
        + "LIMIT ? OFFSET ?";
    List<Listing> listings = jdbcTemplate.query(
        sql,
        listingRowMapper,
        "%" + query + "%", "%" + query + "%", // search pattern
        category, category, // category check
        subCategory, subCategory, // subcategory check
        minPrice, maxPrice, // price range
        limit, offset);
    int total = listings.size();
    return new PageImpl<>(listings, pageable, total);
  }

  /**
   * Retrieves a paginated list of recommended listings for a user.
   *
   * @param userId   the ID of the user for whom to retrieve recommendations
   * @param pageable the pagination information, including page number, page size,
   *                 and sorting
   * @return a page of recommended listings for the specified user
   */
  public Page<Listing> getRecomendedListingsPage(long userId, Pageable pageable) {
    int limit = pageable.getPageSize();
    long offset = pageable.getOffset();
    String sql = "SELECT * FROM listings l WHERE l.deleted = false AND l.active = true AND "
        + "l.owner_id != ? AND "
        + "(l.category IN (SELECT ul.category FROM user_history uh "
        + "JOIN listings ul ON uh.listing_id = ul.uuid WHERE uh.user_id = ?) OR "
        + "l.subcategory IN (SELECT ul.subcategory FROM user_history uh "
        + "JOIN listings ul ON uh.listing_id = ul.uuid WHERE uh.user_id = ?)) "
        + "LIMIT ? OFFSET ?";
    List<Listing> listings = jdbcTemplate.query(
        sql, listingRowMapper, userId, userId, userId, limit, offset);
    int total = listings.size();
    return new PageImpl<>(listings, pageable, total);
  }

  /**
   * Retrives a list of listings from a list of ids.
   *
   * @param ids the list of ids of the listings to retrieve
   * @return a list of listings with the specified ids
   */
  public List<Listing> getListingsByUuids(List<String> ids) {
    String placeholders = String.join(",", ids.stream().map(id -> "?").toArray(String[]::new));
    String sql = "SELECT * FROM listings WHERE uuid IN (" + placeholders + ")";
    return jdbcTemplate.query(sql, listingRowMapper, ids.toArray());
  }
}