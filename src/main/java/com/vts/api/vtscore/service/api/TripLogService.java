package com.vts.api.vtscore.service.api;

import java.util.Date;
import java.util.List;

import com.vts.api.vtscore.model.TripEntity;

public interface TripLogService {

    public List<TripEntity> getTripLogs(Date startDate, Date endDate);
    
    public void insertTripLog(TripEntity tripEntity);

    public void updateTripLog(TripEntity tripEntity);
}


/*
 * Copyright 2016 MSTech LLC All Rights Reserved.
 *
 * This software contains valuable trade secrets and proprietary information of
 * MSTech LLC and is protected by law. It may not be copied or distributed in
 * any form or medium, disclosed to third parties, reverse engineered or used in
 * any manner without prior written authorization from MSTech LLC.
 */
