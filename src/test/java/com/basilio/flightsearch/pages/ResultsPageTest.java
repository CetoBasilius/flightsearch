package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.annotations.GuestAccess;
import com.basilio.flightsearch.components.CustomPagedLoop;
import com.basilio.flightsearch.core.FlightResultFilterImpl;
import com.basilio.flightsearch.connectors.air.AirportInformationConnector;
import com.basilio.flightsearch.entities.AirportStub;
import com.basilio.flightsearch.entities.flightresult.*;
import org.apache.tapestry5.annotations.Import;
import org.junit.Test;

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
    public void testAnotations() {
        assertTrue(ResultsPage.class.isAnnotationPresent(GuestAccess.class));
        assertTrue(ResultsPage.class.isAnnotationPresent(Import.class));
    }

    @Test
    public void testViewMap() {
        ResultsPage resultsPage = new ResultsPage();

        AirportInformationConnector airportInformationConnector = createNiceMock(AirportInformationConnector.class);

        List<AirportStub> testList = new ArrayList<AirportStub>();
        testList.add(new AirportStub(CODE1, ""));
        testList.add(new AirportStub(CODE2, ""));

        expect(airportInformationConnector.getAirportData(isA(ArrayList.class))).andReturn(testList).anyTimes();

        resultsPage.setAirportInformationConnector(airportInformationConnector);
        MapPage mapPage = new MapPage();
        resultsPage.setMapPage(mapPage);

        replay(airportInformationConnector);

        assertEquals(MapPage.class, resultsPage.viewMap(CODE1 + "," + CODE2).getClass());
        assertEquals(MapPage.class, resultsPage.onActionFromInViewMap(CODE1 + "," + CODE2).getClass());
        assertEquals(MapPage.class, resultsPage.onActionFromOutViewMap(CODE1 + "," + CODE2).getClass());

        assertEquals(resultsPage.getAirportInformationConnector(), airportInformationConnector);
        assertEquals(mapPage, resultsPage.getMapPage());

        verify(airportInformationConnector);
    }

    @Test
    public void testSegmentWindowDescription() {
        ResultsPage resultsPage = new ResultsPage();
        Segment segment = new Segment();

        assertEquals(String.class, resultsPage.segmentWindowDescription(segment, 0).getClass());
    }

    @Test
    public void testSetFlightResult() {
        ResultsPage resultsPage = new ResultsPage();

        FlightResult flightResult = new FlightResult();
        resultsPage.setFlightResult(flightResult);

        assertEquals(flightResult, resultsPage.getFlightResult());
        assertEquals(flightResult, resultsPage.getShowingFlightResult());
        assertEquals(flightResult, resultsPage.getFilteredFlightResult());
    }

    @Test
    public void testSetupRender() {
        ResultsPage resultsPage = new ResultsPage();
    }

    @Test
    public void testSetup() {
        ResultsPage resultsPage = new ResultsPage();

        FlightSearch flightSearch = new FlightSearch();
        FlightResult flightResult = new FlightResult();
        CustomPagedLoop customPagedLoop = new CustomPagedLoop();

        assertNull(resultsPage.getFlightResultFilter());

        resultsPage.setCustomPagedLoop(customPagedLoop);
        resultsPage.setup(flightSearch, flightResult);

        assertEquals(flightResult, resultsPage.getFlightResult());
        assertEquals(flightResult, resultsPage.getShowingFlightResult());
        assertEquals(flightResult, resultsPage.getFilteredFlightResult());
        assertEquals(flightResult, resultsPage.getFlightResult());
        assertEquals(1, resultsPage.getCustomPagedLoop().getCurrentPage());
        assertNotNull(resultsPage.getFlightResultFilter());

        assertNotNull(resultsPage.getFilterDescription());
    }

    @Test
    public void testFilter() {
        ResultsPage resultsPage = new ResultsPage();
        FlightResultFilterImpl flightResultFilter = new FlightResultFilterImpl();
        resultsPage.setFlightResultFilter(flightResultFilter);
        resultsPage.setSegmentFilterRadioSelectedValueString(String.valueOf(Flight.ANY_SEGMENTS));
        assertEquals(String.valueOf(Flight.ANY_SEGMENTS), resultsPage.getSegmentFilterRadioSelectedValueString());
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
    public void testGetFlightArray() {
        ResultsPage resultsPage = new ResultsPage();
        FlightResult flightResult = new FlightResult();
        FlightSearch flightSearch = new FlightSearch();

        assertNotNull(resultsPage.getFlights());
        assertEquals(Flight[].class, resultsPage.getFlights().getClass());

        resultsPage.setFlightResult(flightResult);
        resultsPage.setFlightSearch(flightSearch);
        assertEquals(flightSearch, resultsPage.getFlightSearch());

        List<Flight> flights = resultsPage.getFlightResult().getFlights();

        flights.add(new Flight());
        flights.get(0).getOutboundRoutes().add(new Route());
        flights.get(0).getOutboundRoutes().get(0).getSegments().add(new Segment());


        assertNotNull(resultsPage.getFlights());
        resultsPage.getFlightSearch().setDirectFlight(true);
        assertNotNull(resultsPage.getFlights());

    }

    @Test
    public void testGetWindowNumbers() {
        ResultsPage resultsPage = new ResultsPage();

        int number = 0;
        resultsPage.setWindowNumber(number);

        assertEquals(String.valueOf(number - 1), resultsPage.getStaticInWindowNumber());
        assertEquals(String.valueOf(number - 1), resultsPage.getStaticOutWindowNumber());

        assertEquals(String.valueOf(number), resultsPage.getOutWindowNumber());

        assertEquals(String.valueOf(number), resultsPage.getStaticInWindowNumber());
        assertEquals(String.valueOf(number), resultsPage.getStaticOutWindowNumber());

        assertEquals(String.valueOf(number + 1), resultsPage.getInWindowNumber());

        assertEquals(String.valueOf(number + 1), resultsPage.getStaticInWindowNumber());
        assertEquals(String.valueOf(number + 1), resultsPage.getStaticOutWindowNumber());

        assertEquals(String.valueOf(number + 2), resultsPage.getOutWindowNumber());

        assertEquals(String.valueOf(number + 2), resultsPage.getStaticInWindowNumber());
        assertEquals(String.valueOf(number + 2), resultsPage.getStaticOutWindowNumber());

        assertEquals(String.valueOf(number + 3), resultsPage.getOutWindowNumber());
        assertEquals(String.valueOf(number + 4), resultsPage.getOutWindowNumber());
        assertEquals(String.valueOf(number + 5), resultsPage.getOutWindowNumber());
    }

    @Test
    public void testGetOutSegmentsArray(){
        ResultsPage resultsPage = new ResultsPage();
        Route route = new Route();
        List<Segment> segments = new ArrayList<Segment>();
        segments.add(new Segment());
        segments.add(new Segment());
        segments.add(new Segment());
        route.setSegments(segments);
        resultsPage.setOutboundRouteValue(route);

        assertEquals(route, resultsPage.getOutboundRouteValue());
        assertEquals(segments.toArray(new Segment[0]),resultsPage.getOutSegments());
    }

    @Test
    public void testGetInSegmentsArray(){
        ResultsPage resultsPage = new ResultsPage();
        Route route = new Route();
        List<Segment> segments = new ArrayList<Segment>();
        segments.add(new Segment());
        segments.add(new Segment());
        segments.add(new Segment());
        route.setSegments(segments);
        resultsPage.setInboundRouteValue(route);

        assertEquals(route, resultsPage.getInboundRouteValue());
        assertEquals(segments.toArray(new Segment[0]),resultsPage.getInSegments());

    }

    @Test
    public void testGetOutRoutesArray(){
        ResultsPage resultsPage = new ResultsPage();
        Flight flight = new Flight();
        List<Route> routes = new ArrayList<Route>();
        routes.add(new Route());
        routes.add(new Route());
        routes.add(new Route());
        flight.setOutboundRoutes(routes);
        resultsPage.setFlightValue(flight);

        assertEquals(flight,resultsPage.getFlightValue());
        assertEquals(routes.toArray(new Route[0]),resultsPage.getOutRoutes());
    }

    @Test
    public void testGetInRoutesArray(){
        ResultsPage resultsPage = new ResultsPage();
        Flight flight = new Flight();
        List<Route> routes = new ArrayList<Route>();
        routes.add(new Route());
        routes.add(new Route());
        routes.add(new Route());
        flight.setInboundRoutes(routes);
        resultsPage.setFlightValue(flight);

        assertEquals(flight,resultsPage.getFlightValue());
        assertEquals(routes.toArray(new Route[0]),resultsPage.getInRoutes());

    }

}
