package com.basilio.flightsearch.dal;

import com.basilio.flightsearch.entities.ResultCreator;
import com.basilio.flightsearch.entities.Search;
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
    private String ApiTemplateRoundFlightAddress = "http://api.despegar.com/availability/flights/roundTrip/";

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Log
    public List<ResultCreator> searchOneWayFlights(Search search) {
        String statement = createStateOnewayStatement(search.getDepartureAirport().getCode(),
                                                      search.getDestinationAirport().getCode(),
                                                      search.getDepartureDate(),
                                                      search.getNumberAdults(),
                                                      search.getNumberChildren(),
                                                      search.getNewBorns());
        List<ResultCreator> resultingList = new ArrayList<ResultCreator>();

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(statement);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();

            InputStream instream = entity.getContent();
            try {
                //Rest message is Gziped for despegar api
                StringWriter responseBody = new StringWriter();
                PrintWriter responseWriter = new PrintWriter(responseBody);
                GZIPInputStream zippedInputStream =  new GZIPInputStream(instream);
                BufferedReader reader = new BufferedReader(new InputStreamReader(zippedInputStream));

                String line;
                ResultCreator result = new ResultCreator();
                List<String> resultList = new ArrayList<String>();
                do {
                    line = reader.readLine();
                    if (line != null) {
                        resultList.add(line);
                    }
                } while (line != null);
                result.setResult(resultList);
                resultingList.add(result);
            } catch (IOException ex) {
                throw ex;
            } catch (RuntimeException ex) {
                httpget.abort();
                throw ex;
            } finally {
                instream.close();
            }

            httpclient.getConnectionManager().shutdown();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultingList;
    }

    public ResultCreator searchMostEconomicFlight(Search search) {
        return null;
    }

    public ResultCreator searchMostFitFlight(Search search) {
        return null;
    }

    private String createStateOnewayStatement(String from,String to,Date departureDate, int adults, int children, int infants){
        //{from}/{to}/{departureDate}/{adults}/{children}/{infants}
        //Date on yyyy-MM-dd format
        String resultString = ApiTemplateOneWayFlightAddress+from+"/"+to+"/"+sdf.format(departureDate)+"/"+adults+"/"+children+"/"+infants;
        System.out.println(resultString);
        return resultString;
    }

    private String createStateRoundStatement(String from,String to,Date departureDate, Date returnDate, int adults, int children, int infants){
        //{from}/{to}/{departureDate}/{returningDate}/{adults}/{children}/{infants}
        return ApiTemplateOneWayFlightAddress+from+"/"+to+"/"+sdf.format(departureDate)+"/"+returnDate+"/"+adults+"/"+children+"/"+infants+"/";
    }
}
