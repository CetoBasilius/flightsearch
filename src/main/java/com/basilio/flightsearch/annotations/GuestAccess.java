package com.basilio.flightsearch.annotations;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 6/18/12
 * Time: 5:50 PM
 * Security annotation for Tapestry Pages. Enables anonymous access to pages, so the user does not
 * have to be logged in.
 */
@Target(
        {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GuestAccess {

}
