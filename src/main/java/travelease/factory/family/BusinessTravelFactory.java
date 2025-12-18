package travelease.factory.family;

import travelease.flyweight.FlyweightSingletonFactory;
import travelease.flyweight.TravelCategoryFlyweightAPI;
import travelease.flyweight.TravelCategoryType;

// product imports
import travelease.factory.flight.BusinessFlight;
import travelease.factory.flight.AbstractFlight;

import travelease.factory.hotel.BusinessHotel;
import travelease.factory.hotel.AbstractHotel;

import travelease.factory.carrental.ExecutiveSedan;
import travelease.factory.carrental.AbstractCarRental;

/**
 * Concrete factory for "Business" family.
 * Produces BusinessFlight, BusinessHotel, ExecutiveSedan configured with the
 * "Business" flyweight.
 */
public class BusinessTravelFactory implements TravelAbstractFactoryAPI {

    private final FlyweightSingletonFactory flyweightFactory;

    public BusinessTravelFactory() {
        this.flyweightFactory = FlyweightSingletonFactory.getInstance();
    }

    @Override
    public AbstractFlight createFlight(double overridePrice) {
        TravelCategoryFlyweightAPI cat = flyweightFactory.getFlyweight(TravelCategoryType.BUSINESS_FLIGHT);
        return new BusinessFlight(cat, overridePrice);
    }

    @Override
    public AbstractHotel createHotel(double overridePrice) {
        TravelCategoryFlyweightAPI cat = flyweightFactory.getFlyweight(TravelCategoryType.BUSINESS_HOTEL);
        return new BusinessHotel(cat, overridePrice);
    }

    @Override
    public AbstractCarRental createCar(double overridePrice) {
        TravelCategoryFlyweightAPI cat = flyweightFactory.getFlyweight(TravelCategoryType.EXECUTIVE_SEDAN);
        return new ExecutiveSedan(cat, overridePrice);
    }
}
