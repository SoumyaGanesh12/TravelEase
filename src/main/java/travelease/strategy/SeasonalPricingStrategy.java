package travelease.strategy;

import travelease.state.Booking;

public class SeasonalPricingStrategy implements PricingStrategyAPI {

    private final double discountPercent;

    public SeasonalPricingStrategy(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    @Override
    public double calculatePrice(Booking booking) {
        double price = 0.0;
        if (booking.getTravelPackage() != null) price = booking.getTravelPackage().getPrice();
        else if (booking.getTravelItem() != null) price = booking.getTravelItem().getPrice();

        return price * (1 - discountPercent / 100.0);
    }
}
