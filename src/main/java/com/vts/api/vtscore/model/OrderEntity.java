package com.vts.api.vtscore.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrderEntity implements Serializable{

    private static final long serialVersionUID = 784258121698790283L;

    private int truckId;
    private long orderId;
    private String referenceOrderId;
//    private long customerId;
//    private long pickupCustomerId;
////    private long dropoffCustomerId;
    private String customerContactNum;
    private String pickupContactNum;
    private String dropoffContactNum;
    private String orderDate;
    private String pickupDate;
    private String dropoffDate;
    private String paymentMode;
    private int expectedMiles;
    private int actualMiles;
    private double serviceFee;
    private String orderStatus;
    private boolean isPaid;
    private List<VehicleEntity> vehicles;
    private CustomerEntity customerInfo;
    private String truckName;
    private CustomerEntity pickupContactInfo;
    private CustomerEntity dropoffContactInfo;

    
    /*
     * Covered in truckLog
     */
    //private double expense;
    
    public String getTruckName() {
        return truckName;
    }
    public void setTruckName(String truckName) {
        this.truckName = truckName;
    }
    public CustomerEntity getPickupContactInfo() {
        return pickupContactInfo;
    }
    public void setPickupContactInfo(CustomerEntity pickupContactInfo) {
        this.pickupContactInfo = pickupContactInfo;
    }
    public CustomerEntity getDropoffContactInfo() {
        return dropoffContactInfo;
    }
    public void setDropoffContactInfo(CustomerEntity dropoffContactInfo) {
        this.dropoffContactInfo = dropoffContactInfo;
    }
    public CustomerEntity getCustomerInfo() {
        return customerInfo;
    }
    public void setCustomerInfo(CustomerEntity customerInfo) {
        this.customerInfo = customerInfo;
    }
    public long getOrderId() {
        return orderId;
    }
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
    public String getReferenceOrderId() {
        return referenceOrderId;
    }
    public void setReferenceOrderId(String referenceOrderId) {
        this.referenceOrderId = referenceOrderId;
    }
    public double getServiceFee() {
        return serviceFee;
    }
    public void setServiceFee(final double serviceFee) {
        this.serviceFee = serviceFee;
    }
    public int getTruckId() {
        return truckId;
    }
    public void setTruckId(final int truckId) {
        this.truckId = truckId;
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

    public String getPaymentMode() {
        return paymentMode;
    }
    public void setPaymentMode(final String modeOfPayment) {
        paymentMode = modeOfPayment;
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
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    public String getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(final String orderDate) {
        this.orderDate = orderDate;
    }
    public String getPickupDate() {
        return pickupDate;
    }
    public void setPickupDate(final String pickupDate) {
        this.pickupDate = pickupDate;
    }
    public String getDropoffDate() {
        return dropoffDate;
    }
    public void setDropoffDate(final String dropoffDate) {
        this.dropoffDate = dropoffDate;
    }
    
    public String getCustomerContactNum() {
        return customerContactNum;
    }

    public void setCustomerContactNum(String customerContactNum) {
        this.customerContactNum = customerContactNum;
    }
    public String getPickupContactNum() {
        return pickupContactNum;
    }
    public void setPickupContactNum(String pickupContactNum) {
        this.pickupContactNum = pickupContactNum;
    }
    public String getDropoffContactNum() {
        return dropoffContactNum;
    }
    public void setDropoffContactNum(String dropoffContactNum) {
        this.dropoffContactNum = dropoffContactNum;
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
