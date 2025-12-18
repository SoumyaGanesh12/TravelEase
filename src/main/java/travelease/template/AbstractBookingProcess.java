package travelease.template;

import java.util.List;

import travelease.TravelItemAPI;
import travelease.helper.SearchCriteria;
import travelease.state.Booking;

/**
 * Template Pattern: Abstract class defining booking workflow skeleton (validate → pay → confirm) with customizable steps.
 */
public abstract class AbstractBookingProcess {

    protected Booking booking;

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    /**
     * Template Method - Process booking with pre-selected items.
     * This method orchestrates: Validate → Pay → Confirm
     * 
     * @return Confirmed Booking if payment succeeds, null otherwise
     */
    public final Booking processSelectedBooking() {
        if (booking == null) {
            System.out.println("No booking set. Cannot process booking.");
            return null;
        }
        
        if (!validateBooking()) {
            System.out.println("Booking validation failed. Cannot proceed.");
            return null;
        }
        
        boolean paymentSuccessful = pay();
        
        if (!paymentSuccessful) {
            System.out.println("Payment failed. Booking cancelled.");
            return null;
        }
        
        Booking confirmedBooking = confirm();
        return confirmedBooking;
    }

    protected boolean validateBooking() {
        if (booking == null) {
            return false;
        }
        if (booking.getTravelPackage() == null || booking.getTravelPackage().getItems().isEmpty()) {
            System.out.println("Booking has no items. Cannot proceed.");
            return false;
        }
        return true;
    }

    public abstract List<TravelItemAPI> search(SearchCriteria criteria);
    protected abstract boolean pay();
    protected abstract Booking confirm();
}
