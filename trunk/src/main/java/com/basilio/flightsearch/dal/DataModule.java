package com.basilio.flightsearch.dal;

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

        List<User> users = new ArrayList<User>();

        users.add(new User("Basilio German", "cetobasilius", "basi@correo.com",
                "cetobasilius"));
        users.add(new User("Demo person", "username", "user@correo.com", "password"));

        LOGGER.info("Loading initial demo data");
        create(users);

        List<User> userList = DAO.findWithNamedQuery(User.ALL);
        LOGGER.info("Users " + userList);
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
