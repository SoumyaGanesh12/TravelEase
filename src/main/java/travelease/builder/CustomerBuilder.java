package travelease.builder;
import travelease.Customer;
public class CustomerBuilder implements CustomerBuilderAPI {

    protected Customer customer = new Customer();

    @Override
    public CustomerBuilderAPI reset() {
        customer = new Customer();
        return this;
    }

    @Override
    public CustomerBuilderAPI setName(String name) {
        customer.setName(name);
        return this;
    }

    @Override
    public CustomerBuilderAPI setEmail(String email) {
        customer.setEmail(email);
        return this;
    }

    @Override
    public CustomerBuilderAPI setPhoneNumber(String number) {
        customer.setPhoneNumber(number);
        return this;
    }

    @Override
    public CustomerBuilderAPI setCustomerID(String id) {
        customer.setCustomerID(id);
        return this;
    }

    @Override
    public CustomerBuilderAPI setLoyaltyPoints(int points) {
        customer.setLoyaltyPoints(points);
        return this;
    }

    @Override
    public Customer build() {
        return customer;
    }
}
