package travelease.bridge.provider;


import java.util.List;

import travelease.TravelItemAPI;
import travelease.adapter.ExternalSearchAPI;


// bridge/AirlineProvider.java
public class FlightProvider implements Provider {
    private final ExternalSearchAPI api;

    public FlightProvider(ExternalSearchAPI api) {
        this.api = api;
    }

    @Override
    public List<TravelItemAPI> find(String query) {
        return api.search(query);
    }
}
