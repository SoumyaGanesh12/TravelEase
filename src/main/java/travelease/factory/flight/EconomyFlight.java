
package travelease.factory.flight;
import travelease.TravelItemAPI;
import travelease.flyweight.TravelCategoryFlyweightAPI;

public class EconomyFlight extends AbstractFlight {
    protected String flightClass;
    public EconomyFlight(TravelCategoryFlyweightAPI categoryInfo, double price) {
        super(categoryInfo, price);
        this.flightClass = "Economy";  
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