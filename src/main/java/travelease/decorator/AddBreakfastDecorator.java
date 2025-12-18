package travelease.decorator;

import travelease.TravelItemAPI;

public class AddBreakfastDecorator extends TravelItemDecorator {
    private final double breakfastCost = DecoratorPricingConfig.BREAKFAST_COST;
    private final String breakfastDescription = "Breakfast is included";
    public AddBreakfastDecorator(TravelItemAPI travelItem) {
        super(travelItem);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " | " + breakfastDescription;
    }

    @Override
    public double getPrice() {
        return super.getPrice() +breakfastCost;
    }

}
