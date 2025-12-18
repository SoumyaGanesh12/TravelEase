package travelease.observer;
import travelease.state.Booking;

public class SMSNotificationObserver implements ObserverAPI {

    private String phoneNumber;
    
    public SMSNotificationObserver(String phone){
        this.phoneNumber = phone;
    }

    @Override
    public void update(Booking booking) {
        System.out.println("SMS sent to " + phoneNumber +
                ": Booking confirmed -> " + booking.getId());
    }
}