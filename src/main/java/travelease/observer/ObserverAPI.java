package travelease.observer;
import travelease.state.Booking;

public interface ObserverAPI {
    void update(Booking booking);
}
