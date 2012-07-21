package com.basilio.flightsearch.dal;

import com.basilio.flightsearch.entities.ResultCreator;
import com.basilio.flightsearch.entities.Search;
import com.basilio.flightsearch.entities.result.Result;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.tapestry5.annotations.Log;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 7/5/12
 * Time: 8:54 PM
 *
 */
public class FlightSearchConnectorImpl implements  FlightSearchConnector {

    private String ApiTemplateOneWayFlightAddress = "http://api.despegar.com/availability/flights/oneWay/";
    private String ApiTemplateRoundFlightAddress =  "http://api.despegar.com/availability/flights/roundTrip/";
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private Search search;

    private Result getFlightSearchResult(String statement) {
        ResultCreator resultCreator = new ResultCreator();
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(statement);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();

            InputStream instream = entity.getContent();

            //Rest message is Gziped for despegar api
            GZIPInputStream zippedInputStream =  new GZIPInputStream(instream);
            BufferedReader reader = new BufferedReader(new InputStreamReader(zippedInputStream));

            resultCreator.setResultString(reader.readLine());
            instream.close();
            httpclient.getConnectionManager().shutdown();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Result goodResult = resultCreator.getGoodResult();
        System.out.println(this.search);
        goodResult.setSearchedPrice(this.search.getBudgetDollars());
        return goodResult;
    }

    public Result searchFlights(Search search) {
        this.search = search;
        String statement = "";
        if(search.isRoundTrip()){
            statement = createRoundStatement(search.getDepartureAirport().getCode(),
                    search.getDestinationAirport().getCode(),
                    search.getDepartureDate(),
                    search.getReturnDate(),
                    search.getNumberAdults(),
                    search.getNumberChildren(),
                    search.getNewBorns());
        } else {
            statement = createOnewayStatement(search.getDepartureAirport().getCode(),
                    search.getDestinationAirport().getCode(),
                    search.getDepartureDate(),
                    search.getNumberAdults(),
                    search.getNumberChildren(),
                    search.getNewBorns());
        }

        return getFlightSearchResult(statement);
    }

    private String createOnewayStatement(String from,String to,Date departureDate, int adults, int children, int infants){
        //{from}/{to}/{departureDate}/{adults}/{children}/{infants}
        //Date on yyyy-MM-dd format
        String resultString = ApiTemplateOneWayFlightAddress+from+"/"+to+"/"+sdf.format(departureDate)+"/"+adults+"/"+children+"/"+infants+"?pagesize=50";
        System.out.println(resultString);
        return resultString;
    }

    private String createRoundStatement(String from,String to,Date departureDate, Date returnDate, int adults, int children, int infants){
        //{from}/{to}/{departureDate}/{returningDate}/{adults}/{children}/{infants}
        //"http://api.despegar.com/availability/flights/roundTrip/HMO/MEX/2012-07-23/2012-07-27/1/0/0"
        String s = ApiTemplateRoundFlightAddress + from + "/" + to + "/" + sdf.format(departureDate) + "/" + sdf.format(returnDate) + "/" + adults + "/" + children + "/" + infants  + "?pagesize=50";
        System.out.println(s);
        return s;
    }

}
