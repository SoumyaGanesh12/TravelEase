package travelease.adapter.hotel;
import java.util.List;

import travelease.TravelItemAPI;
import travelease.adapter.ExternalSearchAPI;
import travelease.adapter.TravelItemConverter;

public class HotelAdapter implements ExternalSearchAPI {

    private final HotelLegacyAPI api = new HotelLegacyAPI();

    @Override
    public List<TravelItemAPI> search(String query) {
        // Query format: "city" (destination city)
        String city = query.split(",")[0].trim();
        
        System.out.println("[Adapter] Searching hotels in: " + city);
        List<String> legacyData = api.findHotels(city);
        return TravelItemConverter.fromLegacyStrings(legacyData, "Hotel");
    }
}