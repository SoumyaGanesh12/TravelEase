package travelease.adapter;

import java.util.List;

import travelease.TravelItemAPI;

// adapter/ExternalSearchAPI.java
public interface ExternalSearchAPI {
    List<TravelItemAPI> search(String query);
}