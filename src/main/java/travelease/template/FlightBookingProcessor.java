package travelease.template;

import java.util.List;

import travelease.TravelItemAPI;
import travelease.facade.ExternalServiceFacade;
import travelease.facade.ExternalServiceFacadeAPI;
import travelease.helper.SearchCriteria;
import travelease.state.Booking;

/**
 * Template Pattern: Concrete implementation for flight booking with flight-specific search, payment, and confirmation.
 */
public class FlightBookingProcessor extends AbstractBookingProcess {

    private final ExternalServiceFacadeAPI facade;

    /**
     * Default constructor - creates default ExternalServiceFacade
     */
    public FlightBookingProcessor() {
        this(new ExternalServiceFacade());
    }

    public FlightBookingProcessor(ExternalServiceFacadeAPI facade) {
        this.facade = facade;
    }

    @Override
    public List<TravelItemAPI> search(SearchCriteria criteria) {
        System.out.println("Searching flights for: " + criteria);
        return facade.searchAllFlights(criteria);
    }

    @Override
    protected boolean pay() {
        System.out.println("Payment done for flight booking (simulated).");
        return true;
    }

    @Override
    protected Booking confirm() {
        // Use state transition to Confirmed which will handle notification
        booking.proceed();
        System.out.println("Flight booking confirmed.");
        return booking;
    }
}
