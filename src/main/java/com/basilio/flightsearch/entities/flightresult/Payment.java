
package com.basilio.flightsearch.entities.flightresult;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/11/12
 * Time: 12:11 PM
 * Credit card payments
 */

public class Payment {
   	private String cardCode;
   	private String cardDescription;
   	private Installment installments;

 	public String getCardCode(){
		return this.cardCode;
	}
	public void setCardCode(String cardCode){
		this.cardCode = cardCode;
	}
 	public String getCardDescription(){
		return this.cardDescription;
	}
	public void setCardDescription(String cardDescription){
		this.cardDescription = cardDescription;
	}
 	public Installment getInstallments(){
		return this.installments;
	}
	public void setInstallments(Installment installments){
		this.installments = installments;
	}
}
