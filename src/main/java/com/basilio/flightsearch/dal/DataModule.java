package com.basilio.flightsearch.dal;

import com.basilio.flightsearch.entities.AirportStub;
import com.basilio.flightsearch.entities.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.tapestry5.ioc.annotations.Startup;
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
 * Date: 6/17/12
 * Time: 10:50 AM
 * This is the main Data module. For now, it loads the DEMO users and airports from
 */

public class DataModule {
    private static final Logger logger = LoggerFactory.getLogger(DataModule.class);

    private final ServiceDAO serviceDAO;

    private final static String airportListURL = "http://www.photius.com/wfb2001/airport_codes.html";

    private boolean useLocalDemoList = false;

    public DataModule(ServiceDAO serviceDAO) {
        super();
        this.serviceDAO = serviceDAO;
    }

    @Startup
    public void initialize() {
        logger.info("Loading initial demo data");
        createDemoUsers();
        createDemoAirportStubs();
        logger.info("Data Loaded...");
    }

    private void createDemoAirportStubs() {

        if(useLocalDemoList){
            createDemoAirportStubsLocal();
        }else{
            try {
                loadAirportStubsWithHTTP();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void createDemoUsers() {
        List<User> users = new ArrayList<User>();

        users.add(new User("Basilio German", "cetobasilius", "basi@correo.com", "cetobasilius"));
        users.add(new User("Demo person", "username", "user@correo.com", "password"));
        users.add(new User("Administrator", "admin", "admin@flightsearch.com", "admin", true));

        create(users);

        List<User> userList = serviceDAO.findWithNamedQuery(User.ALL);
        logger.info("Users " + userList);
    }

    private void loadAirportStubsWithHTTP() throws IOException {

        List<String> airportGet = new ArrayList<String>();

        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(airportListURL);
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
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

            httpclient.getConnectionManager().shutdown();

            List<AirportStub> airportStubs = new ArrayList<AirportStub>();

            for (String string : airportGet) {
                if(string.length()>6){
                    String code = string.substring(0,3);
                    String descriptor = string.substring(6);
                    AirportStub stub = new AirportStub(code,descriptor);
                    airportStubs.add(stub);
                }
            }
            create(airportStubs);
        }
    }

    private void createDemoAirportStubsLocal() {
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

        create(airportStubs);

        List<AirportStub> airportStubsList = serviceDAO.findWithNamedQuery(AirportStub.ALL);
        logger.info("Airport stubs " + airportStubsList);
    }

    private void create(List<?> entities) {
        for (Object thisEntity : entities) {
            serviceDAO.create(thisEntity);
        }
    }
}
