package com.basilio.flightsearch.services;


import com.basilio.flightsearch.dal.ServiceDAO;
import com.basilio.flightsearch.dal.QueryParameters;
import com.basilio.flightsearch.entities.User;
import com.basilio.flightsearch.security.AuthenticationException;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Session;

import java.util.List;

/**
 * Basic Security Realm implementation
 * 
 * @author Basilio
 * @version 1.0
 */
public class AuthenticatorImpl implements Authenticator
{
    public static final String AUTH_TOKEN = "authToken";

    @Inject
    private ServiceDAO Service;

    @Inject
    private Request request;

    public void login(String username, String password) throws AuthenticationException
    {
        User user = Service.findUniqueWithNamedQuery(User.BY_CREDENTIALS, QueryParameters.with(
                "username",
                username).and("password", password).parameters());

        List<User> userList = Service.findWithNamedQuery(User.ALL);

        if (user == null)
        {
            throw new AuthenticationException("The user doesn't exist");
        }

        request.getSession(true).setAttribute(AUTH_TOKEN, user);
    }

    public void logout()
    {
        Session session = request.getSession(false);
        if (session != null)
        {
            session.setAttribute(AUTH_TOKEN, null);
            session.invalidate();
        }
    }

    public boolean isLoggedIn()
    {
        Session session = request.getSession(false);
        if (session != null) {
            if(session.getAttribute(AUTH_TOKEN) != null){
                return true;
            }
        }
        return false;
    }

    public boolean isUserAdmin()
    {
        User user = null;
        user = getLoggedUser();
        if(user != null){
            if(user.isAdmin()){
                return true;
            }
        }
        return false;
    }

    public User getLoggedUser()
    {
        User user = null;

        if (isLoggedIn())
        {
            user = (User) request.getSession(true).getAttribute(AUTH_TOKEN);
        }
        else
        {
            throw new IllegalStateException("The user is not logged ! ");
        }
        return user;
    }

    public void setService(ServiceDAO service) {
        Service = service;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
