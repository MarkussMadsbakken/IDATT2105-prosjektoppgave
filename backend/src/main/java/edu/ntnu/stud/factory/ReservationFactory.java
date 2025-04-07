package edu.ntnu.stud.factory;

import edu.ntnu.stud.model.Reservation;
import edu.ntnu.stud.model.ReservationRequest;

/**
 * Factory class for creating and converting Reservation objects.
 */
public class ReservationFactory {

  /**
   * Converts a ReservationRequest to a Reservation.
   *
   * @param request the ReservationRequest to convert
   * @return the resulting Reservation
   */
  public static Reservation fromRequest(ReservationRequest request) {
    return new Reservation(
        null,
        request.getListingId(),
        null,
        null);
  }
}
