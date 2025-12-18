package travelease.template;

import java.util.ArrayList;
import java.util.List;

import travelease.TravelItemAPI;
import travelease.facade.ExternalServiceFacade;
import travelease.facade.ExternalServiceFacadeAPI;
import travelease.helper.SearchCriteria;
import travelease.state.Booking;

/**
 * Template Pattern: Concrete implementation for package booking combining flights, hotels, and cars with unified workflow.
 */
public class PackageBookingProcessor extends AbstractBookingProcess {

    private final ExternalServiceFacadeAPI facade;

    /**
     * Default constructor - creates default ExternalServiceFacade
     */
    public PackageBookingProcessor() {
        this(new ExternalServiceFacade());
    }

    public PackageBookingProcessor(ExternalServiceFacadeAPI facade) {
        this.facade = facade;
    }

    @Override
    public List<TravelItemAPI> search(SearchCriteria criteria) {
        System.out.println("Searching package options (flights, hotels, cars) for: " + criteria);
        
        // Combine results from all categories
        List<TravelItemAPI> allResults = new ArrayList<>();
        allResults.addAll(facade.searchAllFlights(criteria));
        allResults.addAll(facade.searchAllHotels(criteria));
        allResults.addAll(facade.searchAllCars(criteria));
        
        System.out.println("Package search complete. Found " + allResults.size() + " items.");
        return allResults;
    }

    @Override
    protected boolean pay() {
        System.out.println("Payment done for package booking (simulated).");
        return true;
    }

    @Override
    protected Booking confirm() {
        // Use state transition to Confirmed which will handle notification
        booking.proceed();
        System.out.println("Package booking confirmed.");
        return booking;
    }
}
