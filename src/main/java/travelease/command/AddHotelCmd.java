package travelease.command;

import travelease.TravelItemAPI;
import travelease.composite.TravelPackage;
import travelease.state.Booking;

/**
 * Command Pattern: Encapsulates adding a hotel item to booking with undo support.
 */
public class AddHotelCmd implements CommandAPI {

    private final Booking booking;
    private final TravelItemAPI hotel;
    private TravelPackage previousState;

    public AddHotelCmd(Booking booking, TravelItemAPI hotel) {
        this.booking = booking;
        this.hotel = hotel;
    }

    @Override
    public void execute() {
        System.out.println("Executing AddHotelCmd...");
        // Save previous state (clone if exists)
        if (booking.getTravelPackage() != null) {
            previousState = booking.getTravelPackage().clone();
        } else {
            previousState = null;
        }
        
        // Get or create package
        if (booking.getTravelPackage() == null) {
            booking.setTravelPackage(new travelease.composite.TravelPackage());
        }
        
        booking.getTravelPackage().addItem(hotel);
    }

    @Override
    public void undo() {
        System.out.println("Undo AddHotelCmd...");
        if (previousState != null) {
            booking.setTravelPackage(previousState);
        } else {
            booking.setTravelPackage(null);
        }
    }
}
