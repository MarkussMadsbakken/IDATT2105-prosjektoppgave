package edu.ntnu.stud.repo;

import static org.assertj.core.api.Assertions.assertThat;

import edu.ntnu.stud.model.base.Listing;
import edu.ntnu.stud.model.base.Reservation;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;


/**
 * Test class for ReservationRepo.
 */
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ReservationRepoTest {

  @Autowired
  private ReservationRepo reservationRepo;
  @Autowired
  private ListingRepo listingRepo;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private Reservation reservation;
  private Listing listing;

  /**
   * Sets up the test environment by clearing the reservations table and creating a test listing
   * and reservation.
   */
  @BeforeEach
  public void setUp() {
    listing = new Listing();
    listing.setName("Test Listing");
    listing.setDescription("This is a test listing.");
    listing.setPrice(100.0);
    listing.setOwnerId(2L);
    listingRepo.saveListing(listing);

    reservation = new Reservation();
    reservation.setId(1L);
    reservation.setUserId(1L);
    reservation.setListingId(listingRepo.getAllListings().get(0).getUuid());
  }

  @AfterEach
  public void tearDown() {
    jdbcTemplate.execute("DELETE FROM reservations");
    jdbcTemplate.execute("DELETE FROM listings");
  }

  @Test
  public void testAddReservation() {
    reservationRepo.addReservation(reservation);

    Reservation retrieved = reservationRepo.getReservationById(1L);
    assertThat(retrieved).isNotNull();
    assertThat(retrieved.getUserId()).isEqualTo(1L);
    assertThat(retrieved.getListingId()).isEqualTo(reservation.getListingId());
  }

  @Test
  public void testGetReservationById() {
    reservationRepo.addReservation(reservation);

    Reservation retrieved = reservationRepo.getReservationById(1L);
    assertThat(retrieved).isNotNull();
  }

  @Test
  public void testGetReservationsByUserId() {
    reservationRepo.addReservation(reservation);

    List<Reservation> reservations = reservationRepo.getReservationsByUserId(
        1L,
        Timestamp.from(ZonedDateTime.now(ZoneId.systemDefault()).minusHours(1).toInstant()));
    assertThat(reservations).hasSize(1);
  }

  @Test
  public void testDeleteReservation() {
    reservationRepo.addReservation(reservation);
    reservationRepo.deleteReservation(1L);

    Reservation retrieved = reservationRepo.getReservationById(1L);
    assertThat(retrieved).isNull();
  }

  @Test
  public void testGetReservationByUserIdAndListingId() {
    reservationRepo.addReservation(reservation);

    Reservation retrieved = reservationRepo.getReservationByUserIdAndListingId(
        1L, reservation.getListingId(), 
        Timestamp.from(ZonedDateTime.now(ZoneId.systemDefault()).minusHours(1).toInstant()));
    assertThat(retrieved).isNotNull();
    assertThat(retrieved.getUserId()).isEqualTo(1L);
    assertThat(retrieved.getListingId()).isEqualTo(reservation.getListingId());
  }

  @Test
  public void testGetReservationByListingId() {
    reservationRepo.addReservation(reservation);

    Reservation retrieved = reservationRepo.getReservationByListingId(
        reservation.getListingId(), 
        Timestamp.from(ZonedDateTime.now(ZoneId.systemDefault()).minusHours(1).toInstant()));
    assertThat(retrieved).isNotNull();
    assertThat(retrieved.getListingId()).isEqualTo(reservation.getListingId());
  }
}
