package travelease.factory.family;

import travelease.flyweight.FlyweightSingletonFactory;
import travelease.flyweight.TravelCategoryFlyweightAPI;
import travelease.flyweight.TravelCategoryType;

import travelease.factory.flight.PremiumEconomyFlight;
import travelease.factory.flight.AbstractFlight;

import travelease.factory.hotel.ResortHotel;
import travelease.factory.hotel.AbstractHotel;

import travelease.factory.carrental.PremiumSUV;
import travelease.factory.carrental.AbstractCarRental;

/**
 * Factory for a leisure/resort family of products.
 */
public class LeisureTravelFactory implements TravelAbstractFactoryAPI {

    private final FlyweightSingletonFactory flyweightFactory;

    public LeisureTravelFactory() {
        this.flyweightFactory = FlyweightSingletonFactory.getInstance();
    }

    @Override
    public AbstractFlight createFlight(double overridePrice) {
        TravelCategoryFlyweightAPI cat = flyweightFactory.getFlyweight(TravelCategoryType.PREMIUM_ECONOMY_FLIGHT);
        return new PremiumEconomyFlight(cat, overridePrice);
    }

    @Override
    public AbstractHotel createHotel(double overridePrice) {
        TravelCategoryFlyweightAPI cat = flyweightFactory.getFlyweight(TravelCategoryType.RESORT_HOTEL);
        return new ResortHotel(cat, overridePrice);
    }

    @Override
    public AbstractCarRental createCar(double overridePrice) {
        TravelCategoryFlyweightAPI cat = flyweightFactory.getFlyweight(TravelCategoryType.PREMIUM_SUV);
        return new PremiumSUV(cat, overridePrice);
    }
}
