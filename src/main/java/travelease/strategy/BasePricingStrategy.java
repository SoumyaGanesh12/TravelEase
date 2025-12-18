package travelease.strategy;

import travelease.state.Booking;

public class BasePricingStrategy implements PricingStrategyAPI {

    @Override
    public double calculatePrice(Booking booking) {
        if (booking.getTravelPackage() != null) return booking.getTravelPackage().getPrice();
        if (booking.getTravelItem() != null) return booking.getTravelItem().getPrice();
        return 0.0;
    }
}
