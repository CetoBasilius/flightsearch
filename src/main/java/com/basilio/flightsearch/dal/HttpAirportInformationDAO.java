package com.basilio.flightsearch.dal;

import com.basilio.flightsearch.dal.AirportInformationDAO;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.tapestry5.annotations.Log;
import org.mozilla.javascript.tools.shell.Global;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

import static com.basilio.flightsearch.dal.DomReader.readFile;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 7/2/12
 * Time: 4:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class HttpAirportInformationDAO implements AirportInformationDAO {

    public String getAirportData(String airportCode) {

        System.out.println("Searching for "+airportCode);
        String data = "empty";
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            HttpClient httpclient = new DefaultHttpClient();

            HttpGet httpget = new HttpGet("http://avdata.geekpilot.net/airport/"+airportCode+".xml");
            HttpResponse response = httpclient.execute(httpget);

            HttpEntity entity = response.getEntity();
            InputStream instream = entity.getContent();

            Document doc = dBuilder.parse(instream);
            doc.getDocumentElement().normalize();

            data = readFile(doc);

            httpclient.getConnectionManager().shutdown();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException ee) {
            ee.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return data;
    }


}
