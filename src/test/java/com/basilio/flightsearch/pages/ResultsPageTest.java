package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.entities.Search;
import com.basilio.flightsearch.entities.result.Flights;
import com.basilio.flightsearch.entities.result.Result;
import com.basilio.flightsearch.services.Authenticator;
import org.apache.tapestry5.services.ComponentSource;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.Response;
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
 * Time: 3:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class ResultsPageTest {

    private Search search;
    private Result result;
    private ResultsPage resultsPage;

    @Before
    public void setupTests(){
        search = createNiceMock(Search.class);
        result = createNiceMock(Result.class);

    }

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
        expect(result.getDirectFlights()).andReturn(directFlightsList).times(2);
        expect(directFlightsList.size()).andReturn(1);

        replay(search,result,directFlightsList);

        resultsPage.setResult(result);
        resultsPage.setSearch(search);
        resultsPage.getFlights();

        verify(search, result, directFlightsList);

    }
}
