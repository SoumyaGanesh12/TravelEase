
package travelease.factory.flight;
import travelease.TravelItemAPI;
import travelease.flyweight.TravelCategoryFlyweightAPI;

public class PremiumEconomyFlight extends AbstractFlight {
    protected String flightClass;
    public PremiumEconomyFlight(TravelCategoryFlyweightAPI categoryInfo, double price) {
        super(categoryInfo, price);
        this.flightClass = "PremiumEconomy";  
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