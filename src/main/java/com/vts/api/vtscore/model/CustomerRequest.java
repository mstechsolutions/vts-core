package com.vts.api.vtscore.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerRequest implements Serializable{

    
    /**
     * 
     */
    private static final long serialVersionUID = 1509507262801916029L;

    @XmlElement(defaultValue="0")
    private long customerId;
    
    private String firstName;
    private String middleName;
    
    @XmlElement(defaultValue="")
    private String lastName;
    private String contactNumber;
    private String addressLine1;
    private String addressLine2;
    private String state;
    private String country;
    private int zipCode;
    private String emailAddress;
    private String city;
    private Timestamp createdTimestamp;
    
    

    /**
     * @return the createdTimestamp
     */
    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }
    /**
     * @param createdTimestamp the createdTimestamp to set
     */
    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
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
    public int getZipCode() {
        return zipCode;
    }
    public void setZipCode(final int zipCode) {
        this.zipCode = zipCode;
    }
    public long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
    
    public boolean compare(CustomerRequest customer)
    {
        return (firstName.equals(customer.firstName) &&
            lastName.equals(customer.lastName) &&
            contactNumber.equals(customer.contactNumber));
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