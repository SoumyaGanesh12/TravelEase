package travelease.builder;

import travelease.TravelItemAPI;
import travelease.composite.TravelPackage;

/**
 * Builder implementation focused on assembling custom travel packages.
 */
public class CustomTravelPackageBuilder implements TravelPackageBuilderAPI {

    private TravelPackage travelPackage;

    public CustomTravelPackageBuilder() {
        reset();
    }

    @Override
    public TravelPackageBuilderAPI reset() {
        this.travelPackage = new TravelPackage();
        return this;
    }

    @Override
    public TravelPackageBuilderAPI addFlight(TravelItemAPI flightItem) {
        addItemIfPresent(flightItem);
        return this;
    }

    @Override
    public TravelPackageBuilderAPI addHotel(TravelItemAPI hotelItem) {
        addItemIfPresent(hotelItem);
        return this;
    }

    @Override
    public TravelPackageBuilderAPI addCarRental(TravelItemAPI carItem) {
        addItemIfPresent(carItem);
        return this;
    }

    @Override
    public TravelPackage build() {
        TravelPackage result = this.travelPackage.clone();
        reset();
        return result;
    }

    // ---- Helpers for package metadata ----

    public CustomTravelPackageBuilder setPackageId(int packageId) {
        this.travelPackage.setPackageId(packageId);
        return this;
    }

    public CustomTravelPackageBuilder setPackageName(String packageName) {
        this.travelPackage.setPackageName(packageName);
        return this;
    }

    /**
     * Safely adds an item to the package if it is not null.
     * This method acts as a robustness feature by silently ignoring null inputs,
     * allowing the builder to handle partial configurations without implementation
     * errors.
     *
     * @param item The travel item to add; if null, no action is taken.
     */
    private void addItemIfPresent(TravelItemAPI item) {
        if (item != null) {
            this.travelPackage.addItem(item);
        }
    }
}
