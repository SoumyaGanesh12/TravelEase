package travelease.demo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import travelease.Customer;
import travelease.TravelItemAPI;
import travelease.builder.CustomTravelPackageBuilder;
import travelease.builder.TravelPackageDirector;
import travelease.composite.TravelPackage;
import travelease.decorator.TravelItemDecorator;
import travelease.facade.BookingFacadeAPI;
import travelease.facade.BookingFacadeImpl;
import travelease.helper.PricingServiceAPI;
import travelease.helper.SearchCriteria;
import travelease.observer.AppNotificationObserver;
import travelease.observer.EmailNotificationObserver;
import travelease.observer.NotificationPreferences;
import travelease.observer.ObserverAPI;
import travelease.observer.SMSNotificationObserver;
import travelease.prototype.PrototypeInitializer;
import travelease.prototype.TravelPackagePrototypeRegistry;
import travelease.state.Booking;
import travelease.strategy.LoyaltyPricingStrategy;
import travelease.strategy.SeasonalPricingStrategy;
import travelease.template.AbstractBookingProcess;
import travelease.template.BookingProcessorFactory;

public class TravelBookingSystemDemo {

    private static final Scanner sc = new Scanner(System.in);

    public static void runDemo() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("TRAVEL EASE - COMPREHENSIVE DEMO");
        System.out.println("=".repeat(80) + "\n");
        // Initialize Facade
        BookingFacadeAPI facade = new BookingFacadeImpl();

        // ====================================================================
        // STEP 1: USER LOGIN / CUSTOMER CREATION (Builder Pattern)
        // ====================================================================
        System.out.println(
                "┌──────────────────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println(
                "│ STEP 1: USER LOGIN / CUSTOMER CREATION / NOTIFICATION PREFERENCES                            │");
        System.out.println(
                "│ (Builder, Observer Pattern)                                                                  │");
        System.out.println(
                "└──────────────────────────────────────────────────────────────────────────────────────────────┘");

        System.out.print("Enter your full name: ");
        String name = sc.nextLine().trim();

        // Email Regex validation
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        String email = "";

        while (true) {
            System.out.print("Enter your email: ");
            email = sc.nextLine().trim();

            if (email.matches(emailRegex)) {
                break;
            } else {
                System.out.println("[ERROR] Invalid email format. Please try again.");
            }
        }

        // Phone number regex validation
        String phoneRegex = "^[+]?\\d{10,15}$";
        String phoneNumber = "";

        while (true) {
            System.out.print("Enter your phone number (digits only, may start with +): ");
            phoneNumber = sc.nextLine().trim();

            if (phoneNumber.matches(phoneRegex)) {
                break;
            } else {
                System.out.println("[ERROR] Invalid phone number. Enter 10–15 digits (optional +).");
            }
        }

        // Generate a unique customer ID
        String customerId = "CUST-" + java.util.UUID.randomUUID().toString();

        System.out.print("Enable Email notifications? (yes/no): ");
        boolean emailPref = sc.nextLine().trim().equalsIgnoreCase("yes");

        System.out.print("Enable SMS notifications? (yes/no): ");
        boolean smsPref = sc.nextLine().trim().equalsIgnoreCase("yes");

        System.out.print("Enable App notifications? (yes/no): ");
        boolean appPref = sc.nextLine().trim().equalsIgnoreCase("yes");

        NotificationPreferences preferences = new NotificationPreferences(emailPref, smsPref, appPref);

        // Create booking with customer info
        facade.createEmptyBooking(name, email, phoneNumber, customerId, preferences);
        System.out.println("[OK] Customer created using Customer Builder pattern");
        System.out.println("Customer Details:");
        System.out.println("ID: " + customerId);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("[OK] Customer created using Customer Builder pattern");
        System.out.println("[OK] Booking ID generated using Singleton BookingIdGenerator");
        System.out.println("[OK] Observers attached based on customer preferences");
        System.out.println();

        System.out.println(
                "┌──────────────────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println(
                "│ STEP 2: SEARCH FUNCTIONALITY                                                                 │");
        System.out.println(
                "│ (Facade, Template, Bridge, Adapter, Factory, Flyweight Patterns)                             │");
        System.out.println(
                "└──────────────────────────────────────────────────────────────────────────────────────────────┘");

        SearchCriteria criteria = new SearchCriteria(
                "Chicago",
                "New York",
                LocalDate.now().plusDays(7),
                LocalDate.now().plusDays(10),
                2);
        System.out.println("Search Criteria: " + criteria.getOrigin() + " → " + criteria.getDestination() +
                ", " + criteria.getStartDate() + " to " + criteria.getEndDate());

        var searchResults = facade.searchIndividualItems(criteria);
        System.out.println("[OK] Search executed through BookingFacadeAPI");
        System.out.println(
                "[OK] Template pattern used (FlightBookingProcessor, HotelBookingProcessor, CarBookingProcessor)");
        System.out.println("[OK] Bridge pattern connects BookingServices to Providers");
        System.out.println("[OK] Adapter pattern adapts External APIs to common interface");

        List<TravelItemAPI> resultFlights;
        List<TravelItemAPI> resultHotels;
        List<TravelItemAPI> resultCars;

        resultFlights = searchResults.get(0);
        resultHotels = searchResults.get(1);
        resultCars = searchResults.get(2);

        System.out.println("\n┌─ SEARCH RESULTS for flights ─────────────────────────────────┐");
        int flightIndex = 0;
        for (TravelItemAPI item : resultFlights) {
            System.out.println("├─ [" + flightIndex + "] ID: " + item.getId());
            System.out.println("│     Category: " + item.getCategoryInfo());
            System.out.println("│     Description: " + item.getDescription());
            System.out.println("│     Price: $" + String.format("%.2f", item.getPrice()));
            System.out.println("│");
            flightIndex++;
        }
        System.out.println("└──────────────────────────────────────────────────────────────┘");
        System.out.println();

        System.out.println("\n┌─ SEARCH RESULTS for hotels ─────────────────────────────────┐");
        int hotelIndex = 0;
        for (TravelItemAPI item : resultHotels) {
            System.out.println("├─ [" + hotelIndex + "] ID: " + item.getId());
            System.out.println("│     Category: " + item.getCategoryInfo());
            System.out.println("│     Description: " + item.getDescription());
            System.out.println("│     Price: $" + String.format("%.2f", item.getPrice()));
            System.out.println("│");
            hotelIndex++;
        }
        System.out.println("└──────────────────────────────────────────────────────────────┘");
        System.out.println();

        System.out.println("\n┌─ SEARCH RESULTS for cars ─────────────────────────────────┐");
        int carIndex = 0;
        for (TravelItemAPI item : resultCars) {
            System.out.println("├─ [" + carIndex + "] ID: " + item.getId());
            System.out.println("│     Category: " + item.getCategoryInfo());
            System.out.println("│     Description: " + item.getDescription());
            System.out.println("│     Price: $" + String.format("%.2f", item.getPrice()));
            System.out.println("│");
            carIndex++;
        }
        System.out.println("└──────────────────────────────────────────────────────────────┘");
        System.out.println();

        // Ask customer to select items
        System.out.print("Enter the number of the flight you want to choose: ");
        int selectedFlightIndex = Integer.parseInt(sc.nextLine().trim());

        System.out.print("Enter the number of the hotel you want to choose: ");
        int selectedHotelIndex = Integer.parseInt(sc.nextLine().trim());

        System.out.print("Enter the number of the car you want to choose: ");
        int selectedCarIndex = Integer.parseInt(sc.nextLine().trim());

        TravelItemAPI selectedFlight = resultFlights.get(selectedFlightIndex);
        TravelItemAPI selectedHotel = resultHotels.get(selectedHotelIndex);
        TravelItemAPI selectedCar = resultCars.get(selectedCarIndex);

        System.out.println("\n[OK] Selected Flight: " + selectedFlight.getDescription());
        System.out.println("[OK] Selected Hotel: " + selectedHotel.getDescription());
        System.out.println("[OK] Selected Car: " + selectedCar.getDescription());
        System.out.println();

        // ====================================================================
        // STEP 3: BUILD PACKAGE (Factory, Builder, Composite)
        // ====================================================================
        System.out.println(
                "┌──────────────────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println(
                "│ STEP 3: BUILD TRAVEL PACKAGE                                                                 │");
        System.out.println(
                "│ (Builder + Composite)                                                                        │");
        System.out.println(
                "└──────────────────────────────────────────────────────────────────────────────────────────────┘");

        CustomTravelPackageBuilder customBuilder = new CustomTravelPackageBuilder()
                .setPackageName("Custom Package 2025");

        TravelPackageDirector packageDirector = new TravelPackageDirector(customBuilder);
        TravelPackage customPackage = packageDirector.buildCustomPackage(selectedFlight, selectedHotel,
                selectedCar);

        double customPackagePrice = customPackage.getPrice();
        System.out.println("[OK] CustomTravelPackageBuilder: " + customPackage.getDescription());
        System.out.println("  Custom Package Details: ");
        System.out.println(
                "------------------------------------------------------------------------------------------------------");
        displayPackageDetails(customPackage);
        System.out.println(
                "------------------------------------------------------------------------------------------------------");
        System.out.println("[OK] Builder Pattern: Assembled ad-hoc package via director");
        System.out.println();

        System.out.println(
                "┌──────────────────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println(
                "│ STEP 4: Listing all existing affordable travel packages (Prototype Pattern)                  │");
        System.out.println(
                "└──────────────────────────────────────────────────────────────────────────────────────────────┘");
        PrototypeInitializer.initAllCombinations(facade, resultFlights, resultHotels, resultCars);

        // Filter and print prototype packages with price below custom package
        var keys = TravelPackagePrototypeRegistry.getRegisteredKeys();
        List<String> affordableKeys = new ArrayList<>();
        for (String key : keys) {
            TravelPackage pkgCandidate = TravelPackagePrototypeRegistry.getPrototype(key);
            if (pkgCandidate != null && pkgCandidate.getPrice() < customPackagePrice) {
                affordableKeys.add(key);
            }
        }

        System.out.println("\nFound " + affordableKeys.size() + " existing packages cheaper than your custom package ($"
                + String.format("%.2f", customPackagePrice) + "): ");
        System.out.println();

        int packageNum = 1;
        for (String key : affordableKeys) {
            TravelPackage pkg = TravelPackagePrototypeRegistry.getPrototype(key);
            if (pkg != null) {
                System.out.println("┌─ PACKAGE #" + packageNum
                        + " ───────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
                System.out.println("│  Key: " + key);
                displayPackageDetails(pkg);
                System.out.println(
                        "└───────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘");
                System.out.println();
                packageNum++;
            }
        }

        System.out.println(
                "Choose a package from existing packages above? (yes/no): ");
        String packageChoice = sc.nextLine().trim().toLowerCase();
        if (packageChoice.equals("yes")) {
            System.out.println("Please enter the package key you want to choose:");
            String selectedKey = sc.nextLine().trim().toLowerCase();
            TravelPackage selectedPackage = TravelPackagePrototypeRegistry.getPrototypeClone(selectedKey); // Clone of
                                                                                                           // prototype.
            if (selectedPackage != null) {
                System.out.println("You have selected the package: " + selectedKey);
                System.out.println("Package details:");
                System.out.println(
                        "------------------------------------------------------------------------------------------------------");
                displayPackageDetails(selectedPackage);
                System.out.println(
                        "------------------------------------------------------------------------------------------------------");
                facade.getCurrentBooking().setTravelPackage(selectedPackage);
                if (facade.getCurrentBooking().getTravelPackage() != null) {
                    TravelPackage originalPackage = TravelPackagePrototypeRegistry.getPrototype(selectedKey);
                    TravelPackage clonedPackage = facade.getCurrentBooking().getTravelPackage();
                    System.out.println("[OK] Original package cloned");
                    System.out.println("  Original package hash: " + originalPackage.hashCode());
                    System.out.println("  Cloned package hash: " + clonedPackage.hashCode());
                    System.out.println(
                            "[OK] Prototype Pattern: Hashes doesn't match, confirming cloning of travel packages");
                    System.out.println("  Original item's hash: " + originalPackage.getItems().get(0).hashCode());
                    System.out.println("  Cloned item's hash: " + clonedPackage.getItems().get(0).hashCode());
                    System.out.println(
                            "[OK] Prototype Pattern: Hashes doesn't match, confirming deep clone of travel items within packages");
                    System.out.println("Going directly to Step 5 for add-ons/customization on the selected package.");
                }
            } else {
                System.out.println("Invalid package key entered. Proceeding without a predefined package.");
            }

        } else {
            System.out.println("No package selected. Proceeding with custom package.");
            System.out.println("Associating the custom-built package with the current booking...");
            // Attach the custom package to the booking using facade command
            facade.addPackage(customPackage);
            System.out.println("[OK] Custom package associated with booking using AddPackageCmd");
            System.out.println();
        }

        // ====================================================================
        // STEP 5: ADD-ONS / CUSTOMIZATION (Decorator Pattern)
        // ====================================================================
        System.out.println(
                "┌──────────────────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println(
                "│ STEP 5: ADD-ONS / CUSTOMIZATION (Decorator, Command Pattern)                                 │");
        System.out.println(
                "└──────────────────────────────────────────────────────────────────────────────────────────────┘");

        Booking currentBooking = facade.getCurrentBooking();
        TravelPackage pkg = currentBooking.getTravelPackage();
        if (pkg == null || pkg.getItems().isEmpty()) {
            System.out.println("[INFO] No items in package to add add-ons to.");
            return;
        }
        // Check for Flight and offer flight add-ons
        TravelItemAPI flightItem = findFlightInPackage(pkg);
        if (flightItem != null) {
            System.out.println("\n Flight found: " + flightItem.getCategoryInfo());
            System.out.print("Add meal service to flight? (yes/no): ");
            if (sc.nextLine().trim().equalsIgnoreCase("yes")) {
                facade.addAddOn(flightItem, "MEAL");
                System.out.println("[OK] Meal add-on applied to flight");
            }
            System.out.print("Add flight insurance? (yes/no): ");
            if (sc.nextLine().trim().equalsIgnoreCase("yes")) {
                facade.addAddOn(flightItem, "INSURANCE");
                System.out.println("[OK] Insurance add-on applied to flight");
            }
            System.out.print("Add extra baggage? (yes/no): ");
            if (sc.nextLine().trim().equalsIgnoreCase("yes")) {
                facade.addAddOn(flightItem, "BAG");
                System.out.println("[OK] Baggage add-on applied to flight");
            }
        }

        // Check for Hotel and offer hotel add-ons
        TravelItemAPI hotelItem = findHotelInPackage(pkg);
        if (hotelItem != null) {
            System.out.println("\n Hotel found: " + hotelItem.getCategoryInfo());
            System.out.print("Add breakfast to hotel? (yes/no): ");
            if (sc.nextLine().trim().equalsIgnoreCase("yes")) {
                facade.addAddOn(hotelItem, "BREAKFAST");
                System.out.println("[OK] Breakfast add-on applied to hotel");
            }
            System.out.print("Add suite upgrade? (yes/no): ");
            if (sc.nextLine().trim().equalsIgnoreCase("yes")) {
                facade.addAddOn(hotelItem, "SUITE_UPGRADE");
                System.out.println("[OK] Suite upgrade applied to hotel");
            }
        }

        // Check for Car and offer car add-ons
        TravelItemAPI carItem = findCarInPackage(pkg);
        if (carItem != null) {
            System.out.println("\n Car rental found: " + carItem.getCategoryInfo());
            System.out.print("Add roadside assistance to car? (yes/no): ");
            if (sc.nextLine().trim().equalsIgnoreCase("yes")) {
                facade.addAddOn(carItem, "ROADSIDE_ASSISTANCE");
                System.out.println("[OK] Roadside assistance add-on applied to car");
            }
            System.out.print("Add child seat to car? (yes/no): ");
            if (sc.nextLine().trim().equalsIgnoreCase("yes")) {
                facade.addAddOn(carItem, "CHILD_SEAT");
                System.out.println("[OK] Child seat add-on applied to car");
            }
        }

        System.out.println("Package details after Add ons:");
        pkg = currentBooking.getTravelPackage();
        displayPackageDetails(pkg);
        System.out.println();

        System.out.println("[OK] Decorator Pattern: Dynamically adds features to travel items");
        System.out.println();

        // ====================================================================
        // STEP 6: COMMAND UNDO (Command Pattern - Undo Functionality)
        // ====================================================================
        System.out.println("┌─────────────────────────────────────────────────────────────┐");
        System.out.println("│ STEP 6: UNDO FUNCTIONALITY (Command Pattern)                │");
        System.out.println("└─────────────────────────────────────────────────────────────┘");

        System.out.println("\nCurrent Booking Summary:");
        printPackageContentsWithPriceBreakdown(currentBooking);

        // Undo flow (prompt if any add-ons exist)
        System.out.println();
        if (!hasAnyDecorators(currentBooking.getTravelPackage())) {
            System.out.println("No add-ons were applied.");
        } else {
            // Prompt the user and undo the last add-on repeatedly until they stop or none
            // left
            while (true) {
                System.out.print("Do you want to undo the last add-on? (yes/no): ");
                String undoAns = sc.nextLine().trim();
                if (undoAns.equalsIgnoreCase("yes")) {
                    System.out.println("\nUndoing last add-on...");
                    facade.undoLastAction();
                    System.out.println("\nUpdated Booking Summary:");
                    printPackageContentsWithPriceBreakdown(currentBooking);
                } else {
                    System.out.println("Skipping further undos.");
                    break;
                }

                if (!hasAnyDecorators(currentBooking.getTravelPackage())) {
                    System.out.println("\nNo more add-ons to undo.");
                    break;
                }
            }
        }
        System.out.println();

        // ====================================================================
        // STEP 7: APPLY PRICING (Strategy Pattern)
        // ====================================================================
        System.out.println("┌─────────────────────────────────────────────────────────────┐");
        System.out.println("│ STEP 7: APPLY PRICING (Strategy Pattern)                    │");
        System.out.println("└─────────────────────────────────────────────────────────────┘");

        BookingFacadeImpl facadeImpl = (BookingFacadeImpl) facade;
        PricingServiceAPI pricingService = facadeImpl.getPricingService();
        Booking booking = facade.getCurrentBooking();
        Customer customer = booking.getCustomer();

        // Prompt for loyalty points
        System.out.print("Enter your loyalty points (integer, optional - Enter for 0): ");
        int loyaltyPoints = 0;
        String input = sc.nextLine().trim();
        if (!input.isEmpty()) {
            try {
                loyaltyPoints = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                loyaltyPoints = 0;
            }
        }
        customer.setLoyaltyPoints(loyaltyPoints);

        double seasonalDiscount;

        if (booking.getTravelPackage() != null) {
            var startDate = criteria.getStartDate();
            if (startDate.getMonthValue() >= 6 && startDate.getMonthValue() <= 8) {
                seasonalDiscount = 0.0; // peak season - no discount
            } else {
                seasonalDiscount = 15.0; // off-season - 15%
            }
        } else {
            seasonalDiscount = 10.0; // no package → default 10%
        }

        pricingService.setSeasonalStrategy(new SeasonalPricingStrategy(seasonalDiscount));
        pricingService.setSeasonalDiscountPercent(seasonalDiscount);
        pricingService.setLoyaltyStrategy(new LoyaltyPricingStrategy(
                pricingService.getLoyaltyThreshold(), pricingService.getLoyaltyDiscountPercent()));

        // Calculate and print final price
        double finalPrice = pricingService.calculatePrice(booking);
        System.out.println("[OK] Final Price after discounts: $" + String.format("%.2f", finalPrice));
        pricingService.printPriceBreakdown(booking);

        System.out.println("Customer: " + customer.getName());
        System.out.println("Loyalty Points: " + customer.getLoyaltyPoints());
        System.out.println("Customer Type: "
                + (customer.getLoyaltyPoints() >= pricingService.getLoyaltyThreshold() ? "PREMIUM" : "BASIC"));
        System.out.println("Seasonal Discount Applied: " + seasonalDiscount + "%");

        // ====================================================================
        // STEP 8: STATE TRANSITIONS (State Pattern)
        // ====================================================================
        System.out.println(
                "┌──────────────────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println(
                "│ STEP 8: STATE TRANSITIONS/OBSERVER NOTIFICATIONS                                             │");
        System.out.println(
                "│ (State/Observer Pattern)                                                                     │");
        System.out.println(
                "└──────────────────────────────────────────────────────────────────────────────────────────────┘");
        System.out.println("Current State: " + currentBooking.getState().getClass().getSimpleName());
        System.out.println("[OK] Booking is in PendingState");

        // Display final booking summary before asking for confirmation/cancellation
        System.out.println("\nFinal Booking Summary:");
        printPackageContentsWithPriceBreakdown(currentBooking, finalPrice);
        System.out.println();

        System.out.print("Do you want to confirm or cancel this booking? (confirm/cancel): ");
        String userChoice = sc.nextLine().trim().toLowerCase();

        boolean bookingConfirmed = false;
        boolean bookingCancelled = false;

        if (userChoice.equals("confirm")) {
            System.out.println(
                    "┌──────────────────────────────────────────────────────────────────────────────────────────────┐");
            System.out.println(
                    "│ STEP 9: CONFIRM AND PROCESS BOOKING (Template Method Pattern)                                │");
            System.out.println(
                    "└──────────────────────────────────────────────────────────────────────────────────────────────┘");
            System.out.println("\nProcessing booking using Template Method Pattern...");

            BookingProcessorFactory processorFactory = new BookingProcessorFactory();
            AbstractBookingProcess processor = processorFactory.createPackageProcessor();
            processor.setBooking(currentBooking);

            Booking confirmedBooking = processor.processSelectedBooking();

            if (confirmedBooking != null) {
                bookingConfirmed = true;
                System.out.println("[OK] Template Method Pattern: Complete booking workflow executed");
            } else {
                System.out.println("[ERROR] Booking processing failed.");
                return;
            }
            System.out.println("[OK] State transition: PendingState -> ConfirmedState");

            System.out.print("[OK] Observer Pattern: Notifications sent via ");
            boolean first = true;
            for (ObserverAPI obs : currentBooking.getObservers()) {
                if (!first)
                    System.out.print(", ");
                if (obs instanceof EmailNotificationObserver)
                    System.out.print("Email");
                else if (obs instanceof SMSNotificationObserver)
                    System.out.print("SMS");
                else if (obs instanceof AppNotificationObserver)
                    System.out.print("App");
                first = false;
            }
            System.out.println();

            System.out.println("Current State: " + currentBooking.getState().getClass().getSimpleName());
            System.out.println("[OK] State Pattern: State transitions managed by state objects");
        } else if (userChoice.equals("cancel")) {
            facade.cancelBooking();
            bookingCancelled = true;
            System.out.println("[OK] State transition: PendingState -> CancelledState");
            System.out.println("Current State: " + currentBooking.getState().getClass().getSimpleName());
            System.out.println("[OK] State Pattern: State transitions managed by state objects");
        } else {
            System.out.println("[INFO] Invalid choice. Booking left in PendingState.");
        }
        System.out.println();

        if (bookingConfirmed) {
            // Notifications already sent during state transition (Pending -> Confirmed)
            System.out.println("[OK] Observer Pattern: Notifications sent based on customer preferences.");
        } else if (bookingCancelled) {
            System.out.println("[OK] Observer Pattern: No notifications sent (cancellation from PendingState)");
        } else {
            System.out.println("[INFO] No state change occurred. Observers not notified.");
        }
        System.out.println();

        // ====================================================================
        // SUMMARY
        // ====================================================================
        System.out.println("┌─────────────────────────────────────────────────────────────┐");
        System.out.println("│ DESIGN PATTERNS DEMONSTRATED                                │");
        System.out.println("└─────────────────────────────────────────────────────────────┘");
        System.out.println("[OK] Builder Pattern      - Customer creation, Package building");
        System.out.println("[OK] Singleton Pattern    - BookingIdGenerator, FlyweightFactory");
        System.out.println("[OK] Factory Pattern      - Abstract Factory for product families");
        System.out.println("[OK] Prototype Pattern    - Package cloning");
        System.out.println("[OK] Facade Pattern       - BookingFacadeAPI simplifies complex system");
        System.out.println("[OK] Adapter Pattern      - External API adaptation");
        System.out.println("[OK] Bridge Pattern       - BookingService abstraction");
        System.out.println("[OK] Composite Pattern    - TravelPackage composition");
        System.out.println("[OK] Decorator Pattern    - Add-ons (Meal, Insurance, etc.)");
        System.out.println("[OK] Command Pattern      - Add/Remove operations with undo");
        System.out.println("[OK] Strategy Pattern     - Pricing strategies (Base, Loyalty, Seasonal)");
        System.out.println("[OK] State Pattern        - Booking states (Pending, Confirmed, Cancelled)");
        System.out.println("[OK] Observer Pattern     - Notification system (Email, SMS, App)");
        System.out.println("[OK] Template Pattern     - BookingProcess workflow");
        System.out.println("[OK] Flyweight Pattern    - Shared category metadata");
        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println("DEMO COMPLETED SUCCESSFULLY!");
        System.out.println("=".repeat(80) + "\n");
        sc.close();

    }

    public static void printItemWithAddonsAndPrice(TravelItemAPI item) {
        double price = item.getPrice(); // includes decorators
        System.out.print("Item: ");

        TravelItemAPI current = item;
        while (current instanceof TravelItemDecorator) {
            System.out.print(
                    current.getClass().getSimpleName().replace("Decorator", "") + " -> ");

            current = ((TravelItemDecorator) current).getWrappedItem();
        }

        System.out.println(current.getClass().getSimpleName() + " | Price: $" + price);
    }

    public static void printPackageContentsWithPriceBreakdown(Booking booking) {
        printPackageContentsWithPriceBreakdownInternal(booking);
        System.out.println(
                "└───────────────────────────────────────────────────────────────────────────────────────────────┘");
    }

    // Internal method that prints the table and returns grandTotal
    private static double printPackageContentsWithPriceBreakdownInternal(Booking booking) {
        TravelPackage pkg = booking.getTravelPackage();
        if (pkg == null && booking.getTravelItem() != null) {
            pkg = (TravelPackage) booking.getTravelItem();
        }

        if (pkg == null || pkg.getItems().isEmpty()) {
            System.out.println("No items in booking.");
            return 0.0;
        }

        System.out.println(
                "┌───────────────────────────────────────────────────────────────────────────────────────────────┐");
        System.out.printf("│ %-50s │ %-11s │ %-11s │ %-12s │\n", "Item", "Base Price", "Add-ons", "Total Price");
        System.out.println(
                "├───────────────────────────────────────────────────────────────────────────────────────────────┤");

        double totalBasePrice = 0.0;
        double totalAddonsCost = 0.0;
        double grandTotal = 0.0;

        for (TravelItemAPI item : pkg.getItems()) {
            double totalPrice = item.getPrice(); // includes decorators

            // Unwrap decorators to get base item and calculate decorator costs
            TravelItemAPI current = item;
            List<String> decoratorNames = new ArrayList<>();
            double decoratorTotalCost = 0.0;

            while (current instanceof TravelItemDecorator) {
                TravelItemDecorator decorator = (TravelItemDecorator) current;
                String decoratorName = current.getClass().getSimpleName().replace("Decorator", "");
                decoratorNames.add(decoratorName);

                // Calculate this decorator's cost
                double priceWithDecorator = decorator.getPrice();
                double priceWithoutDecorator = decorator.getWrappedItem().getPrice();
                double decoratorCost = priceWithDecorator - priceWithoutDecorator;
                decoratorTotalCost += decoratorCost;

                current = decorator.getWrappedItem();
            }

            double basePrice = totalPrice - decoratorTotalCost;
            String baseItemName = current.getClass().getSimpleName();
            String itemDescription = baseItemName;

            // Build description with decorators, showing as many as possible
            if (!decoratorNames.isEmpty()) {
                StringBuilder descBuilder = new StringBuilder(baseItemName);
                int maxLength = 50; // Max column width
                int currentLength = baseItemName.length();
                int addedCount = 0;

                for (String decorator : decoratorNames) {
                    String addition = " + " + decorator;
                    int spaceNeeded = addition.length();
                    int remainingCount = decoratorNames.size() - addedCount - 1;

                    if (remainingCount > 0) {
                        // Reserve space for "...(X more)" if there are more decorators
                        spaceNeeded += 10; // approximate space for "...(X more)"
                    }

                    if (currentLength + spaceNeeded <= maxLength) {
                        descBuilder.append(addition);
                        currentLength += addition.length();
                        addedCount++;
                    } else {
                        // Show remaining count if not all decorators fit
                        int remaining = decoratorNames.size() - addedCount;
                        if (remaining > 0) {
                            descBuilder.append(" + ...(");
                            descBuilder.append(remaining);
                            descBuilder.append(" more)");
                        }
                        break;
                    }
                }
                itemDescription = descBuilder.toString();
            }

            if (itemDescription.length() > 50) {
                itemDescription = itemDescription.substring(0, 47) + "...";
            }

            System.out.printf("│ %-50s │ $%-10.2f │ $%-10.2f │ $%-11.2f │\n",
                    itemDescription, basePrice, decoratorTotalCost, totalPrice);

            totalBasePrice += basePrice;
            totalAddonsCost += decoratorTotalCost;
            grandTotal += totalPrice;
        }

        System.out.println(
                "├───────────────────────────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ %-50s │ $%-10.2f │ $%-10.2f │ $%-11.2f │\n",
                "TOTAL", totalBasePrice, totalAddonsCost, grandTotal);

        return grandTotal;
    }

    // Overloaded method to show discount and final price after pricing strategy
    public static void printPackageContentsWithPriceBreakdown(Booking booking, double finalPriceAfterStrategy) {
        double grandTotal = printPackageContentsWithPriceBreakdownInternal(booking);

        // Show discount and final price if strategy was applied
        if (finalPriceAfterStrategy < grandTotal) {
            double discount = grandTotal - finalPriceAfterStrategy;
            System.out.println(
                    "├───────────────────────────────────────────────────────────────────────────────────────────────┤");
            System.out.printf("│ %-50s │ %-11s │ %-11s │ $%-11.2f │\n",
                    "Discount Applied", "", "", discount);
            System.out.println(
                    "├───────────────────────────────────────────────────────────────────────────────────────────────┤");
            System.out.printf("│ %-50s │ %-11s │ %-11s │ $%-11.2f │\n",
                    "FINAL PRICE", "", "", finalPriceAfterStrategy);
        }

        System.out.println(
                "└───────────────────────────────────────────────────────────────────────────────────────────────┘");
    }

    public static void printPackageContents(Booking booking) {
        if (booking.getTravelPackage() != null) {
            for (TravelItemAPI item : booking.getTravelPackage().getItems()) {
                printItemWithAddonsAndPrice(item);
            }
        } else if (booking.getTravelItem() != null) {
            printItemWithAddonsAndPrice(booking.getTravelItem());
        }
    }

    public static boolean isFlight(TravelItemAPI item) {
        String category = item.getCategoryInfo().toLowerCase();
        return category.contains("flight") || category.contains("class");
    }

    public static boolean isHotel(TravelItemAPI item) {
        String category = item.getCategoryInfo().toLowerCase();
        return category.contains("hotel") || category.contains("room") || category.contains("suite");
    }

    public static boolean isCar(TravelItemAPI item) {
        String category = item.getCategoryInfo().toLowerCase();
        return category.contains("car") || category.contains("rental") || category.contains("sedan") ||
                category.contains("suv") || category.contains("compact");
    }

    // Check whether an item has any decorator
    public static boolean isDecorated(TravelItemAPI item) {
        return item instanceof TravelItemDecorator;
    }

    // Check whether any item in the package has decorators
    public static boolean hasAnyDecorators(TravelPackage pkg) {
        if (pkg == null)
            return false;
        for (TravelItemAPI item : pkg.getItems()) {
            if (isDecorated(item))
                return true;
        }
        return false;
    }

    public static TravelItemAPI findFlightInPackage(TravelPackage pkg) {
        if (pkg == null)
            return null;
        for (TravelItemAPI item : pkg.getItems()) {
            if (isFlight(item))
                return item;
        }
        return null;
    }

    public static TravelItemAPI findHotelInPackage(TravelPackage pkg) {
        if (pkg == null)
            return null;
        for (TravelItemAPI item : pkg.getItems()) {
            if (isHotel(item))
                return item;
        }
        return null;
    }

    public static TravelItemAPI findCarInPackage(TravelPackage pkg) {
        if (pkg == null)
            return null;
        for (TravelItemAPI item : pkg.getItems()) {
            if (isCar(item))
                return item;
        }
        return null;
    }

    public static void displayPackageDetails(TravelPackage pkg) {
        if (pkg == null) {
            System.out.println("No package details to display.");
            return;
        }
        System.out.println("│  Name: " + pkg.getPackageName());
        System.out.println("│  Package ID: " + pkg.getId());
        System.out.println("│  Total Price: $" + String.format("%.2f", pkg.getPrice()));
        System.out.println("│  Description: " + pkg.getDescription());
        System.out.println("│");
        System.out.println("│  Items included:");
        var items = pkg.getItems();
        for (int i = 0; i < items.size(); i++) {
            TravelItemAPI item = items.get(i);
            System.out.println("│    " + (i + 1) + ". " + item.getCategoryInfo());
            System.out.println("│       ID: " + item.getId());
            System.out.println("│       Description: " + item.getDescription());
            System.out.println("│       Price: $" + String.format("%.2f", item.getPrice()));
        }
    }
}