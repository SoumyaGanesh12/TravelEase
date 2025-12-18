package travelease.helper;

import travelease.Customer;
import travelease.observer.AppNotificationObserver;
import travelease.observer.EmailNotificationObserver;
import travelease.observer.NotificationPreferences;
import travelease.observer.ObserverAPI;
import travelease.observer.SMSNotificationObserver;
import travelease.state.Booking;

/**
 * Helper Service - Configures and manages Observer pattern notifications (Email, SMS, App) based on customer preferences.
 */
public class NotificationService implements NotificationServiceAPI {

    @Override
    public void notifyObservers(Booking booking) {
        for (ObserverAPI observer : booking.getObservers()) {
            observer.update(booking);
        }
    }

    /**
     * Attach observers to the booking according to customer preferences.
     */
    @Override
    public void configureObservers(Booking booking, NotificationPreferences prefs) {
        // Clear existing observers
        booking.getObservers().clear();

        Customer customer = booking.getCustomer();
        if (prefs.isEmailEnabled() && customer.getEmail() != null && !customer.getEmail().isEmpty()) {
            booking.attachObserver(new EmailNotificationObserver(customer.getEmail()));
        }
        if (prefs.isSmsEnabled() && customer.getPhoneNumber() != null && !customer.getPhoneNumber().isEmpty()) {
            booking.attachObserver(new SMSNotificationObserver(customer.getPhoneNumber()));
        }
        if (prefs.isAppEnabled() && customer.getCustomerID() != null && !customer.getCustomerID().isEmpty()) {
            booking.attachObserver(new AppNotificationObserver(customer.getCustomerID()));
        }
    }
}
