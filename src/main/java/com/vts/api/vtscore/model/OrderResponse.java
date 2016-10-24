package com.vts.api.vtscore.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrderResponse implements Serializable{

    private static final long serialVersionUID = 6734158812292972645L;
    private VehicleEntity truckInfo;
    private CustomerEntity customerInfo;
    private CustomerEntity pickupContactInfo;
    private CustomerEntity dropoffContactInfo;
    private Date orderDate;
    private Date pickupDate;
    private Date dropoffDate;
    private String paymentMode;
    private int expectedMiles;
    private int actualMiles;
    private double cost;
    private double expense;
    private List<VehicleEntity> vehicles;
    private long tripId;
    

    public VehicleEntity getTruckInfo() {
        return truckInfo;
    }
    public void setTruckInfo(final VehicleEntity truckInfo) {
        this.truckInfo = truckInfo;
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
    public double getCost() {
        return cost;
    }
    public void setCost(final double cost) {
        this.cost = cost;
    }
    public double getExpense() {
        return expense;
    }
    public void setExpense(final double expense) {
        this.expense = expense;
    }
    public List<VehicleEntity> getVehicles() {
        return vehicles;
    }
    public void setVehicles(final List<VehicleEntity> vehicles) {
        this.vehicles = vehicles;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    /**
     * @return the tripId
     */
    public long getTripId() {
        return tripId;
    }
    /**
     * @param tripId the tripId to set
     */
    public void setTripId(long tripId) {
        this.tripId = tripId;
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