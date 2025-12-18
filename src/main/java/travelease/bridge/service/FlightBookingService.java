package travelease.bridge.service;

import java.util.List;

import travelease.TravelItemAPI;
import travelease.bridge.provider.Provider;

// bridge/FlightBookingService.java
public class FlightBookingService extends BookingService {
    public FlightBookingService(Provider provider) {
        super(provider);
    }

    @Override
    public List<TravelItemAPI> search(String query) {
        return provider.find(query);
    }
}
