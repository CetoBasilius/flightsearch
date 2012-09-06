package com.basilio.flightsearch.entities;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 9/5/12
 * Time: 5:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContactInformationTest {
    @Test
    public void testEmailsMatch() throws Exception {
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setConfirmEmail("basi@mail.com");
        contactInformation.setEmail("basi@mail.com");

        assertTrue(contactInformation.emailsMatch());

        contactInformation.setEmail("basi2@mail.com");

        assertFalse(contactInformation.emailsMatch());

    }
}
