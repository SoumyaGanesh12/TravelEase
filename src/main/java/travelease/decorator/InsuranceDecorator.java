package travelease.decorator;

import travelease.TravelItemAPI;

public class InsuranceDecorator extends TravelItemDecorator {
    private final double insuranceCost = DecoratorPricingConfig.INSURANCE_COST;
    private final String insuranceDescription = "Travel insurance is included";
    public InsuranceDecorator(TravelItemAPI travelItem) {
        super(travelItem);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " | " + insuranceDescription;
    }

    @Override
    public double getPrice() {
        return super.getPrice() + insuranceCost;
    }

}
