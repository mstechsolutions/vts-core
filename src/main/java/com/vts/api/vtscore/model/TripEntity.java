package com.vts.api.vtscore.model;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({DateAdapter.class})
@XmlRootElement
public class TripEntity implements Serializable{

    private static final long serialVersionUID = -578902639420354981L;

    private int tripId;
    private int truckId;
    private int driverId1;
    private int driverId2;


    @XmlElement(required=true)
    private String startDate;

    private String endDate;

    private long startingMiles;
    private long endingMiles;
    private double gasExpense;
    private double tollExpense;
    private double maintenanceExpense;
    private double miscExpense;
    private double payrollExpense;

    private Timestamp  createdTimestamp;
    private Timestamp lastUpdatedTimestamp;
    
   
    public double getPayrollExpense() {
        return payrollExpense;
    }
    public void setPayrollExpense(double payrollExpense) {
        this.payrollExpense = payrollExpense;
    }
    public int getTripId() {
        return tripId;
    }
    public void setTripId(final int tripId) {
        this.tripId = tripId;
    }
    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }
    public void setCreatedTimestamp(final Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }
    public Timestamp getLastUpdatedTimestamp() {
        return lastUpdatedTimestamp;
    }
    public void setLastUpdatedTimestamp(final Timestamp lastUpdatedTimestamp) {
        this.lastUpdatedTimestamp = lastUpdatedTimestamp;
    }
    public int getTruckId() {
        return truckId;
    }
    public void setTruckId(final int truckId) {
        this.truckId = truckId;
    }
    public int getDriverId1() {
        return driverId1;
    }
    public void setDriverId1(final int driverId1) {
        this.driverId1 = driverId1;
    }
    public int getDriverId2() {
        return driverId2;
    }
    public void setDriverId2(final int driverId2) {
        this.driverId2 = driverId2;
    }

    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(final String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(final String endDate) {
        this.endDate = endDate;
    }
    public long getStartingMiles() {
        return startingMiles;
    }
    public void setStartingMiles(final long startingMiles) {
        this.startingMiles = startingMiles;
    }
    public long getEndingMiles() {
        return endingMiles;
    }
    public void setEndingMiles(final long endingMiles) {
        this.endingMiles = endingMiles;
    }
    public double getGasExpense() {
        return gasExpense;
    }
    public void setGasExpense(final double gasExpense) {
        this.gasExpense = gasExpense;
    }
    public double getTollExpense() {
        return tollExpense;
    }
    public void setTollExpense(final double tollExpense) {
        this.tollExpense = tollExpense;
    }
    public double getMaintenanceExpense() {
        return maintenanceExpense;
    }
    public void setMaintenanceExpense(final double maintenanceExpense) {
        this.maintenanceExpense = maintenanceExpense;
    }
    public double getMiscExpense() {
        return miscExpense;
    }
    public void setMiscExpense(final double miscExpense) {
        this.miscExpense = miscExpense;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
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
