package com.vts.api.vtscore.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrderEntity implements Serializable{

    private static final long serialVersionUID = 784258121698790283L;
    
    private int truckId;
    private String customerContactNumber;
    private String pickupContactNumber;
    private String dropoffContactNumber;

//    private int customerId;
//    private int pickupCustomerId;
//    private int dropoffCustomerId;

    private Date orderDate;
    private Date pickupDate;
    private Date dropoffDate;
    private String paymentMode;
    private int expectedMiles;
    private int actualMiles;
    private double cost;
    private double expense;
    
    private String orderStatus;
    private boolean isPaid;
    public int getTruckId() {
        return truckId;
    }
    public void setTruckId(int truckId) {
        this.truckId = truckId;
    }
    
    public String getCustomerContactNumber() {
        return customerContactNumber;
    }
    public void setCustomerContactNumber(String customerContactNumber) {
        this.customerContactNumber = customerContactNumber;
    }
    public String getPickupContactNumber() {
        return pickupContactNumber;
    }
    public void setPickupContactNumber(String pickupContactNumber) {
        this.pickupContactNumber = pickupContactNumber;
    }
    public String getDropoffContactNumber() {
        return dropoffContactNumber;
    }
    public void setDropoffContactNumber(String dropoffContactNumber) {
        this.dropoffContactNumber = dropoffContactNumber;
    }
    public int getExpectedMiles() {
        return expectedMiles;
    }
    public void setExpectedMiles(int expectedMiles) {
        this.expectedMiles = expectedMiles;
    }
    public int getActualMiles() {
        return actualMiles;
    }
    public void setActualMiles(int actualMiles) {
        this.actualMiles = actualMiles;
    }
    public double getCost() {
        return cost;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }
    public String getPaymentMode() {
        return paymentMode;
    }
    public void setPaymentMode(String modeOfPayment) {
        this.paymentMode = modeOfPayment;
    }
    public String getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    public boolean isPaid() {
        return isPaid;
    }
    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    public Date getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    public Date getPickupDate() {
        return pickupDate;
    }
    public void setPickupDate(Date pickupDate) {
        this.pickupDate = pickupDate;
    }
    public Date getDropoffDate() {
        return dropoffDate;
    }
    public void setDropoffDate(Date dropoffDate) {
        this.dropoffDate = dropoffDate;
    }
    public double getExpense() {
        return expense;
    }
    public void setExpense(double expense) {
        this.expense = expense;
    }
    
    

}


/*
 * Copyright 2016 Capital One Financial Corporation All Rights Reserved.
 * 
 * This software contains valuable trade secrets and proprietary information of
 * Capital One and is protected by law. It may not be copied or distributed in
 * any form or medium, disclosed to third parties, reverse engineered or used in
 * any manner without prior written authorization from Capital One.
 */
