package travelease.singleton;

public class BookingIdGenerator {

    private static volatile BookingIdGenerator instance;
    private int counter = 1000; // starting point

    /**
     * Thread-safe Singleton responsible for generating unique booking IDs.
     * This class ensures that ID generation is centralized and synchronized across
     * user sessions, preventing ID collisions in the system.
     * Also uses the Double-Checked Locking for performance and safety.
     */
    private BookingIdGenerator() {
        // private to block external instantiation
    }

    public static BookingIdGenerator getInstance() {
        if (instance == null) {
            synchronized (BookingIdGenerator.class) {
                if (instance == null) {
                    instance = new BookingIdGenerator();
                }
            }
        }
        return instance;
    }

    public synchronized int nextId() {
        return counter++;
    }
}
