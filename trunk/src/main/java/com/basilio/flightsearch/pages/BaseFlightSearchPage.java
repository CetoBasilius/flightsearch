package com.basilio.flightsearch.pages;

import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 8/15/12
 * Time: 7:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseFlightSearchPage {

    @Inject
    private Messages messages;


    protected void addError(Form form, String error) {
        form.recordError(messages.get("error.login"));
    }
}
