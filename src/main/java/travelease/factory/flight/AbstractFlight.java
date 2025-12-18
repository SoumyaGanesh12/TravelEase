package travelease.factory.flight;

import java.time.LocalDateTime;
import java.util.UUID;

import travelease.Schedulable;
import travelease.TravelItemAPI;
import travelease.flyweight.TravelCategoryFlyweightAPI;

/**
 * Base abstract class for all Flight products.
 *
 * Implements:
 * - TravelItemAPI (common interface for all travel items)
 * - Schedulable (provides start/end times)
 *
 * Uses Flyweight:
 * - TravelCategoryFlyweightAPI (shared category metadata)
 */
public abstract class AbstractFlight
        implements TravelItemAPI, Schedulable {

    // --- Shared category metadata (Flyweight) ---
    protected final TravelCategoryFlyweightAPI categoryInfo;

    // --- Flight-specific data ---
    protected String flightName; // From legacy API (e.g., "American Airlines Express")
    protected String flightNumber;
    protected String origin;
    protected String destination;

    protected LocalDateTime departureTime;
    protected LocalDateTime arrivalTime;

    protected String seatAssignment;
    private final double price;
    private final String id = UUID.randomUUID().toString();

    // --- Constructor ---
    protected AbstractFlight(TravelCategoryFlyweightAPI categoryInfo, double price) {
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
        String namePart = (flightName != null && !flightName.isEmpty()) ? flightName + " " : "";
        String seatPart = (seatAssignment != null && !seatAssignment.isEmpty()) ? " (" + seatAssignment + ")" : "";
        String desc = "\n" + namePart + "Flight " + flightNumber + seatPart +
                " from " + origin +
                " to " + destination;
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
        return departureTime;
    }

    @Override
    public LocalDateTime getEndDate() {
        return arrivalTime;
    }

    // --- Flight-specific setters (to be used by Builders or Factories) ---

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setSeatAssignment(String seatAssignment) {
        this.seatAssignment = seatAssignment;
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
