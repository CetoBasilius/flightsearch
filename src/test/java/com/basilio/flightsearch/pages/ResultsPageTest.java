package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.annotations.GuestAccess;
import com.basilio.flightsearch.entities.flightresult.FlightResult;
import com.basilio.flightsearch.entities.flightresult.FlightSearch;
import org.junit.Before;
import org.junit.Test;

import static org.easymock.EasyMock.createNiceMock;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 7/17/12
 * Time: 3:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class ResultsPageTest {

    private FlightSearch flightSearch;
    private FlightResult flightResult;
    private ResultsPage resultsPage;

    @Before
    public void setupTests(){
        flightSearch = createNiceMock(FlightSearch.class);
        flightResult = createNiceMock(FlightResult.class);

    }

    //TODO: fix these tests
    @Test
    public void testGuestAccess(){
        assertTrue(ResultsPage.class.isAnnotationPresent(GuestAccess.class));
    }
    /*
    @Test
     public void testGetFlights(){
        resultsPage = new ResultsPage();

        List<Flights> directFlightsList = createNiceMock(ArrayList.class);
        List<Flights> flightsList = createNiceMock(ArrayList.class);

        expect(search.isDirectFlight()).andReturn(false);
        expect(result.getFlights()).andReturn(flightsList).times(2);
        expect(flightsList.size()).andReturn(1);

        replay(search,result,flightsList);

        resultsPage.setResult(result);
        resultsPage.setSearch(search);
        resultsPage.getFlights();

        verify(search, result, flightsList);

    }

    @Test
    public void testGetDirectFlights(){
        resultsPage = new ResultsPage();

        List<Flights> directFlightsList = createNiceMock(ArrayList.class);
        List<Flights> flightsList = createNiceMock(ArrayList.class);

        expect(search.isDirectFlight()).andReturn(true);


        replay(search,result,directFlightsList);

        resultsPage.setResult(result);
        resultsPage.setSearch(search);
        resultsPage.getFlights();

        verify(search, result, directFlightsList);

    }*/
}
