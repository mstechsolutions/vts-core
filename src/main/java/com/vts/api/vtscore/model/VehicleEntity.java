package com.vts.api.vtscore.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class VehicleEntity implements Serializable{
    
    private static final long serialVersionUID = 3064125412584643785L;
    
    private String vehicleId;
    private String make;
    private String model;
    private int year;
    private String vin;
    private String licencePlate;
    private boolean isOwnedByManagingEntity;
    private Date lastServiceInspectionDate;
    private Date registrationExpirationDate;
    
    public String getVehicleId() {
        return vehicleId;
    }
    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }
    public String getMake() {
        return make;
    }
    public void setMake(String make) {
        this.make = make;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public String getVin() {
        return vin;
    }
    public void setVin(String vin) {
        this.vin = vin;
    }
    public String getLicencePlate() {
        return licencePlate;
    }
    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }
    public boolean isOwnedByManagingEntity() {
        return isOwnedByManagingEntity;
    }
    public void setOwnedByManagingEntity(boolean isOwnedByManagingEntity) {
        this.isOwnedByManagingEntity = isOwnedByManagingEntity;
    }
    public Date getLastServiceInspectionDate() {
        return lastServiceInspectionDate;
    }
    public void setLastServiceInspectionDate(Date lastServiceInspectionDate) {
        this.lastServiceInspectionDate = lastServiceInspectionDate;
    }
    public Date getRegistrationExpirationDate() {
        return registrationExpirationDate;
    }
    public void setRegistrationExpirationDate(Date registrationExpirationDate) {
        this.registrationExpirationDate = registrationExpirationDate;
    }
    
    

}