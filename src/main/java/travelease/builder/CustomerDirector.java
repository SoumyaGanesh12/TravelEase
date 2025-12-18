package travelease.builder;
import travelease.Customer;

public class CustomerDirector {

    private CustomerBuilderAPI builder;

    public void setBuilder(CustomerBuilderAPI builder) {
        this.builder = builder;
    }

    public Customer createBasicCustomer(String name, String email, String phoneNumber) {
        return builder.reset()
                .setName(name)
                .setEmail(email)
                .setPhoneNumber(phoneNumber)
                .setLoyaltyPoints(0)
                .build();
    }

    public Customer createPremiumCustomer(String name, String email, String phoneNumber, int points) {
        return builder.reset()
                .setName(name)
                .setEmail(email)
                .setPhoneNumber(phoneNumber)
                .setLoyaltyPoints(points)
                .build();
    }
}
