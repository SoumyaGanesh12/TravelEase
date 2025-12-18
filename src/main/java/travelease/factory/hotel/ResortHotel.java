package travelease.factory.hotel;

import travelease.TravelItemAPI;
import travelease.flyweight.TravelCategoryFlyweightAPI;

public class ResortHotel extends AbstractHotel {

    private final String hotelCategory; // intrinsic property

    public ResortHotel(TravelCategoryFlyweightAPI categoryInfo, double price) {
        super(categoryInfo, price);
        this.hotelCategory = "Resort";  
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
