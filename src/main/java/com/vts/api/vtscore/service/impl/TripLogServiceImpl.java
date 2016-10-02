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

    @Override
    public List<TripEntity> getTripLogs() {
        for(final TripEntity tripEntity : tripLogDao.getTripLogs())
        {
            System.out.println(tripEntity.getTruckId());
        }
        return tripLogDao.getTripLogs();
    }

    @Override
    public void insertTripLog(final TripEntity tripEntity){
        tripLogDao.insertTripInfo(tripEntity);
    }

    @Override
    public void updateTripLog(final TripEntity tripEntity) {
        tripLogDao.updatetTripInfo(tripEntity);

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
