package travelease.template;

/**
 * Template Pattern: Factory for creating booking processor instances (Flight, Hotel, Car, Package).
 */
public class BookingProcessorFactory {
    
    /**
     * Creates a flight booking processor.
     * @return FlightBookingProcessor instance
     */
    public AbstractBookingProcess createFlightProcessor() {
        return new FlightBookingProcessor();
    }
    
    /**
     * Creates a hotel booking processor.
     * @return HotelBookingProcessor instance
     */
    public AbstractBookingProcess createHotelProcessor() {
        return new HotelBookingProcessor();
    }
    
    /**
     * Creates a car booking processor.
     * @return CarBookingProcessor instance
     */
    public AbstractBookingProcess createCarProcessor() {
        return new CarBookingProcessor();
    }
    
    /**
     * Creates a package booking processor for bookings with multiple item types.
     * @return PackageBookingProcessor instance
     */
    public AbstractBookingProcess createPackageProcessor() {
        return new PackageBookingProcessor();
    }
}

