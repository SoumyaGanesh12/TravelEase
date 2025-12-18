package travelease.helper;

import travelease.observer.NotificationPreferences;
import travelease.state.Booking;

/**
 * Helper Service Interface - Defines contract for configuring and notifying observers based on preferences.
 */
public interface NotificationServiceAPI {
    void configureObservers(Booking booking, NotificationPreferences preferences);
    void notifyObservers(Booking booking);
}

