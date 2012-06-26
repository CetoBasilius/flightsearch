package com.basilio.flightsearch.components;

import com.basilio.flightsearch.entities.User;
import com.basilio.flightsearch.pages.*;
import com.basilio.flightsearch.services.Authenticator;
import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.SymbolConstants;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 6/21/12
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
@Import(stylesheet = "context:layout/layout.css")
public class Layout
{
    /**
     * The page title, for the <title> element and the <h1> element.
     */
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String title;

    @Property
    private String pageName;

    @Inject
    private ComponentResources resources;

    @Property
    @Inject
    @Symbol(SymbolConstants.APPLICATION_VERSION)
    private String appVersion;

    @Inject
    private Authenticator authenticator;

    public String getClassForPageName()
    {
        return resources.getPageName().equalsIgnoreCase(pageName)
                ? "current_page_item"
                : null;
    }

    public String[] getPageNames()
    {
        return new String[]{};
    }

    public User getUser()
    {
        return authenticator.isLoggedIn() ? authenticator.getLoggedUser() : null;
    }

    @Log
    public Object onActionFromLogout()
    {
        authenticator.logout();
        return Index.class;
    }

    @Log
    public Object onActionFromAccountSettings()
    {
        return AccountSettings.class;
    }

    @Log
    public Object onActionFromSearch()
    {
        return Search.class;
    }

    @Log
    public Object onActionFromSignIn()
    {
        return Signin.class;
    }

    @Log
    public Object onActionFromAbout()
    {
        return About.class;
    }
}
