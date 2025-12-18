package travelease.strategy;

import travelease.state.Booking;

public interface PricingStrategyAPI {
    /**
     * Calculates price based on the booking context.
     */
    double calculatePrice(Booking booking);
}
