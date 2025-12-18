package travelease.factory.carrental;

import java.time.LocalDateTime;
import java.util.UUID;

import travelease.Schedulable;
import travelease.TravelItemAPI;
import travelease.flyweight.TravelCategoryFlyweightAPI;

/**
 * Base abstract class for all Car Rental products.
 *
 * Implements:
 * - TravelItemAPI    (shared interface for all travel items)
 * - Schedulable      (provides pickup and dropoff dates)
 *
 * Uses Flyweight:
 * - TravelCategoryFlyweightAPI (shared category metadata)
 */
public abstract class AbstractCarRental 
        implements TravelItemAPI, Schedulable {

    // --- Shared category metadata (Flyweight) ---
    protected final TravelCategoryFlyweightAPI categoryInfo;

    // --- Car rental–specific data ---
    protected String carName;              // From legacy API (e.g., "Toyota Corolla")
    protected String rentalCompany;
 
    protected String pickupLocation;        
    protected String dropoffLocation;

    protected LocalDateTime pickupDate;
    protected LocalDateTime dropoffDate;

    private final double price;
    private final String id = UUID.randomUUID().toString();

    // --- Constructor ---
    protected AbstractCarRental(TravelCategoryFlyweightAPI categoryInfo, double price) {
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
        String namePart = (carName != null && !carName.isEmpty()) ? carName + " " : "";
        String desc = "\n"+namePart + "Car from " + rentalCompany +
               " (Pickup: " + pickupLocation +
               ", Dropoff: " + dropoffLocation + ")";
        if (categoryInfo != null) {
            desc += " | Amenities: " + categoryInfo.getAmenities();
            desc += categoryInfo.isRefundable() ? " | Refundable" : " | Non-refundable";
        }
        return desc;
    }

    @Override
    public String getCategoryInfo() {
        return categoryInfo.getCategoryName();    // Delegation to Flyweight
    }

    // --- Schedulable ---
    @Override
    public LocalDateTime getStartDate() {
        return pickupDate;
    }

    @Override
    public LocalDateTime getEndDate() {
        return dropoffDate;
    }

    // --- Setters used by Factories/Builders ---

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public void setRentalCompany(String rentalCompany) {
        this.rentalCompany = rentalCompany;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public void setDropoffLocation(String dropoffLocation) {
        this.dropoffLocation = dropoffLocation;
    }

    public void setPickupDate(LocalDateTime pickupDate) {
        this.pickupDate = pickupDate;
    }

    public void setDropoffDate(LocalDateTime dropoffDate) {
        this.dropoffDate = dropoffDate;
    }
    @Override
    public TravelItemAPI clone(){
        try {
            return (TravelItemAPI) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
