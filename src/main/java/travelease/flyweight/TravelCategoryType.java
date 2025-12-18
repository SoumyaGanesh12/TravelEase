package travelease.flyweight;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public enum TravelCategoryType {
    BUSINESS_FLIGHT("business-flight", "Business Flight", 
        Arrays.asList("Extra Legroom", "Priority Boarding", "Lounge Access"), true),
    
    ECONOMY_FLIGHT("economy-flight", "Economy Flight",
        Arrays.asList("Standard Seat"), false),
    
    PREMIUM_ECONOMY_FLIGHT("premium-economy-flight", "Premium Economy Flight",
        Arrays.asList("Extra Legroom", "Complimentary Wifi"), true),
    
    BUDGET_HOTEL("budget-hotel", "Budget Hotel Room",
        Arrays.asList("Basic Room"), false),
    
    BUSINESS_HOTEL("business-hotel", "Business Hotel Room",
        Arrays.asList("Extra-space Room", "Special Dining", "Lounge Access"), true),
    
    RESORT_HOTEL("resort-hotel", "Resort Suite Room",
        Arrays.asList("Luxury Room", "Special Dining", "Spa Access"), true),
    
    EXECUTIVE_SEDAN("executive-sedan", "Executive Sedan Car",
        Arrays.asList("Auto Climate Control", "Leather Seats", "Premium Sound System"), true),
    
    PREMIUM_SUV("premium-suv", "Premium SUV Car",
        Arrays.asList("All-Wheel Drive", "Leather Seats", "Advanced Safety Features"), true),
    
    COMPACT("compact-car", "Compact Car",
        Arrays.asList("Fuel Efficient", "Compact Size", "Basic Features"), false);

    private final String key;
    private final String displayName;
    private final List<String> amenities;
    private final boolean refundable;

    TravelCategoryType(String key, String displayName, List<String> amenities, boolean refundable) {
        this.key = key;
        this.displayName = displayName;
        this.amenities = amenities;
        this.refundable = refundable;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<String> getAmenities() {
        return amenities;
    }

    public boolean isRefundable() {
        return refundable;
    }

    @Override
    public String toString() {
        return key;
    }

    public static TravelCategoryType fromString(String input) {
        if (input == null) return null;
        String normalized = input.trim().toLowerCase(Locale.ROOT)
                .replace('_', '-')
                .replace(' ', '-');
        for (TravelCategoryType t : values()) {
            if (t.key.equalsIgnoreCase(normalized)) return t;
        }
        return null;
    }
}
