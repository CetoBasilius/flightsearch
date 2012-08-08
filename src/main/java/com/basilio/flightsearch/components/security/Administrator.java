package com.basilio.flightsearch.components.security;

import com.basilio.flightsearch.services.Authenticator;
import org.apache.tapestry5.corelib.base.AbstractConditional;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 6/23/12
 * Time: 12:50 PM
 * Used to check if the logged user is an administrator.
 */

public class Administrator extends AbstractConditional {

    @Inject
    private Authenticator authenticator;

    @Override
    protected boolean test() {
        return authenticator.isUserAdmin();
    }

    public Authenticator getAuthenticator() {
        return authenticator;
    }

    public void setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

}
