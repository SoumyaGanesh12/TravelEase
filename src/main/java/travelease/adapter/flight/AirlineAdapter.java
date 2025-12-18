package travelease.adapter.flight;
import java.util.List;

import travelease.TravelItemAPI;
import travelease.adapter.ExternalSearchAPI;
import travelease.adapter.TravelItemConverter;

public class AirlineAdapter implements ExternalSearchAPI {

    private final AirlineLegacyAPI api = new AirlineLegacyAPI();

    @Override
    public List<TravelItemAPI> search(String query) {
        // Query format: "origin,destination"
        String[] parts = query.split(",");
        String origin = parts[0].trim();
        String destination = parts.length > 1 ? parts[1].trim() : "New York";
        
        System.out.println("[Adapter] Searching flights: " + origin + " → " + destination);
        List<String> legacyData = api.findFlights(origin, destination);
        return TravelItemConverter.fromLegacyStrings(legacyData, "Flight");
    }
}