package travelease.decorator;

import travelease.TravelItemAPI;

public class RoadsideAssitanceDecorator extends TravelItemDecorator {
    private final double assitanceCost = DecoratorPricingConfig.ROADSIDE_ASSISTANCE_COST;
    private final String assistanceDescription = "Roadside Assistance is included";
    public RoadsideAssitanceDecorator(TravelItemAPI travelItem) {
        super(travelItem);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " | " + assistanceDescription;
    }

    @Override
    public double getPrice() {
        return super.getPrice() + assitanceCost;
    }

}
