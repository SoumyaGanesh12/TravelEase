package travelease.helper;

import travelease.composite.TravelPackage;
import travelease.state.Booking;
import travelease.strategy.PricingStrategyAPI;

/**
 * Helper Service - Calculates booking prices using Strategy pattern with loyalty and seasonal discounts.
 */
public class PricingService implements PricingServiceAPI {

    // Config values (exposed through the API)
    private double seasonalDiscountPercent = 10.0;
    private int loyaltyThreshold = 100;
    private double loyaltyDiscountPercent = 10.0;

    // Optional strategies (can be injected)
    private PricingStrategyAPI baseStrategy;
    private PricingStrategyAPI loyaltyStrategy;
    private PricingStrategyAPI seasonalStrategy;

    public PricingService() {
        // default - no strategies set (service will use booking + config fallback)
    }

    // STRATEGY SETTERS

    @Override
    public void setBaseStrategy(PricingStrategyAPI strategy) {
        this.baseStrategy = strategy;
    }

    @Override
    public void setLoyaltyStrategy(PricingStrategyAPI strategy) {
        this.loyaltyStrategy = strategy;
    }

    @Override
    public void setSeasonalStrategy(PricingStrategyAPI strategy) {
        this.seasonalStrategy = strategy;
    }

    // CONFIG SETTERS/GETTERS

    @Override
    public void setSeasonalDiscountPercent(double percent) {
        this.seasonalDiscountPercent = percent;
    }

    @Override
    public void setLoyaltyThreshold(int threshold) {
        this.loyaltyThreshold = threshold;
    }

    @Override
    public void setLoyaltyDiscountPercent(double percent) {
        this.loyaltyDiscountPercent = percent;
    }

    @Override
    public double getSeasonalDiscountPercent() {
        return seasonalDiscountPercent;
    }

    @Override
    public int getLoyaltyThreshold() {
        return loyaltyThreshold;
    }

    @Override
    public double getLoyaltyDiscountPercent() {
        return loyaltyDiscountPercent;
    }

    // CORE CALCULATION

    /**
     * Calculate final price for a booking.
     * Sequentially applies loyalty then seasonal adjustments.
     */
    @Override
    public double calculatePrice(Booking booking) {
        if (booking == null) return 0.0;

        // Determine TravelPackage
        TravelPackage pkg = booking.getTravelPackage();
        if (pkg == null && booking.getTravelItem() != null) {
            try {
                pkg = (TravelPackage) booking.getTravelItem();
            } catch (ClassCastException e) {
                pkg = null;
            }
        }

        // If no package and no travel item price available, return 0
        double basePrice = 0.0;
        if (pkg != null) {
            basePrice = pkg.getPrice();
        } else if (booking.getTravelItem() != null) {
            try {
                basePrice = booking.getTravelItem().getPrice();
            } catch (Exception ignored) {
                basePrice = 0.0;
            }
        } else {
            return 0.0;
        }

        // Base price step
        double priceAfterBase;
        if (baseStrategy != null) {
            try {
                priceAfterBase = baseStrategy.calculatePrice(booking);
            } catch (Exception e) {
                // fallback to raw base price on strategy failure
                priceAfterBase = basePrice;
            }
        } else {
            priceAfterBase = basePrice;
        }

        // Loyalty step
        double priceAfterLoyalty;
        if (loyaltyStrategy != null) {
            try {
                priceAfterLoyalty = loyaltyStrategy.calculatePrice(booking);
            } catch (Exception e) {
                priceAfterLoyalty = priceAfterBase;
            }
        } else {
            // Internal loyalty logic: if customer meets threshold, apply loyaltyDiscountPercent to the current price
            if (booking.getCustomer() != null && booking.getCustomer().getLoyaltyPoints() >= loyaltyThreshold) {
                priceAfterLoyalty = priceAfterBase * (1.0 - loyaltyDiscountPercent / 100.0);
            } else {
                priceAfterLoyalty = priceAfterBase;
            }
        }

        // Seasonal step
        double finalPrice;
        if (seasonalStrategy != null) {
            try {
                finalPrice = seasonalStrategy.calculatePrice(booking);
            } catch (Exception e) {
                finalPrice = priceAfterLoyalty;
            }
        } else {
            // Apply configured seasonalDiscountPercent to the price after loyalty
            finalPrice = priceAfterLoyalty * (1.0 - seasonalDiscountPercent / 100.0);
        }

        return finalPrice;
    }

   
    @Override
    public void printPriceBreakdown(Booking booking) {
        if (booking == null) {
            System.out.println("[PricingService] No booking provided.");
            return;
        }

        // Determine base price
        TravelPackage pkg = booking.getTravelPackage();
        if (pkg == null && booking.getTravelItem() != null) {
            try { pkg = (TravelPackage) booking.getTravelItem(); } catch (Exception ignored) { pkg = null; }
        }

        double basePrice = 0.0;
        if (pkg != null) basePrice = pkg.getPrice();
        else {
            try { basePrice = booking.getTravelItem().getPrice(); } catch (Exception ignored) { basePrice = 0.0; }
        }

        System.out.println("----- PRICE BREAKDOWN -----");
        System.out.println("Base price (from booking): $" + String.format("%.2f", basePrice));

        double afterBase = (baseStrategy != null) ? safeStrategyCall(baseStrategy, booking, basePrice) : basePrice;
        System.out.println("After base strategy: $" + String.format("%.2f", afterBase));

        double afterLoyalty = (loyaltyStrategy != null)
                ? safeStrategyCall(loyaltyStrategy, booking, afterBase)
                : ((booking.getCustomer() != null && booking.getCustomer().getLoyaltyPoints() >= loyaltyThreshold)
                    ? afterBase * (1.0 - loyaltyDiscountPercent / 100.0)
                    : afterBase);

        if (afterLoyalty != afterBase) {
            System.out.println("Loyalty applied (threshold=" + loyaltyThreshold + ", discount=" + loyaltyDiscountPercent + "%): $" + String.format("%.2f", afterLoyalty));
        } else {
            System.out.println("Loyalty not applied");
        }

        double afterSeason = (seasonalStrategy != null)
                ? safeStrategyCall(seasonalStrategy, booking, afterLoyalty)
                : afterLoyalty * (1.0 - seasonalDiscountPercent / 100.0);

        if (afterSeason != afterLoyalty) {
            System.out.println("Seasonal applied (discount=" + seasonalDiscountPercent + "%): $" + String.format("%.2f", afterSeason));
        } else {
            System.out.println("Seasonal not applied");
        }

        System.out.println("Final price: $" + String.format("%.2f", afterSeason));
        System.out.println("---------------------------");
    }

    // Helper to call a strategy and gracefully fallback to the provided fallbackPrice if it fails.
    private double safeStrategyCall(PricingStrategyAPI strategy, Booking booking, double fallbackPrice) {
        try {
            double stratPrice = strategy.calculatePrice(booking);
            // If a strategy returns zero or negative unexpectedly, use fallback
            if (stratPrice <= 0.0) return fallbackPrice;
            return stratPrice;
        } catch (Exception e) {
            return fallbackPrice;
        }
    }
}
