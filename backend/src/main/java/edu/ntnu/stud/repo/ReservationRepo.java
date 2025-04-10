package edu.ntnu.stud.repo;

import edu.ntnu.stud.model.base.Reservation;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


/**
 * Repository class for managing reservations in the database.
 */
@Repository
public class ReservationRepo {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) -> {
    Reservation reservation = new Reservation();
    reservation.setId(rs.getLong("id"));
    reservation.setUserId(rs.getLong("user_id"));
    reservation.setListingId(rs.getString("listing_id"));
    reservation.setCreatedAt(rs.getTimestamp("reservation_date"));
    return reservation;
  };

  /**
   * Adds a new reservation to the database.
   *
   * @param reservation the Reservation object to be added
   */
  public void addReservation(Reservation reservation) {
    String query = "INSERT INTO reservations (id ,user_id, listing_id) VALUES (?, ?, ?)";
    jdbcTemplate.update(query,
        reservation.getId(), reservation.getUserId(), reservation.getListingId());
  }

  /**
   * Retrieves a reservation by its ID.
   *
   * @param id the ID of the reservation to retrieve
   * @return the latest Reservation object, or null if not found
   */
  public Reservation getReservationById(long id) {
    String query = "SELECT * FROM reservations WHERE id = ? ORDER BY reservation_date DESC";
    List<Reservation> reservations = jdbcTemplate.query(query, reservationRowMapper, id);
    return reservations.isEmpty() ? null : reservations.get(0);
  }

  /**
   * Retrieves a reservation by its listing ID.
   *
   * @param listingId      the ID of the listing to retrieve the reservation for
   * @param expirationDate the expiration date to check against
   * @return the latest Reservation object, or null if not found
   */
  public Reservation getReservationByListingId(String listingId, Timestamp expirationDate) {
    String query = "SELECT * FROM reservations "
        + "WHERE listing_id = ? AND reservation_date > ? "
        + "ORDER BY reservation_date DESC";
    List<Reservation> reservations = jdbcTemplate.query(
        query, reservationRowMapper, listingId, expirationDate);
    return reservations.isEmpty() ? null : reservations.get(0);
  }

  /**
   * Retrieves a reservation by its user ID.
   *
   * @param userId         the ID of the user to retrieve the reservation for
   * @param expirationDate the expiration date to check against
   * @return the Reservation object, or null if not found
   */
  public List<Reservation> getReservationsByUserId(long userId, Timestamp expirationDate) {
    String query = "SELECT * FROM reservations WHERE user_id = ? AND reservation_date > ?";
    return jdbcTemplate.query(query, reservationRowMapper, userId, expirationDate);
  }

  /**
   * Retrieves a reservation by its user ID and listing ID.
   *
   * @param userId         the ID of the user to retrieve the reservation for
   * @param listingId      the ID of the listing to retrieve the reservation for
   * @param expirationDate the expiration date to check against
   * @return the latest Reservation object, or null if not found
   */
  public Reservation getReservationByUserIdAndListingId(
      long userId, String listingId, Timestamp expirationDate) {
    String query = 
        "SELECT * FROM reservations WHERE user_id = ? AND listing_id = ? AND reservation_date > ? "
        + "ORDER BY reservation_date DESC";
    List<Reservation> reservations = jdbcTemplate.query(
        query, reservationRowMapper, userId, listingId, expirationDate);
    return reservations.isEmpty() ? null : reservations.get(0);
  }

  /**
   * Deletes a reservation by its ID.
   *
   * @param id the ID of the reservation to delete
   */
  public void deleteReservation(long id) {
    String query = "DELETE FROM reservations WHERE id = ?";
    jdbcTemplate.update(query, id);
  }
}
