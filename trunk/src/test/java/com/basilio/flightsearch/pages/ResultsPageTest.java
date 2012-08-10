package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.annotations.GuestAccess;
import com.basilio.flightsearch.components.CustomPagedLoop;
import com.basilio.flightsearch.core.FlightResultFilterImpl;
import com.basilio.flightsearch.dal.air.AirportInformationDAO;
import com.basilio.flightsearch.entities.AirportStub;
import com.basilio.flightsearch.entities.flightresult.Flight;
import com.basilio.flightsearch.entities.flightresult.FlightResult;
import com.basilio.flightsearch.entities.flightresult.FlightSearch;
import com.basilio.flightsearch.entities.flightresult.Segment;
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


}
