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
 * Time: 10:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class AdministratorTest {
    @Test
    public void testTest() {
        Administrator admin = new Administrator();
        Authenticator authenticator = createNiceMock(Authenticator.class);
        admin.setAuthenticator(authenticator);

        expect(authenticator.isUserAdmin()).andReturn(true);

        replay(authenticator);

        assertTrue(admin.test());
        assertEquals(authenticator, admin.getAuthenticator());

        verify(authenticator);
    }
}
