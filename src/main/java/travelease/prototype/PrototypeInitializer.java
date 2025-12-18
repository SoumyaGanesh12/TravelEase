package travelease.prototype;

import travelease.composite.TravelPackage;
import travelease.TravelItemAPI;
import travelease.builder.CustomTravelPackageBuilder;
import travelease.builder.TravelPackageBuilderAPI;
import travelease.facade.BookingFacadeAPI;

import java.util.List;

/**
 * Initializes/registers TravelPackage prototypes using search results
 * from the facade/template processors.
 */
public final class PrototypeInitializer {
    private PrototypeInitializer() {
    }

    public static void initAllCombinations(BookingFacadeAPI facade,
            List<TravelItemAPI> resultFlights,
            List<TravelItemAPI> resultHotels,
            List<TravelItemAPI> resultCars) {

        // Build package via StandardTravelPackageBuilder
        TravelPackageBuilderAPI builder = new CustomTravelPackageBuilder();

        // Create all combinations of flight, hotel, and car packages
        int packageCount = 0;
        for (TravelItemAPI flight : resultFlights) {
            for (TravelItemAPI hotel : resultHotels) {
                for (TravelItemAPI car : resultCars) {
                    builder.reset();
                    builder.addFlight(flight);
                    builder.addHotel(hotel);
                    builder.addCarRental(car);

                    TravelPackage pkg = builder.build();

                    // Create a meaningful package name from item categories
                    String flightCategory = flight.getCategoryInfo().replace(" ", "-");
                    String hotelCategory = hotel.getCategoryInfo().replace(" ", "-");
                    String carCategory = car.getCategoryInfo().replace(" ", "-");

                    String packageName = flightCategory + "." + hotelCategory + "." + carCategory;
                    pkg.setPackageName(packageName);

                    // Create a registry key
                    String key = flightCategory.toLowerCase() + "-" +
                            hotelCategory.toLowerCase() + "-" +
                            carCategory.toLowerCase();

                    TravelPackagePrototypeRegistry.registerPrototype(key, pkg);
                    packageCount++;
                }
            }
        }
    }
}
