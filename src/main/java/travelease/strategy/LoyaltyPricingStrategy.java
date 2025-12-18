package travelease.strategy;

import travelease.state.Booking;

public class LoyaltyPricingStrategy implements PricingStrategyAPI {

    private final int loyaltyThreshold;
    private final double loyaltyDiscountPercent;

    public LoyaltyPricingStrategy(int loyaltyThreshold, double loyaltyDiscountPercent) {
        this.loyaltyThreshold = loyaltyThreshold;
        this.loyaltyDiscountPercent = loyaltyDiscountPercent;
    }

    @Override
    public double calculatePrice(Booking booking) {
        double price = 0.0;
        if (booking.getTravelPackage() != null) price = booking.getTravelPackage().getPrice();
        else if (booking.getTravelItem() != null) price = booking.getTravelItem().getPrice();

        if (booking.getCustomer() != null && booking.getCustomer().getLoyaltyPoints() >= loyaltyThreshold) {
            price = price * (1 - loyaltyDiscountPercent / 100.0);
        }
        return price;
    }
}
