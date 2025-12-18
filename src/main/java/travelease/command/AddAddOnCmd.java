package travelease.command;

import java.util.List;

import travelease.TravelItemAPI;
import travelease.decorator.AddBagDecorator;
import travelease.decorator.AddBreakfastDecorator;
import travelease.decorator.ChildSeatDecorator;
import travelease.decorator.InsuranceDecorator;
import travelease.decorator.MealDecorator;
import travelease.decorator.RoadsideAssitanceDecorator;
import travelease.decorator.SuiteUpgradeDecorator;
import travelease.state.Booking;

/**
 * Command Pattern: Encapsulates adding decorators (add-ons) to travel items with undo support.
 */
public class AddAddOnCmd implements CommandAPI {

    private final Booking booking;
    private final TravelItemAPI targetItem; // item to decorate
    private TravelItemAPI previousState; // original package/item
    private TravelItemAPI decoratedItem; // decorated item after execution
    private final String addOnChoice;

    public AddAddOnCmd(Booking booking, TravelItemAPI targetItem,
            String addOnChoice) {
        this.booking = booking;
        this.targetItem = targetItem;
        this.addOnChoice = addOnChoice;
    }

    @Override
    public void execute() {
        try {
            // Save deep clone of package for undo
            previousState = booking.getTravelPackage().clone();

            // Find the current item in package by ID
            TravelItemAPI current = null;
            List<TravelItemAPI> items = booking.getTravelPackage().getItems();
            for (TravelItemAPI item : items) {
                if (item.getId().equals(targetItem.getId())) {
                    current = item;
                    break;
                }
            }

            if (current == null) {
                throw new IllegalArgumentException("Target item not found in package: " + targetItem.getId());
            }

            // Wrap the current item (which may already have decorators) with the new decorator
            switch (addOnChoice) {
                case "BAG":
                    current = new AddBagDecorator(current);
                    break;
                case "BREAKFAST":
                    current = new AddBreakfastDecorator(current);
                    break;
                case "CHILD_SEAT":
                    current = new ChildSeatDecorator(current);
                    break;
                case "INSURANCE":
                    current = new InsuranceDecorator(current);
                    break;
                case "MEAL":
                    current = new MealDecorator(current);
                    break;
                case "ROADSIDE_ASSISTANCE":
                    current = new RoadsideAssitanceDecorator(current);
                    break;
                case "SUITE_UPGRADE":
                    current = new SuiteUpgradeDecorator(current);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid Add-On Choice: " + addOnChoice);
            }

            // Save final decorated item
            decoratedItem = current;

            // Replace target item with decorated version in package
            booking.replaceItem(targetItem, decoratedItem);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create decorator: " + e.getMessage());
        }
    }

    @Override
    public void undo() {
        // Restore previous package state
        booking.setTravelItem(previousState);
    }
}
