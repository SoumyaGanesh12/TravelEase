package travelease.adapter.car;
import java.util.List;

import travelease.TravelItemAPI;
import travelease.adapter.ExternalSearchAPI;
import travelease.adapter.TravelItemConverter;

public class CarAdapter implements ExternalSearchAPI {

    private final CarLegacyAPI api = new CarLegacyAPI();

    @Override
    public List<TravelItemAPI> search(String query) {
        // Query format: "city" (destination city)
        String city = query.split(",")[0].trim();
        
        System.out.println("[Adapter] Searching cars in: " + city);
        List<String> legacyData = api.findCars(city);
        return TravelItemConverter.fromLegacyStrings(legacyData, "Car");
    }
}