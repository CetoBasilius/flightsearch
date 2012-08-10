package com.basilio.flightsearch.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 6/25/12
 * Time: 8:50 PM
 * This class is used directly by the template files (tml) as an injected JavaScript Object. See mixins/Confirm.js
 */

@Import(library = "Confirm.js")
public class Confirm {

    //Default message
    @Parameter(value = "Are you sure?", defaultPrefix = BindingConstants.LITERAL)
    private String message;
    @Inject
    private JavaScriptSupport javaScriptSupport;
    @InjectContainer
    private ClientElement element;

    @AfterRender
    public void afterRender() {
        javaScriptSupport.addScript("new Confirm('%s', '%s');", element.getClientId(), message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JavaScriptSupport getJavaScriptSupport() {
        return javaScriptSupport;
    }

    public void setJavaScriptSupport(JavaScriptSupport javaScriptSupport) {
        this.javaScriptSupport = javaScriptSupport;
    }

    public void setElement(ClientElement element) {
        this.element = element;
    }
}