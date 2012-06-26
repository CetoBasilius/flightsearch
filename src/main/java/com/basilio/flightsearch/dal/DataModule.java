package com.basilio.flightsearch.dal;

import com.basilio.flightsearch.entities.Airport;
import com.basilio.flightsearch.entities.User;
import org.apache.tapestry5.ioc.annotations.Startup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Demo Data Loader
 * 
 * @author Basilio
 */
public class DataModule
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DataModule.class);

    private final ServiceDAO DAO;

    public DataModule(ServiceDAO DAO)
    {
        super();
        this.DAO = DAO;
    }

    @Startup
    public void initialize()
    {

        LOGGER.info("Loading initial demo data");
        List<User> users = new ArrayList<User>();

        users.add(new User("Basilio German", "cetobasilius", "basi@correo.com","cetobasilius"));
        users.add(new User("Demo person", "username", "user@correo.com", "password"));
        users.add(new User("Administrator", "admin", "admin@flightsearch.com", "admin",true));

        create(users);

        List<User> userList = DAO.findWithNamedQuery(User.ALL);
        LOGGER.info("Users " + userList);

        List<Airport> airports = new ArrayList<Airport>();

        airports.add(new Airport("HMO","Mexico","Hermosillo Sonora"));
        airports.add(new Airport("TUS","USA","Tucson Arizona"));
        airports.add(new Airport("NYC","USA","New York City"));
        airports.add(new Airport("MEX","Mexico","Mexico City"));
        airports.add(new Airport("LAX","USA","Los Angeles California"));
        airports.add(new Airport("SFO","USA","San Francisco California"));
        airports.add(new Airport("XEX","France","Paris"));
        airports.add(new Airport("BER","Germany","Berlin"));
        airports.add(new Airport("PGG","Brazil","Sao Paulo"));

        create(airports);

        List<Airport> airportList = DAO.findWithNamedQuery(Airport.ALL);
        LOGGER.info("Airports " + airportList);
        LOGGER.info("Data Loaded...");
    }

    private void create(List<?> entities)
    {
        for (Object e : entities)
        {
            DAO.create(e);
        }
    }
}
