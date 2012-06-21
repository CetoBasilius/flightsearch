package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.annotations.AnonymousAccess;
import com.basilio.flightsearch.security.AuthenticationException;
import com.basilio.flightsearch.services.Authenticator;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * User can sign in to the app
 * 
 * @author Basilio
 */
@AnonymousAccess
public class Signin
{
    @Property
    private String flashmessage;

    @Property
    private String username;

    @Property
    private String password;

    @Inject
    private Authenticator authenticator;

    @Component
    private Form loginForm;

    @Inject
    private Messages messages;

    @Log
    public Object onSubmitFromLoginForm()
    {
        try
        {
            System.out.println("Trying to login with "+username);
            authenticator.login(username, password);
            System.out.println("user "+username+" logged in...");
        }
        catch (AuthenticationException ex)
        {
            System.out.println("Login failed, somehow...");
            loginForm.recordError(messages.get("error.login"));
            return null;
        }

        return Index.class;
    }

    public String getFlashMessage()
    {
        return flashmessage;
    }

    public void setFlashMessage(String flashmessage)
    {
        this.flashmessage = flashmessage;
    }

}
