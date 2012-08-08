package com.basilio.flightsearch.components.security;

import com.basilio.flightsearch.services.Authenticator;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 8/8/12
 * Time: 10:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticatedTest {

    @Test
    public void testTest(){
        Authenticated authenticated = new Authenticated();
        Authenticator authenticator = createNiceMock(Authenticator.class);

        authenticated.setAuthenticator(authenticator);

        expect(authenticator.isLoggedIn()).andReturn(true);

        replay(authenticator);

        assertTrue(authenticated.test());
        assertEquals(authenticator,authenticated.getAuthenticator());

        verify(authenticator);
    }
}
