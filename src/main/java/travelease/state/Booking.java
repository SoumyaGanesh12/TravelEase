package travelease.state;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import travelease.Customer;
import travelease.TravelItemAPI;
import travelease.composite.TravelPackage;
import travelease.observer.ObserverAPI;

/**
 * State Pattern: Context class that maintains current state and delegates state-specific behavior.
 */
public class Booking {
    private final String id;
    private final Customer customer;
    private TravelPackage travelPackage;
    private BookingStateAPI state;
    private final List<ObserverAPI> observers;
    private final LocalDateTime createdAt;

    public Booking(String id, Customer customer) {
        this.id = id;
        this.customer = customer;
        this.travelPackage = null; // initially no package
        this.state = new BookingStateFactory().createPendingState(); // initial state
        this.observers = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
    }

    // State Handling
    public void setState(BookingStateAPI state) {
        this.state = state;
    }

    public void proceed() {
        state.proceed(this);
    }

    public void cancel() {
        state.cancel(this);
    }

    // Observer Handling
    public void attachObserver(ObserverAPI o) {
        observers.add(o);
    }

    public void detachObserver(ObserverAPI o) {
        observers.remove(o);
    }

    public void notifyObservers() {
        for (ObserverAPI o : observers) {
            o.update(this);
        }
    }

    // Getters & Setters
    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public TravelItemAPI getTravelItem() {
        return travelPackage;
    }

    public void setTravelItem(TravelItemAPI item) {
        if (item instanceof TravelPackage) {
            this.travelPackage = (TravelPackage) item;
        } else if (item != null) {
            // If it's a single item, wrap it in a package
            this.travelPackage = new TravelPackage(item);
        } else {
            this.travelPackage = null;
        }
    }

    public TravelPackage getTravelPackage() {
        return travelPackage;
    }

    public void setTravelPackage(TravelPackage travelPackage) {
        this.travelPackage = travelPackage;
    }

    public BookingStateAPI getState() {
        return state;
    }

    public List<ObserverAPI> getObservers() {
        return observers;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void replaceItem(TravelItemAPI oldItem, TravelItemAPI newItem) {
        if (travelPackage == null) return;

        List<TravelItemAPI> items = travelPackage.getItems();

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(oldItem.getId())) {
                items.set(i, newItem);
                return;
            }
        }

        throw new IllegalArgumentException("Target item not found in package.");
    }

}