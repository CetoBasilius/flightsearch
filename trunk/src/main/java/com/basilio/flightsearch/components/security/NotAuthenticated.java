package com.basilio.flightsearch.components.security;

import com.basilio.flightsearch.services.Authenticator;
import org.apache.tapestry5.corelib.base.AbstractConditional;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 6/22/12
 * Time: 9:20 PM
 * used to check if user is not logged in
 */
public class NotAuthenticated extends AbstractConditional {
    @Inject
    private Authenticator authenticator;

    @Override
    protected boolean test()
    {
        return !authenticator.isLoggedIn();
    }
}
