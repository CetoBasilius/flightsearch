package com.basilio.flightsearch.components;

import com.basilio.flightsearch.entities.User;
import com.basilio.flightsearch.pages.*;
import com.basilio.flightsearch.services.Authenticator;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 6/21/12
 * Time: 3:50 PM
 * This is the main layout class for the layout page. in short, it is the frame you will always see.
 */

@Import(stylesheet = "context:layout/layout.css")
public class Layout {
    /**
     * The page title, for the <title> element and the <h1> element.
     */
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String title;

    @Property
    @Parameter(required = false, defaultPrefix = BindingConstants.LITERAL)
    private String description;

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

    public String getClassForPageName() {
        return resources.getPageName().equalsIgnoreCase(pageName)
                ? "current_page_item"
                : null;
    }

    public String[] getPageNames() {
        return new String[]{};
    }

    public User getUser() {
        return authenticator.isLoggedIn() ? authenticator.getLoggedUser() : null;
    }

    public Object onActionFromLogout() {
        authenticator.logout();
        return Index.class;
    }

    public Object onActionFromAccountSettings() {
        return AccountSettings.class;
    }

    public Object onActionFromSearch() {
        return SearchPage.class;
    }

    public Object onActionFromSignIn() {
        return Signin.class;
    }

    public Object onActionFromAbout() {
        return About.class;
    }
}
