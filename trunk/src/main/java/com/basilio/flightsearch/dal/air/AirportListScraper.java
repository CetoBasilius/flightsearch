package com.basilio.flightsearch.dal.air;

import com.basilio.flightsearch.entities.AirportStub;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.tapestry5.annotations.Log;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/5/12
 * Time: 3:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class AirportListScraper implements AirportListConnector {

    private static final Logger logger = LoggerFactory.getLogger(AirportListScraper.class);

    private final static String airportListURL = "http://www.photius.com/wfb2001/airport_codes.html";

    public List<AirportStub> GetAirportStubList() throws IOException {

        List<AirportStub> airportStubs = new ArrayList<AirportStub>();

        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(airportListURL);
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            List<String> airportGet = parseHtml(httpget, entity);
            airportStubs = createAirportStubList(airportGet);
        }

        httpclient.getConnectionManager().shutdown();
        return airportStubs;
    }

    List<AirportStub> createAirportStubList( List<String> airportGet) {
        List<AirportStub> airportStubs = new ArrayList<AirportStub>();
        for (String string : airportGet) {
            if(string.length()>6){
                String code = string.substring(0,3);
                String descriptor = string.substring(6);
                AirportStub stub = new AirportStub(code,descriptor);
                airportStubs.add(stub);
            }
        }
        return airportStubs;
    }

    List<String> parseHtml(HttpGet httpget, HttpEntity entity) throws IOException {
        List<String> airportGet = new ArrayList<String>();
        InputStream instream = entity.getContent();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
            String line;
            do {
                line = reader.readLine();
                if (line != null) {
                    if (line.contains("<li>")) {
                        int count = StringUtils.countMatches(line, "<li>");
                        if (count > 1) {
                            String[] splitLine = line.split("<li>");
                            for (int a = 1; a < splitLine.length; a++) {
                                airportGet.add(Jsoup.parse(splitLine[a]).text());
                            }
                        } else {
                            airportGet.add(Jsoup.parse(line).text());
                        }
                    }
                }
            } while (line != null);
        } catch (IOException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            httpget.abort();
            throw ex;
        } finally {
            instream.close();
        }
        return airportGet;
    }

    public List<AirportStub> GetAirportStubListLocal() {
        List<AirportStub> airportStubs = new ArrayList<AirportStub>();

        airportStubs.add(new AirportStub("HMO", "Mexico - Hermosillo Sonora"));
        airportStubs.add(new AirportStub("TUS", "USA - Tucson Arizona"));
        airportStubs.add(new AirportStub("NYC", "USA - New York City"));
        airportStubs.add(new AirportStub("MEX", "Mexico - Mexico City"));
        airportStubs.add(new AirportStub("LAX", "USA - Los Angeles California"));
        airportStubs.add(new AirportStub("SFO", "USA - San Francisco California"));
        airportStubs.add(new AirportStub("XEX", "France - Paris"));
        airportStubs.add(new AirportStub("BER", "Germany - Berlin"));
        airportStubs.add(new AirportStub("PGG", "Brazil - Sao Paulo"));
        airportStubs.add(new AirportStub("PAR", "Paris - France"));

        return airportStubs;
    }
}
