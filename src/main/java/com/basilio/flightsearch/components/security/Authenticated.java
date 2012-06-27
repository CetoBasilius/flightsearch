package com.basilio.flightsearch.components.security;

import com.basilio.flightsearch.services.Authenticator;
import org.apache.tapestry5.corelib.base.AbstractConditional;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 6/22/12
 * Time: 12:35 PM
 * Used to check if user is logged in
 */

public class Authenticated extends AbstractConditional {
    @Inject
    private Authenticator authenticator;

    @Override
    protected boolean test() {
        return authenticator.isLoggedIn();
    }
}
