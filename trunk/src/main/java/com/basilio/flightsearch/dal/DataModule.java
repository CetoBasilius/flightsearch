package com.basilio.flightsearch.dal;

import com.basilio.flightsearch.dal.air.AirportInformationDAO;
import com.basilio.flightsearch.dal.air.AirportListConnector;
import com.basilio.flightsearch.dal.persistence.ServiceDAO;
import com.basilio.flightsearch.entities.AirportStub;
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
 * This is the main Data module.
 */

public class DataModule {

    private static final Logger logger = LoggerFactory.getLogger(DataModule.class);

    private final ServiceDAO serviceDAO;

    @Inject
    private AirportListConnector airportListConnector;

    @Inject
    private AirportInformationDAO airportInformationDAO;

    private boolean useLocalDemoList = false;

    public DataModule(ServiceDAO serviceDAO) {
        super();
        this.serviceDAO = serviceDAO;
    }

    @Startup
    public void initialize() {
        String loadingString = "Loading initial demo data";
        logger.info(loadingString);

        try{
            createDemoUsers();
            createDemoAirportStubs();
        }catch(Exception e){}
        String loadedCompleteString = "Data Loaded...";
        logger.info(loadedCompleteString);

    }

    private void createDemoAirportStubs() {
        if(useLocalDemoList){
            create(airportListConnector.GetAirportStubListLocal());
        }else{
            try {
                List<AirportStub> entities = airportListConnector.GetAirportStubList();
                create(entities);
            } catch (IOException e) {
                logger.error("Could not retrieve airport list, make sure there is an active internet connection.");
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
            try{
                String attemptString = "Attempting to create " + thisEntity.toString();
                logger.info(attemptString);

                serviceDAO.create(thisEntity);
            }catch(Exception e){
                logger.error("Could not create "+thisEntity.toString());
            }
        }
    }

}
