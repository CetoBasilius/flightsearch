
package com.basilio.flightsearch.entities.result;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/11/12
 * Time: 1:24 PM
 * List of payment information.
 */

public class PaymentInfo{
   	private List<Payments> payments;

 	public List<Payments> getPayments(){
		return this.payments;
	}
	public void setPayments(List<Payments> payments){
		this.payments = payments;
	}
}
