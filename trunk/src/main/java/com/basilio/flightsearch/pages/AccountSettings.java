package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.dal.ServiceDAO;
import com.basilio.flightsearch.entities.User;
import com.basilio.flightsearch.services.Authenticator;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 6/22/12
 * Time: 11:07 AM
 * To change this template use File | AccountSettings | File Templates.
 */
public class AccountSettings {

    @Inject
    private Session session;

    @Inject
    private ServiceDAO serviceDAO;

    @Inject
    Authenticator authenticator;

    @Property
    private User user;

    public List<User> getUserlist()
    {
        List<User> userList = serviceDAO.findWithNamedQuery(User.ALL);

        return userList;//session.createCriteria(User.class).list();
    }

    public Object onActionFromDelete(long userId)
    {
        if(authenticator.getLoggedUser().getId() == userId){
            authenticator.logout();
        }
        serviceDAO.delete(User.class,userId);
        return(AccountSettings.class);
    }

}
