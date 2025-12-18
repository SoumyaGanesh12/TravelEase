package travelease.command;

import travelease.composite.TravelPackage;
import travelease.state.Booking;

/**
 * Command Pattern: Encapsulates adding a complete travel package to booking with undo support.
 */
public class AddPackageCmd implements CommandAPI {

    private final Booking booking;
    private final TravelPackage travelPackage;
    private TravelPackage previousState;

    public AddPackageCmd(Booking booking, TravelPackage travelPackage) {
        this.booking = booking;
        this.travelPackage = travelPackage;
    }

    @Override
    public void execute() {
        System.out.println("Executing AddPackageCmd...");
        previousState = booking.getTravelPackage() != null ? booking.getTravelPackage().clone() : null;
        booking.setTravelPackage(travelPackage);
    }

    @Override
    public void undo() {
        System.out.println("Undo AddPackageCmd...");
        if (previousState != null) {
            booking.setTravelPackage(previousState);
        } else {
            booking.setTravelPackage(null);
        }
    }
}
