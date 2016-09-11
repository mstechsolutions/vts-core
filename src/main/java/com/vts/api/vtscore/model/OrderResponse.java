package com.vts.api.vtscore.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrderResponse implements Serializable{

    private static final long serialVersionUID = 6734158812292972645L;
    private TruckEntity truckInfo;
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
    public TruckEntity getTruckInfo() {
        return truckInfo;
    }
    public void setTruckInfo(TruckEntity truckInfo) {
        this.truckInfo = truckInfo;
    }
    public CustomerEntity getCustomerInfo() {
        return customerInfo;
    }
    public void setCustomerInfo(CustomerEntity customerInfo) {
        this.customerInfo = customerInfo;
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
    public String getPaymentMode() {
        return paymentMode;
    }
    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
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
    public double getExpense() {
        return expense;
    }
    public void setExpense(double expense) {
        this.expense = expense;
    }
    public List<VehicleEntity> getVehicles() {
        return vehicles;
    }
    public void setVehicles(List<VehicleEntity> vehicles) {
        this.vehicles = vehicles;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    
    

}