package com.basilio.flightsearch.entities;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 9/5/12
 * Time: 5:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContactInformation {

    private String confirmEmail;
    private String phone;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmEmail() {
        return confirmEmail;
    }

    public void setConfirmEmail(String confirmEmail) {
        this.confirmEmail = confirmEmail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean emailsMatch(){
        return email.equals(confirmEmail);
    }

}
