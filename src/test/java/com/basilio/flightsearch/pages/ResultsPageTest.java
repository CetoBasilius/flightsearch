package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.annotations.GuestAccess;
import com.basilio.flightsearch.components.CustomPagedLoop;
import com.basilio.flightsearch.core.FlightResultFilterImpl;
import com.basilio.flightsearch.dal.air.AirportInformationDAO;
import com.basilio.flightsearch.entities.AirportStub;
import com.basilio.flightsearch.entities.flightresult.*;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 7/17/12
 * Time: 3:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class ResultsPageTest {

    private final String CODE1 = "AAA";
    private final String CODE2 = "BBB";

    @Test
    public void testGuestAccess(){
        assertTrue(ResultsPage.class.isAnnotationPresent(GuestAccess.class));
    }

    @Test
    public void testViewMap(){
        ResultsPage resultsPage = new ResultsPage();

        AirportInformationDAO airportInformationDAO = createNiceMock(AirportInformationDAO.class);

        List<AirportStub> testList = new ArrayList<AirportStub>();
        testList.add(new AirportStub(CODE1,""));
        testList.add(new AirportStub(CODE2,""));

        expect(airportInformationDAO.getAirportData(isA(ArrayList.class))).andReturn(testList).anyTimes();

        resultsPage.setAirportInformationDAO(airportInformationDAO);
        MapPage mapPage = new MapPage();
        resultsPage.setMapPage(mapPage);

        replay(airportInformationDAO);

        assertEquals(MapPage.class, resultsPage.viewMap(CODE1+","+CODE2).getClass());
        assertEquals(MapPage.class, resultsPage.onActionFromInViewMap(CODE1 + "," + CODE2).getClass());
        assertEquals(MapPage.class, resultsPage.onActionFromOutViewMap(CODE1 + "," + CODE2).getClass());

        assertEquals(resultsPage.getAirportInformationDAO(),airportInformationDAO);
        assertEquals(mapPage,resultsPage.getMapPage());

        verify(airportInformationDAO);
    }

    @Test
    public void testSegmentWindowDescription(){
        ResultsPage resultsPage = new ResultsPage();
        Segment segment = new Segment();

        assertEquals(String.class, resultsPage.segmentWindowDescription(segment, 0).getClass());
    }

    @Test
    public void testSetFlightResult(){
        ResultsPage resultsPage = new ResultsPage();

        FlightResult flightResult = new FlightResult();
        resultsPage.setFlightResult(flightResult);

        assertEquals(flightResult,resultsPage.getFlightResult());
        assertEquals(flightResult,resultsPage.getShowingFlightResult());
        assertEquals(flightResult,resultsPage.getFilteredFlightResult());
    }

    @Test
    public void testSetup(){
        ResultsPage resultsPage = new ResultsPage();

        FlightSearch flightSearch = new FlightSearch();
        FlightResult flightResult = new FlightResult();
        CustomPagedLoop customPagedLoop = new CustomPagedLoop();

        assertNull(resultsPage.getFlightResultFilter());

        resultsPage.setCustomPagedLoop(customPagedLoop);
        resultsPage.setup(flightSearch, flightResult);

        assertEquals(flightResult,resultsPage.getFlightResult());
        assertEquals(flightResult,resultsPage.getShowingFlightResult());
        assertEquals(flightResult,resultsPage.getFilteredFlightResult());
        assertEquals(flightResult,resultsPage.getFlightResult());
        assertEquals(1,resultsPage.getCustomPagedLoop().getCurrentPage());
        assertNotNull(resultsPage.getFlightResultFilter());

        assertNotNull(resultsPage.getFilterDescription());
    }

    @Test
    public void testFilter(){
        ResultsPage resultsPage = new ResultsPage();
        FlightResultFilterImpl flightResultFilter = new FlightResultFilterImpl();
        resultsPage.setFlightResultFilter(flightResultFilter);
        resultsPage.setSegmentFilterRadioSelectedValue(String.valueOf(Flight.ANY_SEGMENTS));
        assertEquals(String.valueOf(Flight.ANY_SEGMENTS),resultsPage.getSegmentFilterRadioSelectedValue());
        resultsPage.setFlightResult(new FlightResult());

        FlightSearch flightSearch = new FlightSearch();
        FlightResult flightResult = new FlightResult();
        CustomPagedLoop customPagedLoop = new CustomPagedLoop();

        resultsPage.setCustomPagedLoop(customPagedLoop);
        resultsPage.setup(flightSearch, flightResult);

        resultsPage.filterResults();
        assertTrue(resultsPage.getFlightResultFilter().getIsResultFiltered());
        resultsPage.onDisableFilter();
        assertFalse(resultsPage.getFlightResultFilter().getIsResultFiltered());
    }

    @Test
    public void testGetFlightArray(){
        ResultsPage resultsPage = new ResultsPage();
        FlightResult flightResult = new FlightResult();
        FlightSearch flightSearch = new FlightSearch();

        assertNotNull(resultsPage.getFlights());
        assertEquals(Flight[].class,resultsPage.getFlights().getClass());
        assertTrue(resultsPage.isEmptyResult());

        resultsPage.setFlightResult(flightResult);
        resultsPage.setFlightSearch(flightSearch);
        assertEquals(flightSearch,resultsPage.getFlightSearch());

        List<Flight> flights = resultsPage.getFlightResult().getFlights();

        flights.add(new Flight());
        flights.get(0).getOutboundRoutes().add(new Route());
        flights.get(0).getOutboundRoutes().get(0).getSegments().add(new Segment());


        assertNotNull(resultsPage.getFlights());
        resultsPage.getFlightSearch().setDirectFlight(true);
        assertNotNull(resultsPage.getFlights());

    }

    @Test
    public void testGetWindowNumbers(){
        ResultsPage resultsPage = new ResultsPage();

        int number = 0;
        resultsPage.setWindowNumber(number);

        assertEquals(String.valueOf(number-1),resultsPage.getStaticInWindowNumber());
        assertEquals(String.valueOf(number-1),resultsPage.getStaticOutWindowNumber());

        assertEquals(String.valueOf(number),resultsPage.getOutWindowNumber());

        assertEquals(String.valueOf(number),resultsPage.getStaticInWindowNumber());
        assertEquals(String.valueOf(number),resultsPage.getStaticOutWindowNumber());

        assertEquals(String.valueOf(number+1),resultsPage.getInWindowNumber());

        assertEquals(String.valueOf(number+1),resultsPage.getStaticInWindowNumber());
        assertEquals(String.valueOf(number+1),resultsPage.getStaticOutWindowNumber());

        assertEquals(String.valueOf(number+2),resultsPage.getOutWindowNumber());

        assertEquals(String.valueOf(number+2),resultsPage.getStaticInWindowNumber());
        assertEquals(String.valueOf(number+2),resultsPage.getStaticOutWindowNumber());

        assertEquals(String.valueOf(number+3),resultsPage.getOutWindowNumber());
        assertEquals(String.valueOf(number+4),resultsPage.getOutWindowNumber());
        assertEquals(String.valueOf(number+5),resultsPage.getOutWindowNumber());
    }


}
