package edu.ntnu.stud.dao;

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
    listing.setCreatedAt(rs.getTimestamp("created_at"));
    listing.setUpdatedAt(rs.getTimestamp("updated_at"));
    // TODO: Handle subcategories List<String>
    listing.setCategory(rs.getInt("category"));
    listing.setSubcategory(rs.getInt("subcategory"));
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
    String sql = "INSERT INTO listings "
        + "(uuid, name, price, description, category, subcategory, postal_code, owner_id)"
        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    return jdbcTemplate.update(
        sql,
        listing.getUuid(),
        listing.getName(),
        listing.getPrice(),
        listing.getDescription(),
        listing.getCategory(),
        listing.getSubcategory(),
        listing.getPostalCode(),
        listing.getOwnerId());
  }

  /**
   * Updates an existing listing in the database.
   *
   * @param listing the listing to update
   * @return the number of rows affected
   */
  public int update(ListingUpdate listing) {
    String sql = "UPDATE listings SET name = ?, price = ?, description = ?, "
        + "category = ?, subcategory = ?, postal_code = ?, active = ?, deleted = ?, sold = ? "
        + "WHERE uuid = ?";
    return jdbcTemplate.update(
      sql, 
      listing.getName(), 
      listing.getPrice(), 
      listing.getDescription(),
      listing.getCategory(), 
      listing.getSubcategory(),
      listing.getPostalCode(), 
      listing.isActive(), 
      listing.isDeleted(),
      listing.isSold(), 
      listing.getUuid());
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
    String sql = "SELECT * FROM listings WHERE deleted = false LIMIT ? OFFSET ?";
    List<Listing> listings = jdbcTemplate.query(sql, listingRowMapper, limit, offset);
    int total = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM listings", Integer.class);
    return new PageImpl<>(listings, pageable, total);
  }

  /**
   * Retrieves a paginated list of listings owned by a specific user from the database.
   *
   * @param userId the ID of the user whose listings to retrieve
   * @param pageable the pagination information, including page number, page size, and sorting
   * @return a page of listings owned by the specified user
   */
  public Page<Listing> findPageByOwnerId(long userId, Pageable pageable) {
    int limit = pageable.getPageSize();
    long offset = pageable.getOffset();
    String sql = "SELECT * FROM listings WHERE owner_id = ? AND deleted = false LIMIT ? OFFSET ?";
    List<Listing> listings = jdbcTemplate.query(sql, listingRowMapper, userId, limit, offset);
    int total = jdbcTemplate.queryForObject(
        "SELECT COUNT(*) FROM listings WHERE owner_id = ?", Integer.class, userId);
    return new PageImpl<>(listings, pageable, total);
  }
}