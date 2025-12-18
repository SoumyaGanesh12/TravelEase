package travelease.factory.hotel;

import travelease.TravelItemAPI;
import travelease.flyweight.TravelCategoryFlyweightAPI;

public class BudgetHotel extends AbstractHotel {

    private final String hotelCategory; // intrinsic property

    public BudgetHotel(TravelCategoryFlyweightAPI categoryInfo, double price) {
        super(categoryInfo, price);
        this.hotelCategory = "Budget";  
    }

    public String getHotelCategory() {
        return hotelCategory;
    }

    @Override
    public String getDescription() {
        return hotelCategory + " - " + super.getDescription();
    }

    @Override
    public TravelItemAPI clone(){
        return (TravelItemAPI) super.clone();
    }
}
