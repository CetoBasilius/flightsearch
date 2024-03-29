package com.basilio.flightsearch.entities.flightresult;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 7/17/12
 * Time: 4:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class FlightResultTest {

    @Test
    public void testGetDirectFlights() {
        FlightResult flightResult = new FlightResult();
        List<Flight> mockFlightList = new ArrayList<Flight>();

        Flight mockedFlight1 = createNiceMock(Flight.class);

        mockFlightList.add(mockedFlight1);
        flightResult.setFlights(mockFlightList);

        List<Route> mockedOutboundRoutes = createNiceMock(ArrayList.class);
        List<Segment> mockedSegments = createNiceMock(ArrayList.class);
        Route mockedOutboundRoute = createNiceMock(Route.class);

        expect(mockedFlight1.getOutboundRoutes()).andReturn(mockedOutboundRoutes).anyTimes();
        expect(mockedOutboundRoutes.size()).andReturn(1).anyTimes();
        expect(mockedOutboundRoutes.get(0)).andReturn(mockedOutboundRoute).anyTimes();
        expect(mockedOutboundRoute.getSegments()).andReturn(mockedSegments).anyTimes();
        expect(mockedSegments.size()).andReturn(20).once();
        expect(mockedSegments.size()).andReturn(1).once();
        expect(mockedSegments.size()).andReturn(2).once();

        replay(mockedFlight1, mockedOutboundRoutes, mockedSegments, mockedOutboundRoute);

        assertEquals(0, flightResult.getDirectFlights().size());
        assertEquals(1, flightResult.getDirectFlights().size());
        assertEquals(0, flightResult.getDirectFlights().size());

        verify(mockedFlight1, mockedOutboundRoutes, mockedSegments, mockedOutboundRoute);
    }

    @Test
    public void testGetDescription() {
        FlightResult flightResult = new FlightResult();
        flightResult.setSearchedPrice(600);
        assertNotNull(flightResult.getDescription());
        assertEquals(String.class, flightResult.getDescription().getClass());

        List<Flight> flightList = new ArrayList<Flight>();
        Flight flight1 = new Flight();
        PriceInfo priceInfo = new PriceInfo();
        Total total = new Total();
        total.setFare(new Integer(500));
        priceInfo.setTotal(total);
        flight1.setPriceInfo(priceInfo);
        flightList.add(flight1);
        flightResult.setFlights(flightList);

        Meta meta = new Meta();
        Facet facet = new Facet();
        List<Facet> facets = new ArrayList<Facet>();
        facets.add(facet);
        facets.add(facet);
        facets.add(facet);
        meta.setFacets(facets);
        flightResult.setMeta(meta);

        assertNotNull(flightResult.getDescription());
        assertEquals(String.class, flightResult.getDescription().getClass());

    }

    @Test
    public void testGetFlightsInPriceRange() {
        FlightResult flightResult = new FlightResult();
        flightResult.setSearchedPrice(600);

        List<Flight> mockFlightList = new ArrayList<Flight>();
        Flight mockedFlight1 = createNiceMock(Flight.class);
        mockFlightList.add(mockedFlight1);
        mockFlightList.add(mockedFlight1);
        flightResult.setFlights(mockFlightList);

        Total mockedTotal = createNiceMock(Total.class);
        PriceInfo mockedPriceInfo = createNiceMock(PriceInfo.class);

        expect(mockedFlight1.getPriceInfo()).andReturn(mockedPriceInfo).anyTimes();
        expect(mockedPriceInfo.getTotal()).andReturn(mockedTotal).anyTimes();

        expect(mockedTotal.getFare()).andReturn(new Integer(20)).once();
        expect(mockedTotal.getFare()).andReturn(new Integer(30)).once();

        expect(mockedTotal.getFare()).andReturn(new Integer(20)).once();
        expect(mockedTotal.getFare()).andReturn(new Integer(3000)).once();

        replay(mockedFlight1, mockedPriceInfo, mockedTotal);

        assertEquals(2, flightResult.getFlightsInPriceRange().size());
        assertEquals(1, flightResult.getFlightsInPriceRange().size());

        verify(mockedFlight1, mockedPriceInfo, mockedTotal);

    }


}
