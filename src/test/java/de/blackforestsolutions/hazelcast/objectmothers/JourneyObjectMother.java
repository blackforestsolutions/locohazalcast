package de.blackforestsolutions.hazelcast.objectmothers;

import de.blackforestsolutions.datamodel.*;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;

import java.time.Duration;
import java.time.Instant;
import java.util.*;


public class JourneyObjectMother {

    public static Journey getJourneyBerlinHamburg() {
        Journey journey = new Journey();
        journey.setStart(getTravelPointBrandenBurg());
        journey.getBetweenHolds().put(1, getTravelPointBrandenBurg());
        journey.getBetweenHolds().put(2, getTravelPointHamburg());
        journey.setStart(getTravelPointBrandenBurg());
        journey.setDestination(getTravelPointHamburg());
        journey.setTravelProvider(TravelProvider.DB);
        journey.setStartTime(Date.from(Instant.ofEpochMilli(1L)));
        journey.setArrivalTime(Date.from(Instant.ofEpochMilli(1L)));
        journey.setMatchesRequest(false);
        journey.setStartTimeUpdated(Date.from(Instant.ofEpochMilli(1L)));
        journey.setArrivalTimeUpdated(Date.from(Instant.ofEpochMilli(1L)));
        journey.setId(UUID.fromString("cb11896e-c38a-4f53-8d40-1f28ca690f5a"));
        Price price = new Price();
        price.setCurrency(Currency.getInstance("EUR"));
        price.setSymbol("€");
        price.setValue(234.90);
        journey.setPrice(price);
        journey.setPriceWithCommision(price);
        journey.setUnknownTravelProvider("unknownTravelProvider");
        journey.setDuration(Duration.ofSeconds(22222L));
        journey.setDistance(new Distance(20000L, Metrics.KILOMETERS));
        journey.setJourneysRelated(Collections.singletonList(UUID.fromString("cb11896e-c38a-4f53-8d40-1f28ca690f5a")));
        journey.setProviderId("providerId");
        journey.setDelay(Duration.ofSeconds(22L));
        journey.setVehicleName("vehicleName");
        journey.setVehicleNumber("vehicleNumber");
        journey.setStartStatus("startStatus");
        journey.setArrivalStatus("arrivalStatus");
        journey.setDescription("description");
        return journey;
    }

    public static TravelPoint getTravelPointBrandenBurg() {
        TravelPoint start = new TravelPoint();
        start.setCity("Berlin");
        start.setPostalCode("64664");
        start.setStateOrProvince("Brandenburg");
        start.setStreetNumber("34");
        start.setStreet("Feldweg");
        start.setDepartureTime(Date.from(Instant.ofEpochMilli(1L)));
        start.setArrivalTime(Date.from(Instant.ofEpochMilli(1L)));
        start.setCountry(new Locale("DE", "DE"));
        start.setGpsCoordinates(new Coordinates(1, 0));
        return start;
    }

    public static TravelPoint getTravelPointHamburg() {
        TravelPoint destination = new TravelPoint();
        destination.setCity("Hamburg");
        destination.setPostalCode("58974");
        destination.setStateOrProvince("Hamburg");
        destination.setStreetNumber("6");
        destination.setStreet("Fischmarkt");
        destination.setCountry(new Locale("DE", "DE"));
        destination.setGpsCoordinates(new Coordinates(1, 0));
        destination.setDepartureTime(Date.from(Instant.ofEpochMilli(1L)));
        destination.setArrivalTime(Date.from(Instant.ofEpochMilli(1L)));
        return destination;
    }

}
