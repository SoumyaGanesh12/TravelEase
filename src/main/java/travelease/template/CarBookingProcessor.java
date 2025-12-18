package travelease.template;

import java.util.List;

import travelease.TravelItemAPI;
import travelease.facade.ExternalServiceFacade;
import travelease.facade.ExternalServiceFacadeAPI;
import travelease.helper.SearchCriteria;
import travelease.state.Booking;

/**
 * Template Pattern: Concrete implementation for car rental booking with car-specific search, payment, and confirmation.
 */
public class CarBookingProcessor extends AbstractBookingProcess {

    private final ExternalServiceFacadeAPI facade;

    /**
     * Default constructor - creates default ExternalServiceFacade
     */
    public CarBookingProcessor() {
        this(new ExternalServiceFacade());
    }

    public CarBookingProcessor(ExternalServiceFacadeAPI facade) {
        this.facade = facade;
    }

    @Override
    public List<TravelItemAPI> search(SearchCriteria criteria) {
        System.out.println("Searching cars for: " + criteria);
        return facade.searchAllCars(criteria);
    }

    @Override
    protected boolean pay() {
        System.out.println("Payment done for car booking (simulated).");
        return true;
    }

    @Override
    protected Booking confirm() {
        // Use state transition to Confirmed which will handle notification
        booking.proceed();
        System.out.println("Car booking confirmed.");
        return booking;
    }
}
