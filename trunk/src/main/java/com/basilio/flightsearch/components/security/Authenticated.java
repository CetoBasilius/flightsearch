package com.basilio.flightsearch.components.security;

import com.basilio.flightsearch.services.Authenticator;

import org.apache.tapestry5.corelib.base.AbstractConditional;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * Checks if the user is logged in
 * 
 * @author Basilio
 */
public class Authenticated extends AbstractConditional
{

    @Inject
    private Authenticator authenticator;

    @Override
    protected boolean test()
    {
        return authenticator.isLoggedIn();
    }

}
