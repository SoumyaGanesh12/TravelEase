package travelease.bridge.service;

import java.util.List;

import travelease.TravelItemAPI;
import travelease.bridge.provider.Provider;

// bridge/BookingService.java
public abstract class BookingService {
    // bridge to different providers
    protected Provider provider;

    public BookingService(Provider provider) {
        this.provider = provider;
    }

    public abstract List<TravelItemAPI> search(String query);
}
