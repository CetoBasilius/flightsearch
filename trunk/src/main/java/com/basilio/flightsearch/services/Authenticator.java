package com.basilio.flightsearch.services;

import com.basilio.flightsearch.dal.ServiceDAO;
import com.basilio.flightsearch.entities.User;
import com.basilio.flightsearch.security.AuthenticationException;
import org.apache.tapestry5.services.Request;

/**
 * Basic security interface
 *
 */
public interface Authenticator
{

    /**
     * Gets the logged user
     * 
     * @return User, the logged User
     */
    User getLoggedUser();

    /**
     * Checks if the current user is logged in
     * 
     * @return true if the user is logged in
     */
    boolean isLoggedIn();

    /**
     * Logs the user.
     * 
     * @param username
     * @param password
     * @throws AuthenticationException
     *             throw if an error occurs
     */
    void login(String username, String password) throws AuthenticationException;

    /**
     * Logs out the user
     */
    void logout();

    /**
     * @return true if the logged user is an admin
     */
    public boolean isUserAdmin();

    /**
     * Sets the CRUD DAO
     *
     * @param service the Create Read Update and Delete service Data Access Object
     */
    public void setService(ServiceDAO service);

    public void setRequest(Request request);
}
