package edu.ntnu.stud.dao;

import edu.ntnu.stud.model.Listing;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * Data Access Object (DAO) for the Listing entity.
 * This class provides methods to perform CRUD operations on the Listing table in the database.
 */
@Repository
public class ListingDao {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private final RowMapper<Listing> listingRowMapper = (rs, rowNum) -> {
    Listing listing = new Listing();
    listing.setUuid(rs.getString("uuid"));
    listing.setName(rs.getString("name"));
    listing.setPrice(rs.getDouble("price"));
    listing.setDescription(rs.getString("description"));
    listing.setCreatedAt(rs.getString("created_at"));
    listing.setUpdatedAt(rs.getString("updated_at"));
    // TODO: Handle Blob and List<String> conversion as needed
    listing.setCategory(rs.getString("category"));
    listing.setPostalCode(rs.getInt("postal_code"));
    listing.setActive(rs.getBoolean("active"));
    listing.setDeleted(rs.getBoolean("deleted"));
    listing.setSold(rs.getBoolean("sold"));
    listing.setOwnerId(rs.getLong("owner_id"));
    return listing;
  };

  /**
   * Retrieves all listings from the database.
   *
   * @return a list of all listings
   */
  public List<Listing> findAll() {
    String sql = "SELECT * FROM listings";
    return jdbcTemplate.query(sql, listingRowMapper);
  }

  /**
 * Retrieves listings from the database within a specified range.
 *
 * @param start the starting index of the range (inclusive)
 * @param end the ending index of the range (inclusive)
 * @return a list of listings within the specified range
 */
  public List<Listing> findInRange(int start, int end) {
    String sql = "SELECT * FROM listings LIMIT ? OFFSET ?";
    return jdbcTemplate.query(sql, listingRowMapper, end - start + 1, start);
  }

  /**
   * Retrieves a listing by its uuid.
   *
   * @param uuid the uuid of the listing to retrieve
   * @return the listing with the specified uuid
   */
  public Listing findByUuid(String uuid) {
    String sql = "SELECT * FROM listings WHERE uuid = ?";
    return jdbcTemplate.queryForObject(sql, listingRowMapper, uuid);
  }

  /**
   * Saves a new listing to the database.
   *
   * @param listing the listing to save
   * @return the number of rows affected
   */
  public int save(Listing listing) {
    System.out.println(listing.getUuid());
    String sql = "INSERT INTO listings "
        + "(uuid, name, price, description, category, postal_code, owner_id)"
        + " VALUES (?, ?, ?, ?, ?, ?, ?)";
    return jdbcTemplate.update(
        sql,
        listing.getUuid(),
        listing.getName(),
        listing.getPrice(),
        listing.getDescription(),
        listing.getCategory(),
        listing.getPostalCode(),
        listing.getOwnerId());
  }

  /**
   * Updates an existing listing in the database.
   *
   * @param listing the listing to update
   * @return the number of rows affected
   */
  public int update(Listing listing) {
    String sql = "UPDATE listings SET name = ?, price = ?, description = ?, "
        + "category = ?, postal_code = ?, active = ?, deleted = ?, sold = ?, "
        + "owner_id = ? WHERE uuid = ?";
    return jdbcTemplate.update(sql, listing.getName(), listing.getPrice(), listing.getDescription(),
        listing.getCategory(), listing.getPostalCode(), listing.isActive(), listing.isDeleted(),
        listing.isSold(), listing.getOwnerId(), listing.getUuid());
  }

  /**
   * Deletes a listing by its uuid.
   *
   * @param uuid the uuid of the listing to delete
   * @return the number of rows affected
   */
  public int deleteByUuid(String uuid) {
    String sql = "DELETE FROM listings WHERE uuid = ?";
    return jdbcTemplate.update(sql, uuid);
  }

  /**
   * Retrieves a paginated list of listings from the database.
   *
   * @param pageable the pagination information, including page number, page size, and sorting
   * @return a page of listings
   */
  public Page<Listing> findPage(Pageable pageable) {
    int limit = pageable.getPageSize();
    long offset = pageable.getOffset();
    String sql = "SELECT * FROM listings LIMIT ? OFFSET ?";
    List<Listing> listings = jdbcTemplate.query(sql, listingRowMapper, limit, offset);
    int total = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM listings", Integer.class);
    return new PageImpl<>(listings, pageable, total);
  }
}