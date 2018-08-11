package com.mukul.mvc.domain;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.Currency;
import java.util.Locale;

public class User implements Serializable {
    private Locale currencyLocale;
    private String firstName;
    private String lastName;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String emailAddress;
    private Locale locale;
    private int customerId;
    private boolean isActiveAccount = true;

    public User() {
    }

    public String getAddress() {
        return StringUtils.trim(addressLine1 + " " + addressLine2);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country != null ? country : "--";
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Locale getLocale() {
        return locale == null ? new Locale("en", "IN") : locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }


    public String getLocalCurrency() {
        return Currency.getInstance(currencyLocale).getSymbol(currencyLocale);
    }

    public Locale getCurrencyLocale() {
        return currencyLocale;
    }

    public void setCurrencyLocale(Locale currencyLocale) {
        this.currencyLocale = currencyLocale;
    }

    public String getCurrencyCode() {
        return Currency.getInstance(currencyLocale).getCurrencyCode();
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }


}