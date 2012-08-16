package com.basilio.flightsearch.entities.flightresult;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 8/10/12
 * Time: 9:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class FlightTest {
    @Test
    public void testIsOutboundDirect() {

        Flight flight = new Flight();

        List<Route> outboundList = createNiceMock(ArrayList.class);
        List<Segment> segmentList = createNiceMock(ArrayList.class);
        Route outboundRoute = createNiceMock(Route.class);

        assertTrue(flight.outboundHasSegments(Flight.ANY_SEGMENTS));
        assertTrue(flight.outboundHasSegments(Flight.ONE_SEGMENT));
        assertTrue(flight.outboundHasSegments(Flight.TWO_OR_MORE_SEGMENTS));

        assertTrue(flight.inboundHasSegments(Flight.ANY_SEGMENTS));
        assertTrue(flight.inboundHasSegments(Flight.ONE_SEGMENT));
        assertTrue(flight.inboundHasSegments(Flight.TWO_OR_MORE_SEGMENTS));
        flight.setOutboundRoutes(outboundList);

        expect(outboundList.size()).andReturn(1).anyTimes();
        expect(outboundList.get(0)).andReturn(outboundRoute).anyTimes();
        expect(outboundRoute.getSegments()).andReturn(segmentList).anyTimes();

        expect(segmentList.size()).andReturn(20).once();
        expect(segmentList.size()).andReturn(1).once();
        expect(segmentList.size()).andReturn(2).once();

        expect(segmentList.size()).andReturn(20).once();
        expect(segmentList.size()).andReturn(1).once();
        expect(segmentList.size()).andReturn(2).once();

        expect(segmentList.size()).andReturn(20).once();
        expect(segmentList.size()).andReturn(1).once();
        expect(segmentList.size()).andReturn(2).once();

        replay(outboundList, segmentList, outboundRoute);

        assertEquals(false, flight.outboundHasSegments(Flight.ONE_SEGMENT));
        assertEquals(true, flight.outboundHasSegments(Flight.ONE_SEGMENT));
        assertEquals(false, flight.outboundHasSegments(Flight.ONE_SEGMENT));

        assertEquals(true, flight.outboundHasSegments(Flight.TWO_OR_MORE_SEGMENTS));
        assertEquals(false, flight.outboundHasSegments(Flight.TWO_OR_MORE_SEGMENTS));
        assertEquals(true, flight.outboundHasSegments(Flight.TWO_OR_MORE_SEGMENTS));

        assertEquals(true, flight.outboundHasSegments(Flight.ANY_SEGMENTS));
        assertEquals(true, flight.outboundHasSegments(Flight.ANY_SEGMENTS));
        assertEquals(true, flight.outboundHasSegments(Flight.ANY_SEGMENTS));

        verify(outboundList, segmentList, outboundRoute);
    }

    @Test
    public void testIsInboundDirect() {

        Flight flight = new Flight();

        List<Route> inboundList = createNiceMock(ArrayList.class);
        List<Segment> segmentList = createNiceMock(ArrayList.class);
        Route inboundRoute = createNiceMock(Route.class);

        flight.setInboundRoutes(inboundList);

        expect(inboundList.size()).andReturn(1).anyTimes();
        expect(inboundList.get(0)).andReturn(inboundRoute).anyTimes();
        expect(inboundRoute.getSegments()).andReturn(segmentList).anyTimes();

        expect(segmentList.size()).andReturn(20).once();
        expect(segmentList.size()).andReturn(1).once();
        expect(segmentList.size()).andReturn(2).once();

        expect(segmentList.size()).andReturn(20).once();
        expect(segmentList.size()).andReturn(1).once();
        expect(segmentList.size()).andReturn(2).once();

        expect(segmentList.size()).andReturn(20).once();
        expect(segmentList.size()).andReturn(1).once();
        expect(segmentList.size()).andReturn(2).once();

        replay(inboundList, segmentList, inboundRoute);

        assertEquals(false, flight.inboundHasSegments(Flight.ONE_SEGMENT));
        assertEquals(true, flight.inboundHasSegments(Flight.ONE_SEGMENT));
        assertEquals(false, flight.inboundHasSegments(Flight.ONE_SEGMENT));

        assertEquals(true, flight.inboundHasSegments(Flight.TWO_OR_MORE_SEGMENTS));
        assertEquals(false, flight.inboundHasSegments(Flight.TWO_OR_MORE_SEGMENTS));
        assertEquals(true, flight.inboundHasSegments(Flight.TWO_OR_MORE_SEGMENTS));

        assertEquals(true, flight.inboundHasSegments(Flight.ANY_SEGMENTS));
        assertEquals(true, flight.inboundHasSegments(Flight.ANY_SEGMENTS));
        assertEquals(true, flight.inboundHasSegments(Flight.ANY_SEGMENTS));

        verify(inboundList, segmentList, inboundRoute);

    }

    @Test
    public void testRoutesDescriptions() {
        Flight flight = new Flight();

        List<Route> routeList = new ArrayList<Route>();
        List<Segment> segmentList = new ArrayList<Segment>();
        Segment segment = new Segment();
        segment.setArrival(new Location("arrival"));
        segment.setDeparture(new Location("departure"));
        segmentList.add(segment);

        flight.setInboundRoutes(routeList);
        assertEquals(routeList, flight.getInboundRoutes());
        assertTrue(flight.getInDescription().contains("empty"));

        flight.setOutboundRoutes(routeList);
        assertEquals(routeList, flight.getOutboundRoutes());
        assertTrue(flight.getOutDescription().contains("empty"));

        Route route = new Route();
        route.setSegments(segmentList);
        routeList.add(route);

        assertNotNull(flight.getInDescription());
        assertNotNull(flight.getOutDescription());

        assertTrue(!flight.getInDescription().contains("empty"));
        assertTrue(!flight.getOutDescription().contains("empty"));


    }

    @Test
    public void testOtherMethods() {
        Flight flight = new Flight();

        String idString = "id";
        flight.setId(idString);
        assertEquals(idString, flight.getId());

        PriceInfo priceInfo = new PriceInfo();
        PaymentInfo paymentInfo = new PaymentInfo();

        flight.setPaymentInfo(paymentInfo);
        flight.setPriceInfo(priceInfo);

        assertEquals(priceInfo, flight.getPriceInfo());
        assertEquals(paymentInfo, flight.getPaymentInfo());

        List<ItineraryInfo> itineraryInfos = new ArrayList<ItineraryInfo>();
        flight.setItineraryInfos(itineraryInfos);
        assertEquals(itineraryInfos, flight.getItineraryInfos());
    }
}
