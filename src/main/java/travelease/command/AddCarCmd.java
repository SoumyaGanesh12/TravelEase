package travelease.command;

import travelease.TravelItemAPI;
import travelease.composite.TravelPackage;
import travelease.state.Booking;

/**
 * Command Pattern: Encapsulates adding a car rental item to booking with undo support.
 */
public class AddCarCmd implements CommandAPI {

    private final Booking booking;
    private final TravelItemAPI car;
    private TravelPackage previousState;

    public AddCarCmd(Booking booking, TravelItemAPI car) {
        this.booking = booking;
        this.car = car;
    }

    @Override
    public void execute() {
        System.out.println("Executing AddCarCmd...");
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
        
        booking.getTravelPackage().addItem(car);
    }

    @Override
    public void undo() {
        System.out.println("Undo AddCarCmd...");
        if (previousState != null) {
            booking.setTravelPackage(previousState);
        } else {
            booking.setTravelPackage(null);
        }
    }
}
