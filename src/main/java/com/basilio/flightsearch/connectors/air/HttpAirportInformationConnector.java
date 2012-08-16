package com.basilio.flightsearch.connectors.air;

import com.basilio.flightsearch.core.AirportCreator;
import com.basilio.flightsearch.entities.AirportStub;
import com.basilio.flightsearch.entities.airport.Airport;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import static com.basilio.flightsearch.core.helpers.DomReaderHelper.readTag;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 7/2/12
 * Time: 4:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class HttpAirportInformationConnector implements AirportInformationConnector {

    public AirportStub getAirportData(AirportStub airportIn) {


        AirportStub returnAirportStub = new AirportStub();
        returnAirportStub.setCode(airportIn.getCode());
        returnAirportStub.setDescriptor(airportIn.getDescriptor());
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            HttpClient httpclient = new DefaultHttpClient();

            HttpGet httpget = new HttpGet("http://avdata.geekpilot.net/airport/" + airportIn.getCode() + ".xml");

            HttpResponse response = httpclient.execute(httpget);

            HttpEntity entity = response.getEntity();
            InputStream instream = entity.getContent();

            Document doc = dBuilder.parse(instream);
            doc.getDocumentElement().normalize();

            returnAirportStub.setDescriptor(readTag(doc, "name"));
            returnAirportStub.setLatitude(Float.valueOf(readTag(doc, "latitude")).floatValue());
            returnAirportStub.setLongitude(Float.valueOf(readTag(doc, "longitude")).floatValue());

            httpclient.getConnectionManager().shutdown();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException ee) {
            ee.printStackTrace();
        }
        return returnAirportStub;
    }

    public List<AirportStub> getAirportData(List<AirportStub> airportIn) {
        List<AirportStub> returnList = new ArrayList<AirportStub>();
        for (int index = 0; index < airportIn.size(); index++) {
            AirportStub newStub = getAirportData(airportIn.get(index));
            if (newStub.getLatitude() == 0) {
                newStub = getAirportDataSecondMethod(newStub.getCode());
            }
            returnList.add(newStub);
        }
        return returnList;
    }

    private AirportStub getAirportDataSecondMethod(String code) {

        AirportCreator resultCreator = new AirportCreator();
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet("http://api.despegar.com/airports/" + code);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();

            InputStream instream = entity.getContent();

            //Rest message is Gziped for despegar api
            GZIPInputStream zippedInputStream = new GZIPInputStream(instream);
            BufferedReader reader = new BufferedReader(new InputStreamReader(zippedInputStream));

            resultCreator.setAirportString(reader.readLine());

            instream.close();
            httpclient.getConnectionManager().shutdown();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Airport goodResult = resultCreator.getGoodAirport();

        AirportStub stub = new AirportStub();
        stub.setCode(goodResult.getId());
        stub.setDescriptor(goodResult.getDescription());
        stub.setLongitude(goodResult.getGeoLocation().getLongitude().floatValue());
        stub.setLatitude(goodResult.getGeoLocation().getLatitude().floatValue());

        return stub;

    }
}
