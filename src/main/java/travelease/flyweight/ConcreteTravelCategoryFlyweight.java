package travelease.flyweight;

import java.util.Collections;
import java.util.List;

public class ConcreteTravelCategoryFlyweight implements TravelCategoryFlyweightAPI {
    private final String categoryName;
    private final List<String> amenities;
    private final boolean isRefundable;

    public ConcreteTravelCategoryFlyweight(String categoryName,List<String> amenities,boolean isRefundable) {
        this.categoryName = categoryName;
        this.amenities = Collections.unmodifiableList(amenities);
        this.isRefundable = isRefundable;

    }
    @Override
    public String getCategoryName() {
        return categoryName;
    }

    @Override
    public List<String> getAmenities() {
        return amenities;
    }

    @Override
    public boolean isRefundable() {
        return isRefundable;
    }

}
