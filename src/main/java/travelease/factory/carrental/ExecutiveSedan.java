package travelease.factory.carrental;

import travelease.TravelItemAPI;
import travelease.flyweight.TravelCategoryFlyweightAPI;

public class ExecutiveSedan extends AbstractCarRental {

    // Intrinsic, fixed type for this product
    private final String carType = "Executive Sedan";

    public ExecutiveSedan(TravelCategoryFlyweightAPI categoryInfo, double price) {
        super(categoryInfo, price);
    }

    @Override
    public String getDescription() {
        return carType + " - " + super.getDescription();
    }

    @Override
    public TravelItemAPI clone(){
        return (TravelItemAPI) super.clone();
    }
}
