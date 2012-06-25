package com.basilio.flightsearch.pages;

import java.util.Date;

import com.basilio.flightsearch.annotations.GuestAccess;
import com.basilio.flightsearch.services.Authenticator;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.alerts.AlertManager;

/**
 * This page the user can search flights
 *
 * @author Basilio
 */


@GuestAccess
public class Index
{
    @Inject
    private Authenticator authenticator;

    public Object onActivate()
    {
        return authenticator.isLoggedIn() ? Search.class : About.class;
    }

}
