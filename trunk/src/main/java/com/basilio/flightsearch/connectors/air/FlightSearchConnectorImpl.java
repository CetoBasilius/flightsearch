package com.basilio.flightsearch.connectors.air;

import com.basilio.flightsearch.core.FlightResultCreator;
import com.basilio.flightsearch.entities.flightresult.FlightResult;
import com.basilio.flightsearch.entities.flightresult.FlightSearch;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.GZIPInputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 7/5/12
 * Time: 8:54 PM
 */
public class FlightSearchConnectorImpl implements FlightSearchConnector {

    private static final Logger logger = LoggerFactory.getLogger(FlightSearchConnectorImpl.class);


    private String ApiTemplateOneWayFlightAddress = "http://api.despegar.com/availability/flights/oneWay/";
    private String ApiTemplateRoundFlightAddress = "http://api.despegar.com/availability/flights/roundTrip/";
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private FlightSearch flightSearch;

    private FlightResult getFlightSearchResult(String statement) {
        FlightResultCreator flightResultCreator = new FlightResultCreator();
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(statement);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();

            InputStream instream = entity.getContent();

            //Rest message is Gziped for despegar api
            GZIPInputStream zippedInputStream = new GZIPInputStream(instream);
            BufferedReader reader = new BufferedReader(new InputStreamReader(zippedInputStream));

            flightResultCreator.setResultString(reader.readLine());
            instream.close();
            httpclient.getConnectionManager().shutdown();

        } catch (IOException e) {
            logger.error("Could not reach flight result service");
        }

        FlightResult goodFlightResult = flightResultCreator.getGoodResult();
        if (goodFlightResult == null) {
            goodFlightResult = new FlightResult();
        }

        goodFlightResult.setSearchedPrice(this.flightSearch.getBudgetDollars());
        goodFlightResult.setSearchedDirect(this.flightSearch.isDirectFlight());
        return goodFlightResult;
    }

    public FlightResult searchFlights(FlightSearch flightSearch) {
        this.flightSearch = flightSearch;
        String statement = "";
        if (flightSearch.isRoundTrip()) {
            statement = createRoundStatement(flightSearch.getDepartureAirport().getCode(),
                    flightSearch.getDestinationAirport().getCode(),
                    flightSearch.getDepartureDate(),
                    flightSearch.getReturnDate(),
                    flightSearch.getNumberAdults(),
                    flightSearch.getNumberChildren(),
                    flightSearch.getNewBorns());
        } else {
            statement = createOnewayStatement(flightSearch.getDepartureAirport().getCode(),
                    flightSearch.getDestinationAirport().getCode(),
                    flightSearch.getDepartureDate(),
                    flightSearch.getNumberAdults(),
                    flightSearch.getNumberChildren(),
                    flightSearch.getNewBorns());
        }

        return getFlightSearchResult(statement);
    }

    private String createOnewayStatement(String from, String to, Date departureDate, int adults, int children, int infants) {
        //{from}/{to}/{departureDate}/{adults}/{children}/{infants}
        //Date on yyyy-MM-dd format
        String resultString = ApiTemplateOneWayFlightAddress + from + "/" + to + "/" + sdf.format(departureDate) + "/" + adults + "/" + children + "/" + infants + "?pagesize=50";

        return resultString;
    }

    private String createRoundStatement(String from, String to, Date departureDate, Date returnDate, int adults, int children, int infants) {
        //{from}/{to}/{departureDate}/{returningDate}/{adults}/{children}/{infants}
        //"http://api.despegar.com/availability/flights/roundTrip/HMO/MEX/2012-07-23/2012-07-27/1/0/0"
        String s = ApiTemplateRoundFlightAddress + from + "/" + to + "/" + sdf.format(departureDate) + "/" + sdf.format(returnDate) + "/" + adults + "/" + children + "/" + infants + "?pagesize=50";

        return s;
    }

}
