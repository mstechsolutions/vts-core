package com.vts.api.vtscore.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.vts.api.vtscore.service.util.VTSUtil;

@XmlRootElement
public class OrderRequest implements Serializable, Cloneable{

    /**
     *
     */
    private static final long serialVersionUID = -7402499025342328284L;
    @XmlElement(defaultValue="0")
    private long orderId;
    
    private String referenceOrderId;
    private int truckId;
    private String truckName;
    private CustomerEntity customerInfo;
    private CustomerEntity pickupContactInfo;
    private CustomerEntity dropoffContactInfo;
    
//    @XmlElement(name="orderDate",required=true)
//    @XmlJavaTypeAdapter(DateAdapter.class)
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
    private long tripId;
    
    
    
    public OrderRequest()
    {
        if(referenceOrderId==null) {
            referenceOrderId="0";
        }
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
    
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
        return VTSUtil.convertToDate(orderDate);
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    public Date getPickupDate() {
        return VTSUtil.convertToDate(pickupDate);
    }
    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }
    public Date getDropoffDate() {
        return VTSUtil.convertToDate(dropoffDate);
    }

    public void setDropoffDate(String dropoffDate) {
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
    

}


/*
 * Copyright 2016 MSTech LLC All Rights Reserved.
 *
 * This software contains valuable trade secrets and proprietary information of
 * MSTech LLC and is protected by law. It may not be copied or distributed in
 * any form or medium, disclosed to third parties, reverse engineered or used in
 * any manner without prior written authorization from MSTech LLC.
 */