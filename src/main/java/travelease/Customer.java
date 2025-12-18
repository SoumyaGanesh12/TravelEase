package travelease;

public class Customer extends Person {
    private String customerID;
    private int loyaltyPoints;

    public String getCustomerID() { return customerID; }
    public int getLoyaltyPoints() { return loyaltyPoints; }

    public void setCustomerID(String id) { this.customerID = id; }
    public void setLoyaltyPoints(int p) { this.loyaltyPoints = p; }
}