
package com.basilio.flightsearch.entities.result;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/11/12
 * Time: 12:11 PM
 * Credit card payments
 */

public class Payments{
   	private String cardCode;
   	private String cardDescription;
   	private Installments installments;

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
 	public Installments getInstallments(){
		return this.installments;
	}
	public void setInstallments(Installments installments){
		this.installments = installments;
	}
}
