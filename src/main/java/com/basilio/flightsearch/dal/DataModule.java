package com.basilio.flightsearch.dal;

import com.basilio.flightsearch.entities.User;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Startup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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

    @Inject
    private AirportListConnector airportListConnector;

    private boolean useLocalDemoList = true;

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
            create(airportListConnector.GetAirportStubListLocal());
        }else{
            try {
                create(airportListConnector.GetAirportStubList());
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
    }

    private void create(List<?> entities) {
        for (Object thisEntity : entities) {
            serviceDAO.create(thisEntity);
        }
    }

}
