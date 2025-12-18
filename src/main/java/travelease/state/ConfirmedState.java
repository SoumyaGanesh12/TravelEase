package travelease.state;

/**
 * State Pattern: Final confirmed state preventing further state transitions.
 */
public class ConfirmedState implements BookingStateAPI {
    @Override
    public void proceed(Booking booking) {
        System.out.println("Booking is already confirmed. No further action.");
    }

    @Override
    public void cancel(Booking booking) {
        System.out.println("Cancellation not allowed: booking is already confirmed.");
    }
}