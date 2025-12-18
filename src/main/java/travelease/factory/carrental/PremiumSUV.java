package travelease.factory.carrental;

import travelease.TravelItemAPI;
import travelease.flyweight.TravelCategoryFlyweightAPI;

public class PremiumSUV extends AbstractCarRental {

    // Intrinsic, fixed type for this product
    private final String carType = "Premium SUV";

    public PremiumSUV(TravelCategoryFlyweightAPI categoryInfo, double price) {
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
