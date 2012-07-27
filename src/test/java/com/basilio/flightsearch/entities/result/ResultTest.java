package com.basilio.flightsearch.entities.result;

import com.basilio.flightsearch.entities.Search;
import org.junit.Before;
import org.testng.annotations.Test;

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
public class ResultTest {

    @Before
    public void setupTests(){

    }

    @Test
    public void testGetDirectFlights() throws Exception {
        Result result = new Result();
        List<Flights> mockFlightList = new ArrayList<Flights>();

        Flights mockedFlight1 = createNiceMock(Flights.class);

        mockFlightList.add(mockedFlight1);
        result.setFlights(mockFlightList);

        List<OutboundRoutes> mockedOutboundRoutes = createNiceMock(ArrayList.class);
        List<Segments> mockedSegments = createNiceMock(ArrayList.class);
        OutboundRoutes mockedOutboundRoute = createNiceMock(OutboundRoutes.class);

        expect(mockedFlight1.getOutboundRoutes()).andReturn(mockedOutboundRoutes).anyTimes();
        expect(mockedOutboundRoutes.size()).andReturn(1).anyTimes();
        expect(mockedOutboundRoutes.get(0)).andReturn(mockedOutboundRoute).anyTimes();
        expect(mockedOutboundRoute.getSegments()).andReturn(mockedSegments).anyTimes();
        expect(mockedSegments.size()).andReturn(20).once();
        expect(mockedSegments.size()).andReturn(1).once();
        expect(mockedSegments.size()).andReturn(2).once();

        replay(mockedFlight1,mockedOutboundRoutes,mockedSegments,mockedOutboundRoute);

        assertEquals(0, result.getDirectFlights().size());
        assertEquals(1, result.getDirectFlights().size());
        assertEquals(0, result.getDirectFlights().size());

        verify(mockedFlight1,mockedOutboundRoutes,mockedSegments,mockedOutboundRoute);
    }

    @Test
    public void testGetDescription(){
        Result result = new Result();
        result.setSearchedPrice(600);
        assertNotNull(result.getDescription());
        assertEquals(String.class, result.getDescription().getClass());

        List<Flights> flightList = new ArrayList<Flights>();
        Flights flight1 = new Flights();
        PriceInfo priceInfo = new PriceInfo();
        Total total = new Total();
        total.setFare(new Integer(500));
        priceInfo.setTotal(total);
        flight1.setPriceInfo(priceInfo);
        flightList.add(flight1);
        result.setFlights(flightList);

        Meta meta = new Meta();
        Facets facet = new Facets();
        List<Facets> facets = new ArrayList<Facets>();
        facets.add(facet);
        facets.add(facet);
        facets.add(facet);
        meta.setFacets(facets);
        result.setMeta(meta);

        assertNotNull(result.getDescription());
        assertEquals(String.class, result.getDescription().getClass());

    }

    @Test
    public void testGetFlightsInPriceRange(){
        Result result = new Result();
        result.setSearchedPrice(600);

        List<Flights> mockFlightList = new ArrayList<Flights>();
        Flights mockedFlight1 = createNiceMock(Flights.class);
        mockFlightList.add(mockedFlight1);
        mockFlightList.add(mockedFlight1);
        result.setFlights(mockFlightList);

        Total mockedTotal = createNiceMock(Total.class);
        PriceInfo mockedPriceInfo = createNiceMock(PriceInfo.class);

        expect(mockedFlight1.getPriceInfo()).andReturn(mockedPriceInfo).anyTimes();
        expect(mockedPriceInfo.getTotal()).andReturn(mockedTotal).anyTimes();
        expect(mockedTotal.getFare()).andReturn(new Integer(20)).once();
        expect(mockedTotal.getFare()).andReturn(new Integer(30)).once();

        expect(mockedTotal.getFare()).andReturn(new Integer(20)).once();
        expect(mockedTotal.getFare()).andReturn(new Integer(3000)).once();

        replay(mockedFlight1, mockedPriceInfo,mockedTotal);

        assertEquals(2,result.getFlightsInPriceRange().size());
        assertEquals(1,result.getFlightsInPriceRange().size());

        verify(mockedFlight1, mockedPriceInfo,mockedTotal);

    }
}
