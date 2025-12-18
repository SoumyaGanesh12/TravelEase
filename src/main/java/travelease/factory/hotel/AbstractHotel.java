package travelease.factory.hotel;

import java.time.LocalDateTime;
import java.util.UUID;

import travelease.Schedulable;
import travelease.TravelItemAPI;
import travelease.flyweight.TravelCategoryFlyweightAPI;

/**
 * Base abstract class for all Hotel products.
 *
 * Implements:
 * - TravelItemAPI (common interface for all travel items)
 * - Schedulable (provides check-in and check-out dates)
 *
 * Uses Flyweight:
 * - TravelCategoryFlyweightAPI (shared category metadata)
 */
public abstract class AbstractHotel
        implements TravelItemAPI, Schedulable {

    // --- Shared category metadata (Flyweight) ---
    protected final TravelCategoryFlyweightAPI categoryInfo;

    // --- Hotel-specific data ---
    protected String hotelName;
    protected String location;

    protected int roomCount;

    protected LocalDateTime checkInDate;
    protected LocalDateTime checkOutDate;

    private final double price;
    private final String id = UUID.randomUUID().toString();

    // --- Constructor ---
    protected AbstractHotel(TravelCategoryFlyweightAPI categoryInfo, double price) {
        this.categoryInfo = categoryInfo;
        this.price = price;
    }

    // --- TravelItemAPI ---
    @Override
    public String getId() {
        return id;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getDescription() {
        String desc = "\n Hotel stay at " + hotelName +
                " (" + roomCount + " rooms)" +
                " in " + location;
        if (categoryInfo != null) {
            desc += " | Amenities: " + categoryInfo.getAmenities();
            desc += categoryInfo.isRefundable() ? " | Refundable" : " | Non-refundable";
        }
        return desc;
    }

    @Override
    public String getCategoryInfo() {
        return categoryInfo.getCategoryName(); // Delegation to Flyweight
    }

    // --- Schedulable ---
    @Override
    public LocalDateTime getStartDate() {
        return checkInDate;
    }

    @Override
    public LocalDateTime getEndDate() {
        return checkOutDate;
    }

    // --- Hotel-specific setters (used by Builders/Factories) ---
    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public void setCheckInDate(LocalDateTime checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(LocalDateTime checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    @Override
    public TravelItemAPI clone() {
        try {
            return (TravelItemAPI) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
