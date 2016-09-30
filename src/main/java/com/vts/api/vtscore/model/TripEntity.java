package com.vts.api.vtscore.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlSeeAlso({DateAdapter.class})
@XmlRootElement
public class TripEntity implements Serializable{

    private static final long serialVersionUID = -578902639420354981L;
    
    private int truckId;
    private int driverId1;
    private int driverId2;


    @XmlElement(required=true, type=Date.class)
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date startDate;
    
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date endDate;
    
    private long startingMiles;
    private long endingMiles;
    private double gasExpense;
    private double tollExpense;
    private double maintenanceExpense;
    private double miscExpense;
    
    public int getTruckId() {
        return truckId;
    }
    public void setTruckId(int truckId) {
        this.truckId = truckId;
    }
    public int getDriverId1() {
        return driverId1;
    }
    public void setDriverId1(int driverId1) {
        this.driverId1 = driverId1;
    }
    public int getDriverId2() {
        return driverId2;
    }
    public void setDriverId2(int driverId2) {
        this.driverId2 = driverId2;
    }
    
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public long getStartingMiles() {
        return startingMiles;
    }
    public void setStartingMiles(long startingMiles) {
        this.startingMiles = startingMiles;
    }
    public long getEndingMiles() {
        return endingMiles;
    }
    public void setEndingMiles(long endingMiles) {
        this.endingMiles = endingMiles;
    }
    public double getGasExpense() {
        return gasExpense;
    }
    public void setGasExpense(double gasExpense) {
        this.gasExpense = gasExpense;
    }
    public double getTollExpense() {
        return tollExpense;
    }
    public void setTollExpense(double tollExpense) {
        this.tollExpense = tollExpense;
    }
    public double getMaintenanceExpense() {
        return maintenanceExpense;
    }
    public void setMaintenanceExpense(double maintenanceExpense) {
        this.maintenanceExpense = maintenanceExpense;
    }
    public double getMiscExpense() {
        return miscExpense;
    }
    public void setMiscExpense(double miscExpense) {
        this.miscExpense = miscExpense;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
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
