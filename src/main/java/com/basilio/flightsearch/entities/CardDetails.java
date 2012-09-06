package com.basilio.flightsearch.entities;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 9/5/12
 * Time: 5:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class CardDetails {
    protected String cardNumber;
    protected String cardCode;
    protected String cardOwner;
    protected String expireYear;
    protected String expireMonth;
    protected String cardType;

    public String getExpireYear() {
        return expireYear;
    }

    public void setExpireYear(String expireYear) {
        this.expireYear = expireYear;
    }

    public String getExpireMonth() {
        return expireMonth;
    }

    public void setExpireMonth(String expireMonth) {
        this.expireMonth = expireMonth;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getCardOwner() {
        return cardOwner;
    }

    public void setCardOwner(String cardOwner) {
        this.cardOwner = cardOwner;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
