package travelease.composite;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import travelease.TravelItemAPI;

public class TravelPackage implements TravelItemAPI {
    private final List<TravelItemAPI> items = new ArrayList<>();
    private int packageId;
    private String packageName;
    private final String id;

    public TravelPackage() {
        this.id = UUID.randomUUID().toString();
    }

    public TravelPackage(TravelItemAPI item) {
        this();
        this.addItem(item);
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDescription() {
        StringBuilder sb = new StringBuilder();
        if (packageName != null && !packageName.isEmpty()) {
            sb.append("Package Name: ").append(packageName);
            if (!items.isEmpty())
                sb.append(": ");
        }
        return sb.toString();
    }

    public TravelItemAPI getChild(int index) {
        return items.get(index);
    }

    public void addItem(TravelItemAPI item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add null item to package");
        }
        items.add(item);
    }

    public void removeItem(TravelItemAPI item) {
        items.remove(item);
    }

    public List<TravelItemAPI> getItems() {
        return items;
    }

    @Override
    public double getPrice() {
        double total = 0.0;
        for (TravelItemAPI item : items) {
            total += item.getPrice();
        }
        return total;
    }

    @Override
    public TravelPackage clone() {
        TravelPackage copy = new TravelPackage();
        copy.packageName = this.packageName;
        for (TravelItemAPI item : this.items) {
            copy.addItem(item.clone());
        }
        return copy;
    }

    @Override
    public String toString() {
        return "TravelPackage{id=" + id + ", name='" + packageName + "', items=" + items.size() + "}";
    }

    @Override
    public String getCategoryInfo() {
        return "Travel Package";
    }
}
