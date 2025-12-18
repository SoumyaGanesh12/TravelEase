package travelease.decorator;

import travelease.TravelItemAPI;

public class SuiteUpgradeDecorator extends TravelItemDecorator {
    private final double upgradeCost = DecoratorPricingConfig.SUITE_UPGRADE_COST;
    private final String upgradeDescription = "Upgraded to Suite";
    public SuiteUpgradeDecorator(TravelItemAPI travelItem) {
        super(travelItem);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " | " + upgradeDescription;
    }

    @Override
    public double getPrice() {
        return super.getPrice() + upgradeCost;
    }


}
