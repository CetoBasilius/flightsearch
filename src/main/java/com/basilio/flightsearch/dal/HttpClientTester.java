package com.basilio.flightsearch.dal;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

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
                do{
                    line = reader.readLine();
                    if(line!=null){
                        if(line.contains("<li>")){
                            System.out.println(line);
                        }
                    }
                }while(line != null);



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
        }
    }
}

