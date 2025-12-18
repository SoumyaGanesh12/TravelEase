package travelease.template;

import java.util.List;

import travelease.TravelItemAPI;
import travelease.facade.ExternalServiceFacade;
import travelease.facade.ExternalServiceFacadeAPI;
import travelease.helper.SearchCriteria;
import travelease.state.Booking;

/**
 * Template Pattern: Concrete implementation for hotel booking with hotel-specific search, payment, and confirmation.
 */
public class HotelBookingProcessor extends AbstractBookingProcess {

    private final ExternalServiceFacadeAPI facade;

    /**
     * Default constructor - creates default ExternalServiceFacade
     */
    public HotelBookingProcessor() {
        this(new ExternalServiceFacade());
    }

    public HotelBookingProcessor(ExternalServiceFacadeAPI facade) {
        this.facade = facade;
    }

    @Override
    public List<TravelItemAPI> search(SearchCriteria criteria) {
        System.out.println("Searching hotels for: " + criteria);
        return facade.searchAllHotels(criteria);
    }

    @Override
    protected boolean pay() {
        System.out.println("Payment done for hotel booking (simulated).");
        return true;
    }

    @Override
    protected Booking confirm() {
        // Use state transition to Confirmed which will handle notification
        booking.proceed();
        System.out.println("Hotel booking confirmed.");
        return booking;
    }
}
