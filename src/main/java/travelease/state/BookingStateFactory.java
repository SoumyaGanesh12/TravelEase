package travelease.state;

/**
 * State Pattern: Factory for creating booking state instances (Pending, Confirmed, Cancelled).
 */
public class BookingStateFactory {
    
    /**
     * Creates a pending state.
     * @return PendingState instance
     */
    public BookingStateAPI createPendingState() {
        return new PendingState();
    }
    
    /**
     * Creates a confirmed state.
     * @return ConfirmedState instance
     */
    public BookingStateAPI createConfirmedState() {
        return new ConfirmedState();
    }
    
    /**
     * Creates a cancelled state.
     * @return CancelledState instance
     */
    public BookingStateAPI createCancelledState() {
        return new CancelledState();
    }
}

