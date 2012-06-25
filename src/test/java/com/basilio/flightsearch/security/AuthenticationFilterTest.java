package com.basilio.flightsearch.security;

import com.basilio.flightsearch.pages.Index;
import com.basilio.flightsearch.services.Authenticator;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.runtime.Component;
import org.apache.tapestry5.services.ComponentSource;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.Response;
import org.junit.Before;
import org.junit.Test;


import java.io.IOException;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.createNiceMock;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 6/25/12
 * Time: 2:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticationFilterTest {

    private AuthenticationFilter authenticationFilter;

    private PageRenderLinkSource renderLinkSource;

    private ComponentSource componentSource;

    private Response response;

    private Authenticator authenticator;

    @Before
    public void setupTests(){
        renderLinkSource = createNiceMock(PageRenderLinkSource.class);
        componentSource = createNiceMock(ComponentSource.class);
        response = createNiceMock(Response.class);
        authenticator  = createNiceMock(Authenticator.class);

        authenticationFilter = createAuthenticateFilter();
    }

    private AuthenticationFilter createAuthenticateFilter() {
        return new AuthenticationFilter(renderLinkSource, componentSource, response, authenticator){
            @Override
            Class getPageClass(String pageName) {
                if (this.getDefaultPage().equalsIgnoreCase(pageName)){
                    return this.getDefaultPageClass();
                }
                return Object.class;
            }
        };
    }

    @Test
    public void dispatchedToLoginPageShouldRedirectIfNotLoggedIn() throws IOException {

        String pageName = "index";

        expect(authenticator.isLoggedIn()).andReturn(false);

        replay(renderLinkSource, componentSource, response, authenticator);

        assertFalse(authenticationFilter.dispatchedToLoginPage(pageName));
        assertTrue(authenticationFilter.dispatchedToLoginPage("otherpage"));

        verify(renderLinkSource,componentSource,response,authenticator);

    }

    @Test
    public void dispatchedToLoginPageShouldAllowIfLoggedIn() throws IOException {

        String pageName = authenticationFilter.getDefaultPage();

        Link link = createNiceMock(Link.class);

        expect(authenticator.isLoggedIn()).andReturn(true).times(2);


        replay(renderLinkSource, componentSource, response, authenticator);

        assertFalse(authenticationFilter.dispatchedToLoginPage(pageName));
        assertFalse(authenticationFilter.dispatchedToLoginPage("otherpage"));

        verify(renderLinkSource,componentSource,response,authenticator);

    }

    @Test
    public void dispatchedToLoginPageShouldRedirectToDefaultPage() throws IOException {

        String pageName = authenticationFilter.getSigninPage();

        Link link = createNiceMock(Link.class);

        expect(authenticator.isLoggedIn()).andReturn(true).times(2);

        expect(renderLinkSource.createPageRenderLink(Index.class.getSimpleName())).andReturn(link);
        response.sendRedirect(link);
        expectLastCall();

        replay(renderLinkSource, componentSource, response, authenticator);

        assertTrue(authenticationFilter.dispatchedToLoginPage(pageName));
        assertFalse(authenticationFilter.dispatchedToLoginPage("otherpage"));

        verify(renderLinkSource,componentSource,response,authenticator);

    }

}
