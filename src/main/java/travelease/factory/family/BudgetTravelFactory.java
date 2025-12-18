package travelease.factory.family;

import travelease.flyweight.FlyweightSingletonFactory;
import travelease.flyweight.TravelCategoryFlyweightAPI;

import travelease.factory.flight.EconomyFlight;
import travelease.factory.flight.AbstractFlight;

import travelease.factory.hotel.BudgetHotel;
import travelease.factory.hotel.AbstractHotel;

import travelease.factory.carrental.CompactCar;
import travelease.factory.carrental.AbstractCarRental;

import travelease.flyweight.TravelCategoryType;

/**
 * Factory for budget travel product family.
 */
public class BudgetTravelFactory implements TravelAbstractFactoryAPI {

    private final FlyweightSingletonFactory flyweightFactory;

    public BudgetTravelFactory() {
        this.flyweightFactory = FlyweightSingletonFactory.getInstance();
    }

    @Override
    public AbstractFlight createFlight(double overridePrice) {
        TravelCategoryFlyweightAPI cat = flyweightFactory.getFlyweight(TravelCategoryType.ECONOMY_FLIGHT);
        return new EconomyFlight(cat, overridePrice);
    }

    @Override
    public AbstractHotel createHotel(double overridePrice) {
        TravelCategoryFlyweightAPI cat = flyweightFactory.getFlyweight(TravelCategoryType.BUDGET_HOTEL);
        return new BudgetHotel(cat, overridePrice);
    }

    @Override
    public AbstractCarRental createCar(double overridePrice) {
        TravelCategoryFlyweightAPI cat = flyweightFactory.getFlyweight(TravelCategoryType.COMPACT);
        return new CompactCar(cat, overridePrice);
    }
}
