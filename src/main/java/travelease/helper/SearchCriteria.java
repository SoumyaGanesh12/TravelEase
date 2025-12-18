package travelease.helper;

import java.time.LocalDate;

/**
 * Helper Data Class - Encapsulates search parameters (location, dates, passengers) for travel item queries.
 */
public class SearchCriteria {
    private String origin;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private int passengers;

    // Constructors
    public SearchCriteria() {}
    
    public SearchCriteria(String origin, String destination, LocalDate start, LocalDate end, int passengers) {
        this.origin = origin;
        this.destination = destination;
        this.startDate = start;
        this.endDate = end;
        this.passengers = passengers;
    }

    // Getters & Setters
    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    @Override
    public String toString() {
        return String.format("Origin: %s, Destination: %s, Dates: %s to %s, Passengers: %d",
            origin != null ? origin : "N/A",
            destination != null ? destination : "N/A",
            startDate != null ? startDate.toString() : "N/A",
            endDate != null ? endDate.toString() : "N/A",
            passengers);
    }
}