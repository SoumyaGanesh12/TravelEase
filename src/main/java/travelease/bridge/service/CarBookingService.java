package travelease.bridge.service;

import java.util.List;

import travelease.TravelItemAPI;
import travelease.bridge.provider.Provider;

// bridge/CarBookingService.java
public class CarBookingService extends BookingService {
    public CarBookingService(Provider provider) {
        super(provider);
    }

    @Override
    public List<TravelItemAPI> search(String query) {
        return provider.find(query);
    }
}
