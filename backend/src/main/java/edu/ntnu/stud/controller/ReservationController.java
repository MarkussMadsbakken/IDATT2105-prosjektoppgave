package edu.ntnu.stud.controller;

import edu.ntnu.stud.model.DefaultResponse;
import edu.ntnu.stud.model.Reservation;
import edu.ntnu.stud.model.ReservationRequest;
import edu.ntnu.stud.service.ReservationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for managing reservations.
 */
@RestController
@RequestMapping("/api/reservation")
public class ReservationController {
  @Autowired
  private ReservationService reservationService;

  /**
   * Adds a new reservation to the database.
   *
   * @param reservationRequest the ReservationRequest object containing
   *                           reservation details
   * @param token              the JWT token of the user making the reservation
   * @return a ResponseEntity indicating the result of the operation
   */
  @PostMapping
  public ResponseEntity<Reservation> addReservation(
      @RequestBody ReservationRequest reservationRequest,
      @RequestHeader("Authorization") String token) {
    Reservation reservation = reservationService.addReservation(reservationRequest, token);
    return ResponseEntity.ok(reservation);
  }

  /**
   * Deletes a reservation by its ID.
   *
   * @param id the ID of the reservation to delete
   * @return a ResponseEntity indicating the result of the operation
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<DefaultResponse> deleteReservation(
      @RequestParam long id,
      @RequestHeader("Authorization") String token) {
    try {
      reservationService.deleteReservation(id, token);
      return ResponseEntity.ok(new DefaultResponse(
          "Reservation deleted successfully", "reservationDeleted"));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DefaultResponse(
          "Failed to delete reservation: " + e.getMessage(), "reservationNotDeleted"));
    }
  }

  /**
   * Checks if a reservation exists for a given listing ID.
   *
   * @param listingId the ID of the listing to check for a reservation
   * @return a ResponseEntity containing the reservation if it exists, or a 404
   *         Not Found status if not found
   */
  @GetMapping("/{listingId}")
  public ResponseEntity<Reservation> checkReservation(
      @PathVariable String listingId) {
    Reservation reservation = reservationService.checkReservation(listingId);
    if (reservation != null) {
      return ResponseEntity.ok(reservation);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Retrieves all reservations made by a user.
   *
   * @param token the JWT token of the user making the reservation
   * @return a ResponseEntity containing the list of reservations
   */
  @GetMapping("/user")
  public ResponseEntity<List<Reservation>> getReservationsByUserId(
      @RequestHeader("Authorization") String token) {
    try {
      List<Reservation> reservations = reservationService.getReservationByUserId(token);
      return ResponseEntity.ok(reservations);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

}
