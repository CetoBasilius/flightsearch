package com.basilio.flightsearch.security;

import com.basilio.flightsearch.annotations.GuestAccess;
import com.basilio.flightsearch.pages.Index;
import com.basilio.flightsearch.pages.Signin;
import com.basilio.flightsearch.pages.Signup;
import com.basilio.flightsearch.services.Authenticator;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.services.*;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 6/19/12
 * Time: 11:11 AM
 * Intercepts the current page to redirect through the requested page or to the authentication page if login is required.
 *
 */
public class AuthenticationFilter implements ComponentRequestFilter {

    private final PageRenderLinkSource renderLinkSource;

    private final ComponentSource componentSource;

    private final Response response;

    private final Authenticator authenticator;

    private Class defaultPageClass = Index.class;

    private Class signinPageClass = Signin.class;

    private Class signupPageClass = Signup.class;

    private String defaultPage = defaultPageClass.getSimpleName();

    private String signinPage = signinPageClass.getSimpleName();

    private String signupPage = signupPageClass.getSimpleName();

    public AuthenticationFilter(PageRenderLinkSource renderLinkSource,
                                ComponentSource componentSource, Response response, Authenticator authenticator) {
        this.renderLinkSource = renderLinkSource;
        this.componentSource = componentSource;
        this.response = response;
        this.authenticator = authenticator;
    }

    public void handleComponentEvent(ComponentEventRequestParameters parameters,
                                     ComponentRequestHandler handler) throws IOException {

        if (dispatchedToLoginPage(parameters.getActivePageName())) {
            return;
        }

        handler.handleComponentEvent(parameters);

    }

    public void handlePageRender(PageRenderRequestParameters parameters,
                                 ComponentRequestHandler handler) throws IOException {

        if (dispatchedToLoginPage(parameters.getLogicalPageName())) {
            return;
        }

        handler.handlePageRender(parameters);
    }


    //returns true if user will be redirected.
    boolean dispatchedToLoginPage(String pageName) throws IOException {

        if (authenticator.isLoggedIn()) {
            return dispatchLoggedUser(pageName);
        } else {
            return dispatchGuest(pageName);
        }
    }

    //returns true if Guest will be redirected.
    private boolean dispatchGuest(String pageName) throws IOException {
        //Guest user should be able to see the page if the annotation is present.
        if (hasGuestAnnotation(getPageClass(pageName))) {
            return false;
        }

        Link link = renderLinkSource.createPageRenderLink(signinPage);

        response.sendRedirect(link);
        return true;
    }

    //returns true if user will be redirected.
    private boolean dispatchLoggedUser(String pageName) throws IOException {
        // Logged user should not go back to Signin or Signup
        if (signinPage.equalsIgnoreCase(pageName) || signupPage.equalsIgnoreCase(pageName)) {

            Link link = renderLinkSource.createPageRenderLink(defaultPage);
            response.sendRedirect(link);

            return true;
        }
        return false;
    }

    Class getPageClass(String pageName) {
        return componentSource.getPage(pageName).getClass();
    }

    private boolean hasGuestAnnotation(Class aClass) {
        return aClass.isAnnotationPresent(GuestAccess.class) ? true : false;
    }

    public String getDefaultPage() {
        return defaultPage;
    }

    public String getSigninPage() {
        return signinPage;
    }

    public String getSignupPage() {
        return signupPage;
    }

    public Class getDefaultPageClass() {
        return defaultPageClass;
    }

    public Class getSigninPageClass() {
        return signinPageClass;
    }

    public Class getSignupPageClass() {
        return signupPageClass;
    }

}
