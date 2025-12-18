package travelease.flyweight;

import java.util.HashMap;
import java.util.Map;

public class FlyweightSingletonFactory {
    private static FlyweightSingletonFactory instance;
    private Map<TravelCategoryType, TravelCategoryFlyweightAPI> flyweightMap;

    private FlyweightSingletonFactory() {
        flyweightMap = new HashMap<>();
    }

    public static synchronized FlyweightSingletonFactory getInstance() {
        if (instance == null) {
            instance = new FlyweightSingletonFactory();
        }
        return instance;
    }

    public TravelCategoryFlyweightAPI getFlyweight(String category) {
        TravelCategoryType type = TravelCategoryType.fromString(category);
        if (type == null) return null;
        return getFlyweight(type);
    }

    public TravelCategoryFlyweightAPI getFlyweight(TravelCategoryType category) {
        if (!flyweightMap.containsKey(category)) {
            flyweightMap.put(category, createCategory(category));
        }
        return flyweightMap.get(category);
    }

    private TravelCategoryFlyweightAPI createCategory(TravelCategoryType categoryType) {
        return new ConcreteTravelCategoryFlyweight(
                categoryType.getDisplayName(),
                categoryType.getAmenities(),
                categoryType.isRefundable()
        );
    }
}
