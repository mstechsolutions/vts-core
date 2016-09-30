package com.vts.api.vtscore.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrderEntity implements Serializable{

    private static final long serialVersionUID = 784258121698790283L;
    
    private int truckId;
    private int orderId;
    private int referenceOrderId;
    private int customerId;
    private int pickupCustomerId;
    private int dropoffCustomerId;
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
    
    /*
     * Covered in truckLog
     */
    //private double expense;
    
    
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public int getReferenceOrderId() {
        return referenceOrderId;
    }
    public void setReferenceOrderId(int referenceOrderId) {
        this.referenceOrderId = referenceOrderId;
    }
    public double getServiceFee() {
        return serviceFee;
    }
    public void setServiceFee(double serviceFee) {
        this.serviceFee = serviceFee;
    }
    public int getTruckId() {
        return truckId;
    }
    public void setTruckId(int truckId) {
        this.truckId = truckId;
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
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public int getPickupCustomerId() {
        return pickupCustomerId;
    }
    public void setPickupCustomerId(int pickupCustomerId) {
        this.pickupCustomerId = pickupCustomerId;
    }
    public int getDropoffCustomerId() {
        return dropoffCustomerId;
    }
    public void setDropoffCustomerId(int dropoffCustomerId) {
        this.dropoffCustomerId = dropoffCustomerId;
    }
    public List<VehicleEntity> getVehicles() {
        return vehicles;
    }
    public void setVehicles(List<VehicleEntity> vehicles) {
        this.vehicles = vehicles;
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
