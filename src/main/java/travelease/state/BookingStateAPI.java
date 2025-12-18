package travelease.state;

/**
 * State Pattern Interface - Defines contract for booking state transitions (proceed/cancel).
 */
public interface BookingStateAPI {
    void proceed(Booking booking);
    void cancel(Booking booking);
}
