package com.basilio.flightsearch.services;


import com.basilio.flightsearch.dal.CRUDServiceDAO;
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
public class BasicAuthenticator implements Authenticator
{
    public static final String AUTH_TOKEN = "authToken";

    @Inject
    private CRUDServiceDAO CRUDService;

    @Inject
    private Request request;


    public void login(String username, String password) throws AuthenticationException
    {

        System.out.println(User.BY_CREDENTIALS+""+QueryParameters.with("username",username).and("password", password).parameters());
        User user = CRUDService.findUniqueWithNamedQuery(User.BY_CREDENTIALS, QueryParameters.with(
                "username",
                username).and("password", password).parameters());

        System.out.println(username+","+password);

        List<User> userList = CRUDService.findWithNamedQuery(User.ALL);
        System.out.println("Users " + userList);

        if (user == null)
        {
            System.out.println("something went wrong");
            throw new AuthenticationException("The user doesn't exist");
        }

        request.getSession(true).setAttribute(AUTH_TOKEN, user);

    }

    public boolean isLoggedIn()
    {
        Session session = request.getSession(false);
        if (session != null) { return session.getAttribute(AUTH_TOKEN) != null; }
        return false;
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


}
