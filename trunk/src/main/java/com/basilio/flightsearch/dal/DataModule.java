package com.basilio.flightsearch.dal;

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
 * This is the main Data module. For now, it loads the DEMO users and airports from
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
        logger.info("Loading initial demo data");
        try{
            createDemoUsers();
            createDemoAirportStubs();
        }catch(Exception e){}
        logger.info("Data Loaded...");
    }

    private void createDemoAirportStubs() {
        if(useLocalDemoList){
            create(airportListConnector.GetAirportStubListLocal());
        }else{
            try {
                List<AirportStub> entities = airportListConnector.GetAirportStubList();
                /*for(AirportStub airport : entities){
                    AirportStub stub = airportInformationDAO.getAirportData(airport.getCode());
                    airport.setLatitude(stub.getLatitude());
                    airport.setLongitude(stub.getLongitude());
                    if(airport.getLongitude()==0 || airport.getLatitude()==0){
                        logger.warn(airport.getCode()+" coordinate information not found");
                    }

                }*/
                create(entities);
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
            try{
                serviceDAO.create(thisEntity);
            }catch(Exception e){
                logger.error("Could not create "+thisEntity.toString());
            }
        }
    }

}
