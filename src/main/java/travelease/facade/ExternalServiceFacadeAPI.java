package travelease.facade;

import java.util.List;

import travelease.TravelItemAPI;
import travelease.helper.SearchCriteria;

/**
 * Public API for External Service Facade.
 * This class wires together the entire flow:
 * Legacy APIs -> Adapters -> Providers -> Services -> Facade
 */

public interface ExternalServiceFacadeAPI {

    List<TravelItemAPI> searchAllFlights(SearchCriteria criteria);

    List<TravelItemAPI> searchAllHotels(SearchCriteria criteria);

    List<TravelItemAPI> searchAllCars(SearchCriteria criteria);

}