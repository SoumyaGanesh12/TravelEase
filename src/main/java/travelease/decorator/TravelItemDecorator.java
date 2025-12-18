package travelease.decorator;

import travelease.TravelItemAPI;

public abstract class TravelItemDecorator implements TravelItemAPI {
    protected TravelItemAPI travelItem;

    public TravelItemDecorator(TravelItemAPI travelItem) {
        this.travelItem = travelItem;
    }

    public TravelItemAPI getWrappedItem() {
        return this.travelItem;
    }

    @Override
    public String getId() {
        return travelItem.getId();
    }

    @Override
    public String getDescription() {
        return travelItem.getDescription();
    }

    @Override
    public double getPrice() {
        return travelItem.getPrice();
    }

    @Override
    public String getCategoryInfo() {
        return travelItem.getCategoryInfo();
    }

    @Override
    public TravelItemAPI clone() {
        // Intentionally not cloning the decorator instances.
        // Return the same decorator object so cloned packages will share the decorator.
        return this;
    }

}
