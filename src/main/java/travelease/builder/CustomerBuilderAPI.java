package travelease.builder;
import travelease.Customer;

public interface CustomerBuilderAPI {

    CustomerBuilderAPI reset();

    CustomerBuilderAPI setName(String name);
    CustomerBuilderAPI setEmail(String email);
    CustomerBuilderAPI setPhoneNumber(String number);
    CustomerBuilderAPI setCustomerID(String id);
    CustomerBuilderAPI setLoyaltyPoints(int points);

    Customer build();
}
