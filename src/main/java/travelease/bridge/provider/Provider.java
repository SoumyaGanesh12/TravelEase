package travelease.bridge.provider;

import java.util.List;

import travelease.TravelItemAPI;

// bridge/Provider.java
public interface Provider {
    List<TravelItemAPI> find(String query);
}
