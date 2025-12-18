package travelease.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import travelease.TravelItemAPI;
import travelease.factory.carrental.AbstractCarRental;
import travelease.factory.family.BudgetTravelFactory;
import travelease.factory.family.BusinessTravelFactory;
import travelease.factory.family.LeisureTravelFactory;
import travelease.factory.family.TravelAbstractFactoryAPI;
import travelease.factory.flight.AbstractFlight;
import travelease.factory.hotel.AbstractHotel;

/**
 * Converter utility that transforms legacy API string data into TravelItemAPI objects
 * using factory methods. This is the core of the Adapter pattern implementation.
 */
public class TravelItemConverter {
    
    private static final TravelAbstractFactoryAPI budgetFactory = new BudgetTravelFactory();
    private static final TravelAbstractFactoryAPI businessFactory = new BusinessTravelFactory();
    private static final TravelAbstractFactoryAPI leisureFactory = new LeisureTravelFactory();
    // private static final FlyweightSingletonFactory flyweightFactory = FlyweightSingletonFactory.getInstance();
    
    // Pattern to extract price from strings like "$30/day" or "$299.99"
    private static final Pattern PRICE_PATTERN = Pattern.compile("\\$([0-9]+\\.?[0-9]*)");
    
    /**
     * Converts a list of legacy string data to TravelItemAPI objects
     * @param legacyData List of strings from legacy API
     * @param category Type of item: "Car", "Flight", or "Hotel"
     * @return List of TravelItemAPI objects
     */
    public static List<TravelItemAPI> fromLegacyStrings(List<String> legacyData, String category) {
        List<TravelItemAPI> items = new ArrayList<>();
        
        if (legacyData == null || legacyData.isEmpty()) {
            return items;
        }
        
        for (String data : legacyData) {
            try {
                TravelItemAPI item = convertSingleItem(data, category);
                if (item != null) {
                    items.add(item);
                }
            } catch (Exception e) {
                System.err.println("Error converting legacy data: " + data + " - " + e.getMessage());
            }
        }
        
        return items;
    }
    
    /**
     * Converts a single legacy string to a TravelItemAPI object
     */
    private static TravelItemAPI convertSingleItem(String data, String category) {
        if (data == null || data.trim().isEmpty()) {
            return null;
        }
        
        double price = extractPrice(data);
        
        if (category.equalsIgnoreCase("Car")) {
            return convertToCar(data, price);
        } else if (category.equalsIgnoreCase("Flight")) {
            return convertToFlight(data, price);
        } else if (category.equalsIgnoreCase("Hotel")) {
            return convertToHotel(data, price);
        }
        
        return null;
    }
    
    /**
     * Extracts price from legacy string format
     */
    private static double extractPrice(String data) {
        Matcher matcher = PRICE_PATTERN.matcher(data);
        if (matcher.find()) {
            try {
                return Double.parseDouble(matcher.group(1));
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }
        return 0.0;
    }
    
    /**
     * Converts legacy car string to AbstractCarRental
     * Format: "UberX | Boston | $30/day"
     */
    private static AbstractCarRental convertToCar(String data, double price) {
        String[] parts = data.split("\\|");
        if (parts.length < 3) {
            return null;
        }

        String carName = parts[0].trim();
        String location = parts[1].trim();

        AbstractCarRental car = null;

        for (String part : parts) {

            String p = part.toLowerCase();

            if (p.contains("budget")) {
                car = (AbstractCarRental) budgetFactory.createCar(price);
                break;
            }

            if (p.contains("business")) {
                car = (AbstractCarRental) businessFactory.createCar(price);
                break;
            }

            if (p.contains("premium")) {
                car = (AbstractCarRental) leisureFactory.createCar(price);
                break;
            }
        }

        // If no category matched, create a default car
        if (car == null) {
            car = (AbstractCarRental) budgetFactory.createCar(price);
        }

        // Populate shared fields
        car.setCarName(carName);
        car.setRentalCompany(
                carName.toLowerCase().contains("uber") ? "Uber" :
                "Car Rental"
        );
        car.setPickupLocation(location);
        car.setDropoffLocation(location);
        // car.setPickupDate(pickupDate);
        // car.setDropoffDate(dropoffDate);

        return car;
    }

    /**
     * Converts legacy flight string to AbstractFlight
     * Format: "American Airlines | AA123 | Boston,New York | $299.99 | business"
     */
    private static AbstractFlight convertToFlight(String data, double price) {
        String[] parts = data.split("\\|");
        if (parts.length < 4) {
            return null;
        }

        String airline = parts[0].trim();
        String flightNumber = parts[1].trim();
        String[] cities = parts[2].trim().split(",");
        if (cities.length < 2) {
            return null;
        }

        String origin = cities[0].trim();
        String destination = cities[1].trim();

        AbstractFlight flight = null;


        for (String part : parts) {
            String p = part.toLowerCase();

            if (p.contains("economy") || p.contains("budget")) {
                flight = (AbstractFlight) budgetFactory.createFlight(price);
                break;
            }

            if (p.contains("business")) {
                flight = (AbstractFlight) businessFactory.createFlight(price);
                break;
            }

            if (p.contains("premium")) {
                flight = (AbstractFlight) leisureFactory.createFlight(price);
                break;
            }
        }


        if (flight == null) {
            flight = (AbstractFlight) budgetFactory.createFlight(price);
        }

        flight.setFlightName(airline);
        flight.setFlightNumber(flightNumber);
        flight.setOrigin(origin);
        flight.setDestination(destination);

        // flight.departureTime("09:00 AM"); // Default time
        // flight.arrivalTime("12:00 PM");   // Default time
        // flight.setSeatCount(150);         // Default seat count
        // flight.setSeatAssignment(airline);

        return flight;
    }

    /**
     * Converts legacy hotel string to AbstractHotel
     * Format: "Hilton | New York | $150/night | leisure"
     */
    private static AbstractHotel convertToHotel(String data, double price) {
        String[] parts = data.split("\\|");
        if (parts.length < 3) {
            return null;
        }

        String hotelName = parts[0].trim();
        String location = parts[1].trim();

        AbstractHotel hotel = null;


        for (String part : parts) {
            String p = part.toLowerCase();

            if (p.contains("budget")) {
                hotel = (AbstractHotel) budgetFactory.createHotel(price);
                break;
            }

            if (p.contains("business")) {
                hotel = (AbstractHotel) businessFactory.createHotel(price);
                break;
            }

            if (p.contains("leisure")) {
                hotel = (AbstractHotel) leisureFactory.createHotel(price);
                break;
            }
        }


        if (hotel == null) {
            hotel = (AbstractHotel) budgetFactory.createHotel(price);
        }

        hotel.setHotelName(hotelName);
        hotel.setLocation(location);
        hotel.setRoomCount(1); // Default to 1 room
        // hotel.setCheckInDate(checkInDate);
        // hotel.setCheckOutDate(checkOutDate);

        return hotel;
    }

}

