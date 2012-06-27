package com.basilio.flightsearch.dal;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
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
 * User: Cetobasilius
 * Date: 6/26/12
 * Time: 3:10 AM
 * This is the demo httpclient application in a static method for testing purposes.
 */
public class HttpClientTester {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientTester.class);

    public static void testMethod() throws IOException {
        List<String> airportGet = new ArrayList<String>();

        LOGGER.info("--------------- HTTP Client ----------------------");

        HttpClient httpclient = new DefaultHttpClient();

        // Prepare a request object
        HttpGet httpget = new HttpGet("http://www.photius.com/wfb2001/airport_codes.html");

        // Execute the request
        HttpResponse response = httpclient.execute(httpget);

        // Examine the response status
        System.out.println(response.getStatusLine());

        // Get hold of the response entity
        HttpEntity entity = response.getEntity();

        // If the response does not enclose an entity, there is no need
        // to worry about connection release
        if (entity != null) {
            InputStream instream = entity.getContent();
            try {

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(instream));
                // do something useful with the response


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

                // In case of an IOException the connection will be released
                // back to the connection manager automatically
                throw ex;

            } catch (RuntimeException ex) {

                // In case of an unexpected exception you may want to abort
                // the HTTP request in order to shut down the underlying
                // connection and release it back to the connection manager.
                httpget.abort();
                throw ex;

            } finally {

                // Closing the input stream will trigger connection release
                instream.close();

            }

            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
            LOGGER.info("--------------- HTTP Client Shutdown ----------------------");

            int count = 0;
            for (String string : airportGet) {
                System.out.println(string);
                count++;
            }
            System.out.println(count + " airports");

            LOGGER.info("--------------- End of airport list ----------------------");

        }
    }
}

