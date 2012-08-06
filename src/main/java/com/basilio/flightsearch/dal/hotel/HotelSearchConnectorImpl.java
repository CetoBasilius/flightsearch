package com.basilio.flightsearch.dal.hotel;

import com.basilio.flightsearch.core.FlightResultCreator;
import com.basilio.flightsearch.core.HotelResultCreator;
import com.basilio.flightsearch.entities.flightresult.FlightResult;
import com.basilio.flightsearch.entities.hotelresult.HotelResult;
import com.basilio.flightsearch.entities.hotelresult.HotelSearch;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.GZIPInputStream;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 8/3/12
 * Time: 1:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class HotelSearchConnectorImpl implements HotelSearchConnector {

    private String apiTemplateHotelAddress = "http://api.despegar.com/availability/cities/";
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public HotelResult hotelSearch(HotelSearch hotelSearch) {
        String Statement = createStatement(hotelSearch.getCityCode(),hotelSearch.getCheckInDate(),hotelSearch.getCheckOutDate(),hotelSearch.getDistribution());
        return getHotelSearchResult(Statement);
    }

    private HotelResult getHotelSearchResult(String statement) {
        HotelResultCreator hotelResultCreator = new HotelResultCreator();
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(statement);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();

            InputStream instream = entity.getContent();

            GZIPInputStream zippedInputStream =  new GZIPInputStream(instream);
            BufferedReader reader = new BufferedReader(new InputStreamReader(zippedInputStream));

            hotelResultCreator.setResultString(reader.readLine());
            instream.close();
            httpclient.getConnectionManager().shutdown();

        } catch (IOException e) {
            e.printStackTrace();
        }

        HotelResult goodHotelResult = hotelResultCreator.getGoodHotelResult();

        return goodHotelResult;
    }

    private String createStatement(String City, Date dateIn, Date dateOut, String distribution){
        //Date format 2012-12-20
        //http://api.despegar.com/availability/cities/CITY/hotels?checkin=DATEIN&checkout=DATEOUT&distribution=DISTRIBUTION&pagesize=50;

        return apiTemplateHotelAddress+City+"hotels?checkin="+sdf.format(dateIn)+"&checkout="+sdf.format(dateOut)+"&distribution="+distribution+"&pagesize=50";
    }
}
