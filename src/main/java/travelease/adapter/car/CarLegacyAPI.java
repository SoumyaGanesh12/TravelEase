// external/UberLegacyAPI.java
package travelease.adapter.car;

import java.util.List;

public class CarLegacyAPI {
    public List<String> findCars(String city) {
        return List.of(
            "UberPink | " + city + " | $30/day | budget",
            "UberBlack | " + city + " | $190/day | premium",
            "UberWhite | " + city + " | $20/day | budget",
            "UberABC | " + city + " | $340/day | business",
            "UberXYZ | " + city + " | $30/day | budget",
            "UberBlack | " + city + " | $190/day | premium"
        );
    }
}
