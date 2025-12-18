package travelease.bridge.service;

import java.util.List;

import travelease.TravelItemAPI;
import travelease.bridge.provider.Provider;

// bridge/HotelBookingService.java
public class HotelBookingService extends BookingService {
    public HotelBookingService(Provider provider) {
        super(provider);
    }

    @Override
    public List<TravelItemAPI> search(String query) {
        return provider.find(query);
    }
}
