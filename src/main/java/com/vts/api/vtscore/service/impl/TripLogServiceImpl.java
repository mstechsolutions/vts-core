package com.vts.api.vtscore.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.vts.api.vtscore.model.TripEntity;
import com.vts.api.vtscore.service.api.TripLogDao;
import com.vts.api.vtscore.service.api.TripLogService;

@Named
public class TripLogServiceImpl implements TripLogService{

    @Inject
    private TripLogDao tripLogDao;
    
    public List<TripEntity> getTripLogs() {
        for(TripEntity tripEntity : tripLogDao.getTripLogs())
        {
            System.out.println(tripEntity.getTruckId());
        }
        return tripLogDao.getTripLogs();
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
