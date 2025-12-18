package travelease.observer;
import travelease.state.Booking;

public class AppNotificationObserver implements ObserverAPI {

    private final String customerID;

    public AppNotificationObserver(String customerID){
        this.customerID = customerID;
    }

    @Override
    public void update(Booking booking) {
        System.out.println("In-App: Notification to user " + customerID +
                ": Booking " + booking.getId() + " confirmed!");
    }
}