package travelease.bridge.provider;

import java.util.List;

import travelease.TravelItemAPI;
import travelease.adapter.ExternalSearchAPI;


public class HotelProvider implements Provider {
    private final ExternalSearchAPI api;

    public HotelProvider(ExternalSearchAPI api) {
        this.api = api;
    }

    @Override
    public List<TravelItemAPI> find(String query) {
        return api.search(query);
    }
}
