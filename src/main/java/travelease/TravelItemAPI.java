package travelease;

/**
 * TravelItemAPI represents the core Component interface and defines the minimal set of operations common to all travel items
 * (e.g., Flights, Hotels, Car Rentals, Decorators).
 * 
**/
public interface TravelItemAPI extends Cloneable {

    String getId();

    double getPrice();

    String getDescription();

    String getCategoryInfo(); 
    
    TravelItemAPI clone();
}
