package travelease.flyweight;

import java.util.List;

public interface TravelCategoryFlyweightAPI {
    String getCategoryName();
    List<String> getAmenities();
    boolean isRefundable();

}
