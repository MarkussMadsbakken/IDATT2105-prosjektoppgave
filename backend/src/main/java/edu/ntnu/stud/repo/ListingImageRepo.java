package edu.ntnu.stud.repo;

import edu.ntnu.stud.model.ListingImage;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * Repository class for managing ListingImage entities in the database.
 * This class provides methods to add, retrieve and delete ListingImages
 */
@Repository
public class ListingImageRepo {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private final RowMapper<ListingImage> listingImageRowMapper = (rs, rowNum) -> {
    ListingImage listingImage = new ListingImage();
    listingImage.setUuid(rs.getString("uuid"));
    listingImage.setImageBlob(rs.getBlob("image_blob"));
    listingImage.setListingUuid(rs.getString("listing_uuid"));
    listingImage.setImageFormat(rs.getString("image_format"));

    return listingImage;
  };

  /**
   * Retrieves a list of ListingImage entities associated with a specific listing
   * UUID.
   *
   * @param listingUuid the UUID of the listing to retrieve images for
   * @return a list of ListingImage entities associated with the specified listing
   *         UUID
   */
  public List<ListingImage> getImagesByListingUuid(String listingUuid) {
    String sql = "SELECT * FROM listing_images WHERE listing_uuid = ?";
    return jdbcTemplate.query(sql, listingImageRowMapper, listingUuid);
  }

  /**
   * Saves a ListingImage entity to the database.
   *
   * @param listingImage the ListingImage entity to save
   * @return the number of rows affected by the save operation
   */
  public int saveListingImage(ListingImage listingImage) {
    String sql = "INSERT INTO listing_images "
        + "(uuid, listing_uuid, image_blob, image_format)"
        + " VALUES (?, ?, ?, ?)";
    return jdbcTemplate.update(
        sql,
        listingImage.getUuid(),
        listingImage.getListingUuid(),
        listingImage.getImageBlob(),
        listingImage.getImageFormat());
  }
}