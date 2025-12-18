package travelease.factory.hotel;

import travelease.TravelItemAPI;
import travelease.flyweight.TravelCategoryFlyweightAPI;

public class BusinessHotel extends AbstractHotel {

    private final String hotelCategory; // intrinsic property

    public BusinessHotel(TravelCategoryFlyweightAPI categoryInfo, double price) {
        super(categoryInfo, price);
        this.hotelCategory = "Business";  
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
