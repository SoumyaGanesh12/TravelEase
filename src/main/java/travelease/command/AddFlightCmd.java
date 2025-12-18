package travelease.command;

import travelease.TravelItemAPI;
import travelease.composite.TravelPackage;
import travelease.state.Booking;

/**
 * Command Pattern: Encapsulates adding a flight item to booking with undo support.
 */
public class AddFlightCmd implements CommandAPI {

    private final Booking booking;
    private final TravelItemAPI flight;
    private TravelPackage previousState;

    public AddFlightCmd(Booking booking, TravelItemAPI flight) {
        this.booking = booking;
        this.flight = flight;
    }

    @Override
    public void execute() {
        System.out.println("Executing AddFlightCmd...");
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
        
        booking.getTravelPackage().addItem(flight);
    }

    @Override
    public void undo() {
        System.out.println("Undo AddFlightCmd...");
        if (previousState != null) {
            booking.setTravelPackage(previousState);
        } else {
            booking.setTravelPackage(null);
        }
    }
}
