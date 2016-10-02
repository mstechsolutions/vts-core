package com.vts.api.vtscore.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
public class OrderRequest implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = -7402499025342328284L;
    private int truckId;
    private String truckName;
    private CustomerEntity customerInfo;
    private CustomerEntity pickupContactInfo;
    private CustomerEntity dropoffContactInfo;

    @XmlElement(name="orderDate",required=true)
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date orderDate;
    private Date pickupDate;
    private Date dropoffDate;
    private String paymentMode;
    private int expectedMiles;
    private int actualMiles;
    private double serviceFee;
    private String orderStatus;
    private boolean isPaid;
    private List<VehicleEntity> vehicles;

    public int getTruckId() {
        return truckId;
    }
    public void setTruckId(final int truckId) {
        this.truckId = truckId;
    }
    public String getTruckName() {
        return truckName;
    }
    public void setTruckName(final String truckName) {
        this.truckName = truckName;
    }
    public CustomerEntity getCustomerInfo() {
        return customerInfo;
    }
    public void setCustomerInfo(final CustomerEntity customerInfo) {
        this.customerInfo = customerInfo;
    }
    public CustomerEntity getPickupContactInfo() {
        return pickupContactInfo;
    }
    public void setPickupContactInfo(final CustomerEntity pickupContactInfo) {
        this.pickupContactInfo = pickupContactInfo;
    }
    public CustomerEntity getDropoffContactInfo() {
        return dropoffContactInfo;
    }
    public void setDropoffContactInfo(final CustomerEntity dropoffContactInfo) {
        this.dropoffContactInfo = dropoffContactInfo;
    }
    public Date getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(final Date orderDate) {
        this.orderDate = orderDate;
    }
    public Date getPickupDate() {
        return pickupDate;
    }
    public void setPickupDate(final Date pickupDate) {
        this.pickupDate = pickupDate;
    }
    public Date getDropoffDate() {
        return dropoffDate;
    }
    public void setDropoffDate(final Date dropoffDate) {
        this.dropoffDate = dropoffDate;
    }
    public String getPaymentMode() {
        return paymentMode;
    }
    public void setPaymentMode(final String paymentMode) {
        this.paymentMode = paymentMode;
    }
    public int getExpectedMiles() {
        return expectedMiles;
    }
    public void setExpectedMiles(final int expectedMiles) {
        this.expectedMiles = expectedMiles;
    }
    public int getActualMiles() {
        return actualMiles;
    }
    public void setActualMiles(final int actualMiles) {
        this.actualMiles = actualMiles;
    }
    public double getServiceFee() {
        return serviceFee;
    }
    public void setServiceFee(final double serviceFee) {
        this.serviceFee = serviceFee;
    }
    public String getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(final String orderStatus) {
        this.orderStatus = orderStatus;
    }
    public boolean isPaid() {
        return isPaid;
    }
    public void setPaid(final boolean isPaid) {
        this.isPaid = isPaid;
    }
    public List<VehicleEntity> getVehicles() {
        return vehicles;
    }
    public void setVehicles(final List<VehicleEntity> vehicles) {
        this.vehicles = vehicles;
    }

}


/*
 * Copyright 2016 MSTech LLC All Rights Reserved.
 *
 * This software contains valuable trade secrets and proprietary information of
 * MSTech LLC and is protected by law. It may not be copied or distributed in
 * any form or medium, disclosed to third parties, reverse engineered or used in
 * any manner without prior written authorization from MSTech LLC.
 */