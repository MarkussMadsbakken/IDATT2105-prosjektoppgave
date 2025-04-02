package edu.ntnu.stud.dao;

import edu.ntnu.stud.model.ListingImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ListingImageDao {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private final RowMapper<ListingImage> listingImageRowMapper = new RowMapper<ListingImage>() {
    @Override
    public ListingImage mapRow(ResultSet rs, int rowNum) throws SQLException {
      return new ListingImage(rs.getString("uuid"), rs.getBlob("image_blob"));
    }
  };

  public List<ListingImage> findByListingUuid(String listingUuid) {
    String sql = "SELECT * FROM listing_images WHERE listing_uuid = ?";
    return jdbcTemplate.query(sql, listingImageRowMapper, listingUuid);
  }

  public int save(ListingImage listingImage) {
    String sql = "INSERT INTO listing_images (uuid, listing_uuid, image_blob) VALUES (?, ?, ?)";
    return jdbcTemplate.update(sql, listingImage.getUuid(), listingImage.getListingUuid(), listingImage.getImageBlob());
  }

  public int deleteByUuid(String uuid) {
    String sql = "DELETE FROM listing_images WHERE uuid = ?";
    return jdbcTemplate.update(sql, uuid);
  }
}