package com.vts.api.vtscore.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CustomerEntity implements Serializable{

    private static final long serialVersionUID = 1353210947680668275L;

    private int customerId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String contactNumber;
    private String addressLine1;
    private String addressLine2;
    private String state;
    private String country;
    private String zipCode;
    private String emailAddress;

    public String getEmailAddress() {
        return emailAddress;
    }
    public void setEmailAddress(final String emailAddress) {
        this.emailAddress = emailAddress;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }
    public String getMiddleName() {
        return middleName;
    }
    public void setMiddleName(final String middleName) {
        this.middleName = middleName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }
    public String getContactNumber() {
        return contactNumber;
    }
    public void setContactNumber(final String contactNumber) {
        this.contactNumber = contactNumber;
    }
    public String getAddressLine1() {
        return addressLine1;
    }
    public void setAddressLine1(final String addressLine1) {
        this.addressLine1 = addressLine1;
    }
    public String getAddressLine2() {
        return addressLine2;
    }
    public void setAddressLine2(final String addressLine2) {
        this.addressLine2 = addressLine2;
    }
    public String getState() {
        return state;
    }
    public void setState(final String state) {
        this.state = state;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(final String country) {
        this.country = country;
    }
    public String getZipCode() {
        return zipCode;
    }
    public void setZipCode(final String zipCode) {
        this.zipCode = zipCode;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(final int customerId) {
        this.customerId = customerId;
    }

    /*
     * Copyright 2016 MSTech LLC All Rights Reserved.
     *
     * This software contains valuable trade secrets and proprietary information of
     * MSTech LLC and is protected by law. It may not be copied or distributed in
     * any form or medium, disclosed to third parties, reverse engineered or used in
     * any manner without prior written authorization from MSTech LLC.
     */
}