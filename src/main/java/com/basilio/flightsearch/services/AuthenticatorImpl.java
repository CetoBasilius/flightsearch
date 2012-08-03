package com.basilio.flightsearch.services;


import com.basilio.flightsearch.dal.persistence.QueryParameters;
import com.basilio.flightsearch.dal.persistence.ServiceDAO;
import com.basilio.flightsearch.entities.User;
import com.basilio.flightsearch.security.AuthenticationException;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Session;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 6/15/12
 * Time: 9:27 AM
 * Basic Security implementation.
 *
 */
public class AuthenticatorImpl implements Authenticator {
    public static final String AUTHORIZATION_TOKEN = "loggedUserToken";

    @Inject
    private ServiceDAO serviceDAO;

    @Inject
    private Request request;

    public void login(String username, String password) throws AuthenticationException {
        User user = serviceDAO.findUniqueWithNamedQuery(User.BY_CREDENTIALS, QueryParameters.with(
                "username",
                username).and("password", password).parameters());

        if (user == null) {
            throw new AuthenticationException("The user doesn't exist");
        }

        request.getSession(true).setAttribute(AUTHORIZATION_TOKEN, user);
    }

    public void logout() {
        Session session = request.getSession(false);
        if (session != null) {
            session.setAttribute(AUTHORIZATION_TOKEN, null);
            session.invalidate();
        }
    }

    public boolean isLoggedIn() {
        Session session = request.getSession(false);
        if (session != null) {
            if (session.getAttribute(AUTHORIZATION_TOKEN) != null) {
                return true;
            }
        }
        return false;
    }

    public boolean isUserAdmin() {
        User user = null;
        user = getLoggedUser();
        if (user != null) {
            if (user.isAdmin()) {
                return true;
            }
        }
        return false;
    }

    public User getLoggedUser() {
        User user = null;

        if (isLoggedIn()) {
            user = (User) request.getSession(true).getAttribute(AUTHORIZATION_TOKEN);
        } else {
            throw new IllegalStateException("The user is not logged ! ");
        }
        return user;
    }

    public void setService(ServiceDAO service) {
        serviceDAO = service;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
