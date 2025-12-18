package travelease;
import java.time.LocalDateTime;

/**
 * Schedulable defines optional time-based properties for travel items for
 * items that have a start and end time (such as flights, hotels,
 * and car rentals) 
 */
public interface Schedulable {

    LocalDateTime getStartDate();

    LocalDateTime getEndDate();
}