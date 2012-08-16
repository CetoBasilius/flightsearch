package com.basilio.flightsearch.components.security;

import com.basilio.flightsearch.services.Authenticator;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 8/8/12
 * Time: 10:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class NotAuthenticatedTest {

    @Test
    public void testTest() {
        NotAuthenticated notAuthenticated = new NotAuthenticated();
        Authenticator authenticator = createNiceMock(Authenticator.class);

        notAuthenticated.setAuthenticator(authenticator);

        expect(authenticator.isLoggedIn()).andReturn(true);

        replay(authenticator);

        assertFalse(notAuthenticated.test());
        assertEquals(authenticator, notAuthenticated.getAuthenticator());

        verify(authenticator);
    }
}
