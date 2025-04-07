package edu.ntnu.stud.service;

import edu.ntnu.stud.factory.ReservationFactory;
import edu.ntnu.stud.model.Notification;
import edu.ntnu.stud.model.Reservation;
import edu.ntnu.stud.model.ReservationRequest;
import edu.ntnu.stud.repo.ReservationRepo;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing reservations.
 */
@Service
public class ReservationService {
  @Autowired
  private ReservationRepo reservationRepo;
  @Autowired
  private JWTService jwtService;
  @Autowired
  private NotificationService notificationService;
  @Autowired
  private ListingService listingService;

  /**
   * Adds a new reservation to the database.
   *
   * @param reservationRequest the ReservationRequest object containing reservation details
   * @param token the JWT token of the user making the reservation
   * @return the created Reservation object
   */
  public Reservation addReservation(ReservationRequest reservationRequest, String token) {
    // Fetch values
    Long userId = jwtService.extractUserId(token);
    Timestamp expirationDate = new Timestamp(System.currentTimeMillis() 
                             - Time.valueOf("01:00:00").getTime());
    Reservation existingReservation = reservationRepo.getReservationByUserIdAndListingId(
        userId, reservationRequest.getListingId(), expirationDate);
    // Check if a reservation already exists for the user and listing
    if (existingReservation != null) {
      throw new IllegalArgumentException("A reservation already exists for this listing.");
    }
    // Create and add the new reservation
    Reservation reservation = ReservationFactory.fromRequest(reservationRequest);
    reservation.setUserId(userId);
    reservationRepo.addReservation(reservation);

    // Send notification to the listing owner
    Notification notification = new Notification();
    notification.setUserId(
        listingService.getListingByUuid(reservationRequest.getListingId()).getOwnerId());
    notification.setMessage("userReservedYourListing");
    notification.setLink(
        "/listing/" + reservationRequest.getListingId());
    notificationService.addNotification(notification);

    // Return the created reservation
    return reservationRepo.getReservationByUserIdAndListingId(
        userId, reservationRequest.getListingId(), expirationDate);
  }

  /**
   * Retrieves a reservation by its ID.
   *
   * @param id the ID of the reservation to retrieve
   */
  public Reservation getReservationById(long id) {
    return reservationRepo.getReservationById(id);
  }

  /**
   * Retrieves all reservation done by a user.
   *
   * @param token the JWT token of the user making the reservation
   */
  public List<Reservation> getReservationByUserId(String token) {
    Long userId = jwtService.extractUserId(token);
    Timestamp expirationDate = new Timestamp(System.currentTimeMillis() 
                             - Time.valueOf("01:00:00").getTime());
    return reservationRepo.getReservationsByUserId(userId, expirationDate);
  }

  /**
   * Checks if a reservation exists for a given listing ID.
   *
   * @param listingId the ID of the listing to check for a reservation
   * @return the Reservation object if it exists, or null if not found
   */
  public Reservation checkReservation(String listingId) {
    Timestamp expirationDate = new Timestamp(System.currentTimeMillis() 
                             - Time.valueOf("01:00:00").getTime());
    return reservationRepo.getReservationByListingId(listingId, expirationDate);
  }

  /**
   * Deletes a reservation by its ID.
   *
   * @param id the ID of the reservation to delete
   * @param token the JWT token of the user making the reservation
   */
  public void deleteReservation(long id, String token) {
    Long userId = jwtService.extractUserId(token);
    Reservation reservation = reservationRepo.getReservationById(id);
    if (reservation != null && reservation.getUserId().equals(userId)) {
      reservationRepo.deleteReservation(id);
    } else {
      throw new IllegalArgumentException("Reservation not found or user not authorized");
    }
  }
}
