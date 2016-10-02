package com.vts.api.vtscore.model;

public class VehicleProcessDetail {

    private VehicleEntity vehicleEntity;
    private boolean isUpsert;
    public VehicleEntity getVehicleEntity() {
        return vehicleEntity;
    }
    public void setVehicleEntity(VehicleEntity vehicleEntity) {
        this.vehicleEntity = vehicleEntity;
    }
    public boolean isUpsert() {
        return isUpsert;
    }
    public void setUpsert(boolean isUpsert) {
        this.isUpsert = isUpsert;
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
