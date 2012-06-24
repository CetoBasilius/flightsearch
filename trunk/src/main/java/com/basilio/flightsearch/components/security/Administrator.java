package com.basilio.flightsearch.components.security;

import com.basilio.flightsearch.services.Authenticator;
import org.apache.tapestry5.corelib.base.AbstractConditional;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 6/23/12
 * Time: 12:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class Administrator extends AbstractConditional {

    @Inject
    private Authenticator authenticator;

    @Override
    protected boolean test()
    {
        return authenticator.isUserAdmin();
    }
}
