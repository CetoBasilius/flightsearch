package com.basilio.flightsearch.security;

import com.basilio.flightsearch.pages.Index;
import com.basilio.flightsearch.services.Authenticator;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.services.ComponentSource;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.Response;
import org.testng.annotations.Test;

import java.io.IOException;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.easymock.EasyMock.*;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 6/25/12
 * Time: 2:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticationFilterTest {

    private AuthenticationFilter createAuthenticateFilter(PageRenderLinkSource renderLinkSource,
                                                          ComponentSource componentSource,
                                                          Response response,
                                                          Authenticator authenticator) {
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
        AuthenticationFilter authenticationFilter;
        PageRenderLinkSource renderLinkSource = createNiceMock(PageRenderLinkSource.class);
        ComponentSource componentSource = createNiceMock(ComponentSource.class);
        Response response = createNiceMock(Response.class);
        Authenticator authenticator  = createNiceMock(Authenticator.class);
        authenticationFilter = createAuthenticateFilter(renderLinkSource, componentSource, response, authenticator);

        String pageName = "index";

        expect(authenticator.isLoggedIn()).andReturn(false);

        replay(renderLinkSource, componentSource, response, authenticator);

        assertFalse(authenticationFilter.dispatchedToLoginPage(pageName));
        assertTrue(authenticationFilter.dispatchedToLoginPage("otherpage"));

        verify(renderLinkSource,componentSource,response,authenticator);

    }

    @Test
    public void dispatchedToLoginPageShouldAllowIfLoggedIn() throws IOException {
        AuthenticationFilter authenticationFilter;
        PageRenderLinkSource renderLinkSource = createNiceMock(PageRenderLinkSource.class);
        ComponentSource componentSource = createNiceMock(ComponentSource.class);
        Response response = createNiceMock(Response.class);
        Authenticator authenticator  = createNiceMock(Authenticator.class);
        authenticationFilter = createAuthenticateFilter(renderLinkSource, componentSource, response, authenticator);

        String pageName = authenticationFilter.getDefaultPage();

        expect(authenticator.isLoggedIn()).andReturn(true).times(2);

        replay(renderLinkSource, componentSource, response, authenticator);

        assertFalse(authenticationFilter.dispatchedToLoginPage(pageName));
        assertFalse(authenticationFilter.dispatchedToLoginPage("otherpage"));

        verify(renderLinkSource,componentSource,response,authenticator);

    }

    @Test
    public void dispatchedToLoginPageShouldRedirectToDefaultPage() throws IOException {
        AuthenticationFilter authenticationFilter;
        PageRenderLinkSource renderLinkSource = createNiceMock(PageRenderLinkSource.class);
        ComponentSource componentSource = createNiceMock(ComponentSource.class);
        Response response = createNiceMock(Response.class);
        Authenticator authenticator  = createNiceMock(Authenticator.class);
        authenticationFilter = createAuthenticateFilter(renderLinkSource, componentSource, response, authenticator);

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
