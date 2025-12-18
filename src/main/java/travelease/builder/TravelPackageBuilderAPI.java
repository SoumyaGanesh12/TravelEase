package travelease.builder;

import travelease.TravelItemAPI;
import travelease.composite.TravelPackage;

public interface TravelPackageBuilderAPI {

    TravelPackageBuilderAPI reset(); // Start new package

    TravelPackageBuilderAPI addFlight(TravelItemAPI flightItem);

    TravelPackageBuilderAPI addHotel(TravelItemAPI hotelItem);

    TravelPackageBuilderAPI addCarRental(TravelItemAPI carItem);

    TravelPackage build(); // Final product

}
