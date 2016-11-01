package com.vts.api.vtscore.rest;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONException;

import com.vts.api.vtscore.model.TripEntity;
import com.vts.api.vtscore.service.api.TripLogService;
import com.vts.api.vtscore.service.util.VTSConstants;
import com.vts.api.vtscore.service.util.VTSUtil;

@Path("trips")
public class TripResource {

    @Inject
    private TripLogService tripLogService;

    @Context
    private HttpServletResponse response;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TripEntity> getTrips(@QueryParam("startDate") String startDate, 
            @QueryParam("endDate") String endDate) throws JSONException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if(StringUtils.isBlank(startDate)){
            startDate=VTSConstants.CURRENT_DATE;
        }
        if(StringUtils.isBlank(endDate)){
            endDate=VTSConstants.CURRENT_DATE;
        }
        final Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(VTSUtil.convertToDate(startDate));
        final int startMonth = startCalendar.get(Calendar.MONTH)+1;
        final int startYear = startCalendar.get(Calendar.YEAR);
        
        final Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(VTSUtil.convertToDate(endDate));
        final int endMonth = endCalendar.get(Calendar.MONTH)+1;
        final int endYear = endCalendar.get(Calendar.YEAR);
        final List<TripEntity> tripLogs = tripLogService.getTripLogs(startMonth,  startYear, endMonth, endYear);
        return tripLogs;
    }

    @Path("create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void insertTrip(final TripEntity trip){
        tripLogService.insertTripLog(trip);
        System.out.println("tripId: "+trip.getTripId());
    }

    

    @Path("update")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateTrip(final TripEntity trip){
        tripLogService.updateTripLog(trip);
        System.out.println("trip id: "+trip.getTripId());
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
