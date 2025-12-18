// external/HotelLegacyAPI.java
package travelease.adapter.hotel;

import java.util.List;

public class HotelLegacyAPI {
    public List<String> findHotels(String city) {
        return List.of(
            "Hilton | " + city + " | $150/night | business",
            "Marriott | " + city + " | $180/night | leisure",
            "Holiday Inn | " + city + " | $120/night | budget",
            "Hyatt | " + city + " | $200/night | premium",
            "Best Western | " + city + " | $110/night | budget"
        );
    }
}

