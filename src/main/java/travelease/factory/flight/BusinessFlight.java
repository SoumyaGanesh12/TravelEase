
package travelease.factory.flight;
import travelease.TravelItemAPI;
import travelease.flyweight.TravelCategoryFlyweightAPI;

public class BusinessFlight extends AbstractFlight {
    protected String flightClass;
    public BusinessFlight(TravelCategoryFlyweightAPI categoryInfo, double price) {
        super(categoryInfo, price);
        this.flightClass = "Business";  
    }

    public String getFlightClass() {
     return flightClass;
}

    @Override
    public String getDescription() {
        return flightClass + " - " + super.getDescription();
    }

    @Override
    public TravelItemAPI clone(){
        return (TravelItemAPI) super.clone();
    }
}