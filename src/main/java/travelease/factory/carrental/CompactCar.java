package travelease.factory.carrental;

import travelease.TravelItemAPI;
import travelease.flyweight.TravelCategoryFlyweightAPI;

public class CompactCar extends AbstractCarRental {

    // Intrinsic, fixed type for this product
    private final String carType = "Compact Car";

    public CompactCar(TravelCategoryFlyweightAPI categoryInfo, double price) {
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
