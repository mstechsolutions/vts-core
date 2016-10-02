package com.vts.api.vtscore.model;

public class CustomerProcessDetail {
    private boolean isExist;
    private String customerRole;
    private boolean isUpsert;
    private CustomerEntity customerEntity;
    
    public CustomerProcessDetail()
    {
        
    }
    
    
    public CustomerProcessDetail(CustomerEntity customerEntity, String customerRole, boolean isExist, boolean isUpsert)
    {
        this.customerEntity=customerEntity;
        this.isExist=isExist;
        this.isUpsert = isUpsert;
        this.customerRole=customerRole;
    }
    
    public boolean isExist() {
        return isExist;
    }
    public void setExist(boolean isExist) {
        this.isExist = isExist;
    }
    public String getCustomerRole() {
        return customerRole;
    }
    public void setCustomerRole(String customerRole) {
        this.customerRole = customerRole;
    }
    public CustomerEntity getCustomerEntity() {
        return customerEntity;
    }
    public void setCustomerEntity(CustomerEntity customerEntity) {
        this.customerEntity = customerEntity;
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
