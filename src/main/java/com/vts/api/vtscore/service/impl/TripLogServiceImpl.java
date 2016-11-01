package com.vts.api.vtscore.service.impl;

import java.math.BigInteger;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.vts.api.vtscore.model.TripEntity;
import com.vts.api.vtscore.service.api.GenericDao;
import com.vts.api.vtscore.service.api.TripLogDao;
import com.vts.api.vtscore.service.api.TripLogService;
import com.vts.api.vtscore.service.dao.impl.GenericDaoImpl;

@Named
public class TripLogServiceImpl implements TripLogService{

    @Inject
    private TripLogDao tripLogDao;
    @Inject
    private GenericDao genericDao;
    
    @Override
    public List<TripEntity> getTripLogs(int startMonth, int startYear, int endMonth, int endYear) {
        return tripLogDao.getTripLogs(startMonth,  startYear, endMonth, endYear);
    }
    
    @Override
    public void insertTripLog(TripEntity tripEntity){
        final List<BigInteger> tripLogId = genericDao.getSequenceIdList(GenericDaoImpl.GET_TRIPLOG_ID, 1);
        tripEntity.setTripId(tripLogId.get(0).intValue());
        tripLogDao.insertTripInfo(tripEntity);
    }

    @Override
    public void updateTripLog(TripEntity tripEntity) {
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
