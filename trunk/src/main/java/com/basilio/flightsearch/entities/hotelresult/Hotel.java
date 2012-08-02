
package com.basilio.flightsearch.entities.hotelresult;

import java.util.List;

public class Hotel {
   	private Number avgDiscountPriceWithoutTax;
   	private Number avgPriceWithoutTax;
   	private String discountText;
   	private Number id;
   	private Number installmentsPermitted;
   	private boolean isAvailable;
   	private List<String> paymentTypes;
   	private Provider provider;
   	private String regimeCode;
   	private String regimeDescription;

 	public Number getAvgDiscountPriceWithoutTax(){
		return this.avgDiscountPriceWithoutTax;
	}
	public void setAvgDiscountPriceWithoutTax(Number avgDiscountPriceWithoutTax){
		this.avgDiscountPriceWithoutTax = avgDiscountPriceWithoutTax;
	}
 	public Number getAvgPriceWithoutTax(){
		return this.avgPriceWithoutTax;
	}
	public void setAvgPriceWithoutTax(Number avgPriceWithoutTax){
		this.avgPriceWithoutTax = avgPriceWithoutTax;
	}
 	public String getDiscountText(){
		return this.discountText;
	}
	public void setDiscountText(String discountText){
		this.discountText = discountText;
	}
 	public Number getId(){
		return this.id;
	}
	public void setId(Number id){
		this.id = id;
	}
 	public Number getInstallmentsPermitted(){
		return this.installmentsPermitted;
	}
	public void setInstallmentsPermitted(Number installmentsPermitted){
		this.installmentsPermitted = installmentsPermitted;
	}
 	public boolean getIsAvailable(){
		return this.isAvailable;
	}
	public void setIsAvailable(boolean isAvailable){
		this.isAvailable = isAvailable;
	}
 	public List<String> getPaymentTypes(){
		return this.paymentTypes;
	}
	public void setPaymentTypes(List<String> paymentTypes){
		this.paymentTypes = paymentTypes;
	}
 	public Provider getProvider(){
		return this.provider;
	}
	public void setProvider(Provider provider){
		this.provider = provider;
	}
 	public String getRegimeCode(){
		return this.regimeCode;
	}
	public void setRegimeCode(String regimeCode){
		this.regimeCode = regimeCode;
	}
 	public String getRegimeDescription(){
		return this.regimeDescription;
	}
	public void setRegimeDescription(String regimeDescription){
		this.regimeDescription = regimeDescription;
	}
}
