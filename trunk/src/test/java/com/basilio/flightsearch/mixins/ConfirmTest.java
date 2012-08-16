package com.basilio.flightsearch.mixins;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 8/10/12
 * Time: 8:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConfirmTest {
    @Test
    public void testAfterRender() {
        Confirm confirm = new Confirm();
        String message = "This is a message";
        ClientElement element = createNiceMock(ClientElement.class);
        JavaScriptSupport javaScriptSupport = createNiceMock(JavaScriptSupport.class);

        expect(element.getClientId()).andReturn("id").anyTimes();
        javaScriptSupport.addScript("new Confirm('%s', '%s');", "id", message);
        expectLastCall();


        confirm.setMessage(message);
        confirm.setJavaScriptSupport(javaScriptSupport);
        confirm.setElement(element);
        assertEquals(message, confirm.getMessage());
        assertEquals(javaScriptSupport, confirm.getJavaScriptSupport());

        replay(element, javaScriptSupport);

        confirm.afterRender();

        verify(element, javaScriptSupport);

    }
}
