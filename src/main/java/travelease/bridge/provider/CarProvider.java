package travelease.bridge.provider;

import java.util.List;

import travelease.TravelItemAPI;
import travelease.adapter.ExternalSearchAPI;


// bridge/CarProvider.java
public class CarProvider implements Provider {
    private final ExternalSearchAPI api;

    public CarProvider(ExternalSearchAPI api) {
        this.api = api;
    }

    @Override
    public List<TravelItemAPI> find(String query) {
        return api.search(query);
    }
}