package travelease.facade;

import java.util.ArrayList;
import java.util.List;

import travelease.Customer;
import travelease.TravelItemAPI;
import travelease.builder.CustomTravelPackageBuilder;
import travelease.builder.CustomerBuilder;
import travelease.builder.CustomerBuilderAPI;
import travelease.builder.CustomerDirector;
import travelease.command.AddAddOnCmd;
import travelease.command.AddCarCmd;
import travelease.command.AddFlightCmd;
import travelease.command.AddHotelCmd;
import travelease.command.AddPackageCmd;
import travelease.command.BookingCommandManager;
import travelease.command.CommandAPI;
import travelease.composite.TravelPackage;
import travelease.helper.NotificationService;
import travelease.helper.NotificationServiceAPI;
import travelease.helper.PricingService;
import travelease.helper.PricingServiceAPI;
import travelease.helper.SearchCriteria;
import travelease.observer.NotificationPreferences;
import travelease.singleton.BookingIdGenerator;
import travelease.state.Booking;
import travelease.state.BookingStateAPI;
import travelease.state.BookingStateFactory;
import travelease.template.AbstractBookingProcess;
import travelease.template.BookingProcessorFactory;

/**
 * Facade Pattern: Implementation that simplifies complex booking operations by coordinating multiple subsystems.
 */
public class BookingFacadeImpl implements BookingFacadeAPI {

    private Booking currentBooking;
    private final BookingCommandManager invoker;
    private final PricingServiceAPI pricingService;
    private final NotificationServiceAPI notificationService;
    private final CustomerDirector customerDirector;
    private final BookingProcessorFactory processorFactory;
    private final BookingStateFactory stateFactory;
    private final CustomerBuilderAPI customerBuilder;

    /**
     * Default constructor
     */
    public BookingFacadeImpl() {
        this(new BookingCommandManager(),
            new PricingService(),
            new NotificationService(),
            new CustomerDirector(),
            new BookingProcessorFactory(),
            new BookingStateFactory(),
            new CustomerBuilder());
    }

    // Parameterized constructor
    public BookingFacadeImpl(BookingCommandManager invoker,
                            PricingServiceAPI pricingService,
                            NotificationServiceAPI notificationService,
                            CustomerDirector customerDirector,
                            BookingProcessorFactory processorFactory,
                            BookingStateFactory stateFactory,
                            CustomerBuilderAPI customerBuilder) {
        this.invoker = invoker;
        this.pricingService = pricingService;
        this.notificationService = notificationService;
        this.customerDirector = customerDirector;
        this.processorFactory = processorFactory;
        this.stateFactory = stateFactory;
        this.customerBuilder = customerBuilder;
    }

    // SEARCH
    @Override
    public List<List<TravelItemAPI>> searchIndividualItems(SearchCriteria criteria) {
        List<TravelItemAPI> resultsFlights = new ArrayList<>();
        List<TravelItemAPI> resultsHotels = new ArrayList<>();
        List<TravelItemAPI> resultsCars = new ArrayList<>();

        // Use factory to create processors
        AbstractBookingProcess flightProc = processorFactory.createFlightProcessor();
        resultsFlights.addAll(flightProc.search(criteria));

        AbstractBookingProcess hotelProc = processorFactory.createHotelProcessor();
        resultsHotels.addAll(hotelProc.search(criteria));

        AbstractBookingProcess carProc = processorFactory.createCarProcessor();
        resultsCars.addAll(carProc.search(criteria));

        // Aggregate results
        List<List<TravelItemAPI>> results = new ArrayList<>();
        results.add(resultsFlights);
        results.add(resultsHotels);
        results.add(resultsCars);

        return results;
    }

    public PricingServiceAPI getPricingService() {
        return this.pricingService;
    }

    // BOOKING CREATION
    @Override
    public void createCustomerProfile(String name, String email, String phoneNumber, String customerId) {
        // Use Customer Builder pattern
        customerDirector.setBuilder(customerBuilder);
        
        Customer c = customerDirector.createBasicCustomer(
            "Customer " + customerId, 
            email,
            phoneNumber
        );
        

        // Use BookingIdGenerator singleton
        String bookingId = "BKG-" + BookingIdGenerator.getInstance().nextId();
        
        currentBooking = new Booking(bookingId, c);
        // Use factory to create state
        BookingStateAPI pendingState = stateFactory.createPendingState();
        currentBooking.setState(pendingState);
    }
    @Override
    public void createEmptyBooking(String name, String email, String phoneNumber, String customerId, NotificationPreferences preferences) {
        createCustomerProfile(name, email, phoneNumber, customerId);
        // Configure observers according to the provided preferences
        notificationService.configureObservers(currentBooking, preferences);
    }

    // COMMAND-BASED ITEM ADDITIONS
    @Override
    public void addFlight(TravelItemAPI flight) {
        ensurePackageCreated();
        CommandAPI cmd = new AddFlightCmd(currentBooking, flight);
        invoker.execute(cmd);
    }

    @Override
    public void addHotel(TravelItemAPI hotel) {
        ensurePackageCreated();
        CommandAPI cmd = new AddHotelCmd(currentBooking, hotel);
        invoker.execute(cmd);
    }

    @Override
    public void addCar(TravelItemAPI car) {
        ensurePackageCreated();
        CommandAPI cmd = new AddCarCmd(currentBooking, car);
        invoker.execute(cmd);
    }

    @Override
    public void addPackage(TravelPackage travelPackage) {
        // Attach a pre-built TravelPackage to the current booking
        CommandAPI cmd = new AddPackageCmd(currentBooking, travelPackage);
        invoker.execute(cmd);
    }

    @Override
    public void addAddOn(TravelItemAPI target, String addOnChoice) {
        CommandAPI cmd = new AddAddOnCmd(currentBooking, target, addOnChoice);
        invoker.execute(cmd);
    }

    // Create an empty travel package if not already existing
    private void ensurePackageCreated() {
        if (currentBooking.getTravelItem() == null && currentBooking.getTravelPackage() == null) {
            // Use builder to create a new empty TravelPackage
            CustomTravelPackageBuilder builder = new CustomTravelPackageBuilder();
            TravelPackage emptyPackage = builder
                    .reset()
                    .build();

            // Set it in the booking
            currentBooking.setTravelItem(emptyPackage);
            currentBooking.setTravelPackage(emptyPackage);
        }
    }

    // PRICING
    @Override
    public double applyPricing() {
        return pricingService.calculatePrice(currentBooking);
    }

    // STATE ACTIONS
    @Override
    public void confirmBooking() {
        currentBooking.proceed();
    }

    @Override
    public void cancelBooking() {
        currentBooking.cancel();
    }

    // COMMAND UNDO
    @Override
    public void undoLastAction() {
        invoker.undoLastAction();
    }

    // GETTER
    @Override
    public Booking getCurrentBooking() {
        return currentBooking;
    }
}
