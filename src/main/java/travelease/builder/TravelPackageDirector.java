package travelease.builder;

import travelease.TravelItemAPI;
import travelease.composite.TravelPackage;

public class TravelPackageDirector {

    private final TravelPackageBuilderAPI builder;

    public TravelPackageDirector(TravelPackageBuilderAPI builder) {
        this.builder = builder;
    }

    // --- Build an empty package (Director decides the flow) ---
    public TravelPackage constructEmptyPackage() {
        // Method chaining
        return builder.reset().build();
    }

    /**
     * Director orchestrates sequence; items come from external input.
     */
    public TravelPackage buildCustomPackage(
            TravelItemAPI flight,
            TravelItemAPI hotel,
            TravelItemAPI car) {

        // Only add items that are provided (not null)
        // This allows any combination: just flight, flight+hotel, hotel+car, etc.
        if (flight != null) {
            builder.addFlight(flight);
        }
        if (hotel != null) {
            builder.addHotel(hotel);
        }
        if (car != null) {
            builder.addCarRental(car);
        }

        return builder.build();
    }
}
