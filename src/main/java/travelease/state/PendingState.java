package travelease.state;

/**
 * State Pattern: Initial booking state allowing transition to Confirmed or Cancelled.
 */
public class PendingState implements BookingStateAPI {
    
    // Use factory for state creation
    private static final BookingStateFactory stateFactory = new BookingStateFactory();
    
    @Override
    public void proceed(Booking booking) {
        System.out.println("Booking proceeding from Pending to Confirmed.");
        booking.setState(stateFactory.createConfirmedState());
        // Notify only on confirmation
        booking.notifyObservers();
    }

    @Override
    public void cancel(Booking booking) {
        System.out.println("Booking cancelled from Pending state.");
        booking.setState(stateFactory.createCancelledState());
    }
}
