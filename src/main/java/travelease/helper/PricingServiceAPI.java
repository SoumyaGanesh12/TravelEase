package travelease.helper;

import travelease.state.Booking;
import travelease.strategy.PricingStrategyAPI;

/**
 * Helper Service Interface - Defines contract for price calculation with strategy pattern support.
 */
public interface PricingServiceAPI {
    double calculatePrice(Booking booking);
    void setSeasonalDiscountPercent(double percent);
    void setLoyaltyThreshold(int threshold);
    void setLoyaltyDiscountPercent(double percent);
    double getSeasonalDiscountPercent();
    int getLoyaltyThreshold();
    double getLoyaltyDiscountPercent();
    void setBaseStrategy(PricingStrategyAPI strategy);

    void setLoyaltyStrategy(PricingStrategyAPI strategy);

    void setSeasonalStrategy(PricingStrategyAPI strategy);

    void printPriceBreakdown(Booking booking);
}

