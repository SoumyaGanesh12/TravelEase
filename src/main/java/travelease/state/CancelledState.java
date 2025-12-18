package travelease.state;

/**
 * State Pattern: Final cancelled state preventing further state transitions.
 */
public class CancelledState implements BookingStateAPI {
    @Override
    public void proceed(Booking booking) {
        System.out.println("Cannot proceed. Booking is cancelled.");
    }

    @Override
    public void cancel(Booking booking) {
        System.out.println("Booking is already cancelled.");
    }
}