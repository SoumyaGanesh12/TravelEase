package travelease.factory.family;

import travelease.factory.flight.AbstractFlight;
import travelease.factory.hotel.AbstractHotel;
import travelease.factory.carrental.AbstractCarRental;

/**
 * Abstract Factory for Travel products.
 * Concrete factories produce a consistent family: flight + hotel + car rental.
 */
public interface TravelAbstractFactoryAPI {

    /**
     * Create a flight, hotel, car with an explicit (override) price.
     * Useful for adapters that receive external/legacy pricing.
     */
    AbstractFlight createFlight(double price);

    AbstractHotel createHotel(double price);

    AbstractCarRental createCar(double price);
}
