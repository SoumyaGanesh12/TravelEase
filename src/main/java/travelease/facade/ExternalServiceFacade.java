package travelease.facade;

import java.util.ArrayList;
import java.util.List;

import travelease.TravelItemAPI;
import travelease.adapter.car.CarAdapter;
import travelease.adapter.flight.AirlineAdapter;
import travelease.adapter.hotel.HotelAdapter;
import travelease.bridge.provider.CarProvider;
import travelease.bridge.provider.FlightProvider;
import travelease.bridge.provider.HotelProvider;
import travelease.bridge.service.CarBookingService;
import travelease.bridge.service.FlightBookingService;
import travelease.bridge.service.HotelBookingService;
import travelease.helper.SearchCriteria;

public class ExternalServiceFacade implements ExternalServiceFacadeAPI {

    // Layer 1: Adapters (convert legacy APIs to standard interface)
    private final AirlineAdapter flightAdapter = new AirlineAdapter();
    private final HotelAdapter hotelAdapter = new HotelAdapter();
    private final CarAdapter carAdapter = new CarAdapter();

    // Layer 2: Providers (abstraction in Bridge pattern)
    private final FlightProvider flightProvider = new FlightProvider(flightAdapter);
    private final HotelProvider hotelProvider = new HotelProvider(hotelAdapter);
    private final CarProvider carProvider = new CarProvider(carAdapter);

    // Layer 3: Services (implements Bridge pattern - decouples service from provider)
    private final FlightBookingService flightService = new FlightBookingService(flightProvider);
    private final HotelBookingService hotelService = new HotelBookingService(hotelProvider);
    private final CarBookingService carService = new CarBookingService(carProvider);

    @Override
    public List<TravelItemAPI> searchAllFlights(SearchCriteria criteria) {
        String query = convertCriteriaToFlightQuery(criteria);
        return flightService.search(query);
    }

    @Override
    public List<TravelItemAPI> searchAllHotels(SearchCriteria criteria) {
        String query = convertCriteriaToHotelQuery(criteria);
        return hotelService.search(query);
    }

    @Override
    public List<TravelItemAPI> searchAllCars(SearchCriteria criteria) {
        String query = convertCriteriaToCarQuery(criteria);
        return carService.search(query);
    }

    /**
     * Searches all categories (flights, hotels, cars)
     */
    public List<TravelItemAPI> searchAll(SearchCriteria criteria) {
        List<TravelItemAPI> allResults = new ArrayList<>();
        allResults.addAll(searchAllFlights(criteria));
        allResults.addAll(searchAllHotels(criteria));
        allResults.addAll(searchAllCars(criteria));
        return allResults;
    }

    // Helper methods to convert SearchCriteria to query strings
    
    private static String convertCriteriaToFlightQuery(SearchCriteria criteria) {
        if (criteria == null) {
            return "Boston,New York";
        }
        String origin = criteria.getOrigin() != null ? criteria.getOrigin() : "Boston";
        String destination = criteria.getDestination() != null ? criteria.getDestination() : "New York";
        return origin + "," + destination;
    }

    private static String convertCriteriaToHotelQuery(SearchCriteria criteria) {
        if (criteria == null) {
            return "Boston";
        }
        // For hotels, we use destination as the city
        return criteria.getDestination() != null ? criteria.getDestination() : "Boston";
    }

    private static String convertCriteriaToCarQuery(SearchCriteria criteria) {
        if (criteria == null) {
            return "Boston";
        }
        // For cars, we use destination as the city
        return criteria.getDestination() != null ? criteria.getDestination() : "Boston";
    }
}