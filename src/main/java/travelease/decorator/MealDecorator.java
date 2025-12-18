package travelease.decorator;

import travelease.TravelItemAPI;

public class MealDecorator extends TravelItemDecorator {
    private final double mealCost = DecoratorPricingConfig.MEAL_COST;
    private final String mealDescription = "Meal is included";
    public MealDecorator(TravelItemAPI travelItem) {
        super(travelItem);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " | " + mealDescription;
    }

    @Override
    public double getPrice() {
        return super.getPrice() + mealCost;
    }

}
