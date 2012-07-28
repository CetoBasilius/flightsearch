package com.basilio.flightsearch.services;

import com.basilio.flightsearch.dal.QueryParameters;
import com.basilio.flightsearch.dal.ServiceDAO;
import com.basilio.flightsearch.entities.User;
import com.basilio.flightsearch.security.AuthenticationException;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Session;
import org.junit.Before;
import org.testng.annotations.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 6/22/12
 * Time: 5:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticatorImplTest {

    public static final String AUTHORIZATION_TOKEN = "loggedUserToken";
    private final String username = "myusername";
    private final String password = "mypassword";


    @Test
    public void loginShouldLoginUser() throws AuthenticationException {
        Authenticator authenticator = new AuthenticatorImpl();;
        Request request = createNiceMock(Request.class);;
        ServiceDAO serviceDAO = createNiceMock(ServiceDAO.class);;
        User user = createNiceMock(User.class);;
        Session session = createNiceMock(Session.class);;

        authenticator.setRequest(request);
        authenticator.setService(serviceDAO);

        expect(serviceDAO.findUniqueWithNamedQuery(User.BY_CREDENTIALS, QueryParameters.with(
                "username",
                username).and("password", password).parameters())).andReturn(user);
        expect(request.getSession(true)).andReturn(session);
        session.setAttribute(AUTHORIZATION_TOKEN, user);
        expectLastCall();

        replay(request, serviceDAO, session);

        authenticator.login(username,password);

        verify(request,serviceDAO, session);

    }


    @Test
    public void getLoggedUserShouldReturnLoggedUser() throws AuthenticationException {
        Authenticator authenticator = new AuthenticatorImpl();;
        Request request = createNiceMock(Request.class);;
        ServiceDAO serviceDAO = createNiceMock(ServiceDAO.class);;
        User user = createNiceMock(User.class);;
        Session session = createNiceMock(Session.class);;

        authenticator.setRequest(request);
        authenticator.setService(serviceDAO);

        expect(request.getSession(false)).andReturn(session);
        expect(session.getAttribute(AUTHORIZATION_TOKEN)).andReturn(user);
        expect(request.getSession(true)).andReturn(session);
        expect(session.getAttribute(AUTHORIZATION_TOKEN)).andReturn(user);

        replay(request, serviceDAO, session);

        assertEquals(user,authenticator.getLoggedUser());

        verify(request,serviceDAO, session);

    }

    @Test
    public void getLoggedUserShouldBeAdmin() throws AuthenticationException {
        Authenticator authenticator = new AuthenticatorImpl();;
        Request request = createNiceMock(Request.class);;
        ServiceDAO serviceDAO = createNiceMock(ServiceDAO.class);;
        User user = createNiceMock(User.class);;
        Session session = createNiceMock(Session.class);;

        authenticator.setRequest(request);
        authenticator.setService(serviceDAO);

        expect(request.getSession(false)).andReturn(session);
        expect(session.getAttribute(AUTHORIZATION_TOKEN)).andReturn(user);
        expect(request.getSession(true)).andReturn(session);
        expect(session.getAttribute(AUTHORIZATION_TOKEN)).andReturn(user);

        expect(user.isAdmin()).andReturn(true);

        replay(request, serviceDAO, session, user);

        assertEquals(true,authenticator.isUserAdmin());

        verify(request,serviceDAO, session, user);

    }
}
