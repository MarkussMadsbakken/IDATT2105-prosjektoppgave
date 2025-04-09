package edu.ntnu.stud.service;

import edu.ntnu.stud.factory.ReservationFactory;
import edu.ntnu.stud.model.ReservationRequest;
import edu.ntnu.stud.model.base.Notification;
import edu.ntnu.stud.model.base.Reservation;
import edu.ntnu.stud.repo.ReservationRepo;
import edu.ntnu.stud.util.Validate;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    Validate.that(reservationRequest.getListingId(),
        Validate.isNotBlankOrNull(), "Listing ID cannot be null or empty");
    Long userId = jwtService.extractUserId(token.substring(7));
    Timestamp expirationDate = Timestamp.from(
          ZonedDateTime.now(ZoneId.systemDefault()).minusHours(1).toInstant());
    Reservation existingReservation = reservationRepo.getReservationByListingId(
        reservationRequest.getListingId(), expirationDate);
    // Check if a reservation already exists for the user and listing
    Validate.that(existingReservation, Validate.isNull(), 
        "There alreaddy is a reservation for this listing");

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
    Long userId = jwtService.extractUserId(token.substring(7));
    Timestamp expirationDate = Timestamp.from(
        ZonedDateTime.now(ZoneId.systemDefault()).minusHours(1).toInstant());
    return reservationRepo.getReservationsByUserId(userId, expirationDate);
  }

  /**
   * Checks if a reservation exists for a given listing ID.
   *
   * @param listingId the ID of the listing to check for a reservation
   * @return the Reservation object if it exists, or null if not found
   */
  public Reservation checkReservation(String listingId) {
    Timestamp expirationDate = Timestamp.from(
        ZonedDateTime.now(ZoneId.systemDefault()).minusHours(1).toInstant());
    return reservationRepo.getReservationByListingId(listingId, expirationDate);
  }

  /**
   * Deletes a reservation by its ID.
   *
   * @param id the ID of the reservation to delete
   * @param token the JWT token of the user making the reservation
   */
  public void deleteReservation(long id, String token) {
    Long userId = jwtService.extractUserId(token.substring(7));
    Reservation reservation = reservationRepo.getReservationById(id);
    Validate.that(reservation, Validate.isNotNull(), "Reservation not found");
    Validate.that(reservation.getUserId() == userId, Validate.isTrue(), 
        "You are not authorized to delete this reservation");
    reservationRepo.deleteReservation(id);
  }
}
