package travelease.observer;
import travelease.state.Booking;

public class EmailNotificationObserver implements ObserverAPI {

    private String customerEmail;

    public EmailNotificationObserver(String email){
        this.customerEmail = email;
    }

    @Override
    public void update(Booking booking) {
        System.out.println("Email sent to " + customerEmail +
                ": Booking confirmed -> " + booking.getId());
    }
}
