package travelease.decorator;

import travelease.TravelItemAPI;

public class AddBagDecorator extends TravelItemDecorator {
    private final double bagCost = DecoratorPricingConfig.BAG_COST;
    private final String bagDescription = "Additional Bag is included";
    public AddBagDecorator(TravelItemAPI travelItem) {
        super(travelItem);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " | " + bagDescription;
    }

    @Override
    public double getPrice() {
        return super.getPrice() + bagCost;
    }

}
