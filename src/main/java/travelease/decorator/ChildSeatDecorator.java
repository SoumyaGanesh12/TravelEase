package travelease.decorator;

import travelease.TravelItemAPI;

public class ChildSeatDecorator extends TravelItemDecorator {
    private final double seatCost = DecoratorPricingConfig.CHILD_SEAT_COST;
    private final String seatDescription = "Child seat is included";
    public ChildSeatDecorator(TravelItemAPI travelItem) {
        super(travelItem);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " | " + seatDescription;
    }

    @Override
    public double getPrice() {
        return super.getPrice() + seatCost;
    }

}
