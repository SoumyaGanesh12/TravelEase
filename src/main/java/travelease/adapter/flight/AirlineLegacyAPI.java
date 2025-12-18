// external/AirlineLegacyAPI.java
package travelease.adapter.flight;

import java.util.List;

public class AirlineLegacyAPI {
    public List<String> findFlights(String origin, String destination) {
        return List.of(
            "American Airlines | AA101 | " + origin + "," + destination + " | $259.99 | economy",
            "Delta Airlines | DL234 | " + origin + "," + destination + " | $349.99 | business",
            "United Airlines | UA345 | " + origin + "," + destination + " | $299.99 | premium",
            "JetBlue | JB456 | " + origin + "," + destination + " | $219.99 | economy",
            "Southwest | SW567 | " + origin + "," + destination + " | $329.99 | business"
        );
    }
}

