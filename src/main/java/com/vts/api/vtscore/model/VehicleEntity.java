package com.vts.api.vtscore.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class VehicleEntity implements Serializable{

    private static final long serialVersionUID = 3064125412584643785L;
    
    @XmlElement(defaultValue="0")
    private long vehicleId;
    private long orderId; 
    private String make;
    private String model;
    private String year;
    private String vin;
    private String licencePlate;
    private boolean isOwnedByManagingEntity;
    private Date lastServiceInspectionDate;
    private Date registrationExpirationDate;
    
    
    public long getOrderId() {
        return orderId;
    }
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
    public long getVehicleId() {
        return vehicleId;
    }
    public void setVehicleId(long vehicleId) {
        this.vehicleId = vehicleId;
    }
    public String getMake() {
        return make;
    }
    public void setMake(final String make) {
        this.make = make;
    }
    public String getModel() {
        return model;
    }
    public void setModel(final String model) {
        this.model = model;
    }
    public String getYear() {
        return year;
    }
    public void setYear(final String year) {
        this.year = year;
    }
    public String getVin() {
        return vin;
    }
    public void setVin(final String vin) {
        this.vin = vin;
    }
    public String getLicencePlate() {
        return licencePlate;
    }
    public void setLicencePlate(final String licencePlate) {
        this.licencePlate = licencePlate;
    }
    public boolean isOwnedByManagingEntity() {
        return isOwnedByManagingEntity;
    }
    public void setOwnedByManagingEntity(final boolean isOwnedByManagingEntity) {
        this.isOwnedByManagingEntity = isOwnedByManagingEntity;
    }
    public Date getLastServiceInspectionDate() {
        return lastServiceInspectionDate;
    }
    public void setLastServiceInspectionDate(final Date lastServiceInspectionDate) {
        this.lastServiceInspectionDate = lastServiceInspectionDate;
    }
    public Date getRegistrationExpirationDate() {
        return registrationExpirationDate;
    }
    public void setRegistrationExpirationDate(final Date registrationExpirationDate) {
        this.registrationExpirationDate = registrationExpirationDate;
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