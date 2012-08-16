package com.basilio.flightsearch.entities.flightresult;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/11/12
 * Time: 1:24 PM
 * List of payment information.
 */

public class PaymentInfo {
    private List<Payment> payments;

    public List<Payment> getPayments() {
        return this.payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}
