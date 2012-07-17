package com.basilio.flightsearch.entities.result;

import com.basilio.flightsearch.entities.Search;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

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
}
