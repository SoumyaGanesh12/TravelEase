package travelease.facade;

import java.util.List;

import travelease.TravelItemAPI;
import travelease.composite.TravelPackage;
import travelease.helper.SearchCriteria;
import travelease.observer.NotificationPreferences;
import travelease.state.Booking;

/**
 * Facade Pattern Interface Provides simplified unified interface to complex booking subsystem operations.
 */
public interface BookingFacadeAPI {

    // SEARCH
    List<List<TravelItemAPI>> searchIndividualItems(SearchCriteria criteria);

    // CUSTOMER PROFILE CREATION
    void createCustomerProfile(String name, String email, String phoneNumber, String customerId);

    // CREATE EMPTY BOOKING & SET NOTIFICATION PREFERENCES
    void createEmptyBooking(String name, String email, String phoneNumber, String customerId, NotificationPreferences preferences);

    // ADD ITEMS
    void addFlight(TravelItemAPI flight);
    void addHotel(TravelItemAPI hotel);
    void addCar(TravelItemAPI car);
    
    // ADD WHOLE PACKAGE
    void addPackage(TravelPackage travelPackage);

    // ADD-ONS
    void addAddOn(TravelItemAPI targetItem, String addOnChoice);

    // PRICING
    double applyPricing();

    // STATE ACTIONS
    void confirmBooking();
    void cancelBooking();

    // COMMAND UNDO
    void undoLastAction();

    // GETTER
    Booking getCurrentBooking();
}
