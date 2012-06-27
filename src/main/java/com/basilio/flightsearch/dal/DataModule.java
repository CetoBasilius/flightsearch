package com.basilio.flightsearch.dal;

import com.basilio.flightsearch.entities.Airport;
import com.basilio.flightsearch.entities.User;
import org.apache.tapestry5.ioc.annotations.Startup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 6/17/12
 * Time: 10:50 AM
 * This is the main Data module. For now, it loads the DEMO users and airports.
 */

public class DataModule {
    private static final Logger logger = LoggerFactory.getLogger(DataModule.class);

    private final ServiceDAO serviceDAO;

    public DataModule(ServiceDAO serviceDAO) {
        super();
        this.serviceDAO = serviceDAO;
    }

    @Startup
    public void initialize() {
        logger.info("Loading initial demo data");
        createDemoUsers();
        createDemoAirports();
        logger.info("Data Loaded...");
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

    private void createDemoAirports() {
        List<Airport> airports = new ArrayList<Airport>();

        airports.add(new Airport("HMO", "Mexico", "Hermosillo Sonora"));
        airports.add(new Airport("TUS", "USA", "Tucson Arizona"));
        airports.add(new Airport("NYC", "USA", "New York City"));
        airports.add(new Airport("MEX", "Mexico", "Mexico City"));
        airports.add(new Airport("LAX", "USA", "Los Angeles California"));
        airports.add(new Airport("SFO", "USA", "San Francisco California"));
        airports.add(new Airport("XEX", "France", "Paris"));
        airports.add(new Airport("BER", "Germany", "Berlin"));
        airports.add(new Airport("PGG", "Brazil", "Sao Paulo"));

        create(airports);

        List<Airport> airportList = serviceDAO.findWithNamedQuery(Airport.ALL);
        logger.info("Airports " + airportList);
    }

    private void create(List<?> entities) {
        for (Object thisEntity : entities) {
            serviceDAO.create(thisEntity);
        }
    }
}
