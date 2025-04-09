package edu.ntnu.stud.controller;

import edu.ntnu.stud.model.ReservationRequest;
import edu.ntnu.stud.model.base.DefaultResponse;
import edu.ntnu.stud.model.base.Reservation;
import edu.ntnu.stud.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
@Tag(name = "Reservation", 
    description = "Endpoints for managing reservations")
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
  @Operation(summary = "Add a reservation", 
      description = "Adds a new reservation to the database.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successfully added reservation"),
  })
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
  @Operation(summary = "Delete a reservation", 
      description = "Deletes a reservation by its ID.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successfully deleted reservation"),
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<DefaultResponse> deleteReservation(
      @RequestParam long id,
      @RequestHeader("Authorization") String token) {
    reservationService.deleteReservation(id, token);
    return ResponseEntity.ok(new DefaultResponse(
        "Reservation deleted successfully", "reservationDeleted"));
  }

  /**
   * Checks if a reservation exists for a given listing ID.
   *
   * @param listingId the ID of the listing to check for a reservation
   * @return a ResponseEntity containing the reservation if it exists, or a 404
   *         Not Found status if not found
   */
  @Operation(summary = "Check reservation existence", 
      description = "Checks if a reservation exists for a given listing ID.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Reservation exists"),
      @ApiResponse(responseCode = "204", description = "No reservation found"),
  })
  @GetMapping("/{listingId}")
  public ResponseEntity<Reservation> checkReservation(
      @PathVariable String listingId) {
    Reservation reservation = reservationService.checkReservation(listingId);
    if (reservation != null) {
      return ResponseEntity.ok(reservation);
    } else {
      return ResponseEntity.noContent().build();
    }
  }

  /**
   * Retrieves all reservations made by a user.
   *
   * @param token the JWT token of the user making the reservation
   * @return a ResponseEntity containing the list of reservations
   */
  @Operation(summary = "Get user reservations", 
      description = "Retrieves all reservations made by a user.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successfully retrieved reservations"),
      @ApiResponse(responseCode = "204", description = "No reservations found"),
  })
  @GetMapping("/user")
  public ResponseEntity<List<Reservation>> getReservationsByUserId(
      @RequestHeader("Authorization") String token) {
    List<Reservation> reservations = reservationService.getReservationByUserId(token);
    if (reservations.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(reservations);
  }

}
