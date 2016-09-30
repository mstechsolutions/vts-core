package com.vts.api.vtscore.rest;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;

import com.vts.api.vtscore.model.OrderRequest;
import com.vts.api.vtscore.model.TripEntity;
import com.vts.api.vtscore.service.api.TripLogService;

@Path("trips")
public class TripResource {
    
    @Inject
    private TripLogService tripLogService;
    
    @Context
    private HttpServletResponse response;

    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TripEntity> getOrders() throws JSONException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        List<TripEntity> tripLogs = tripLogService.getTripLogs();
        return tripLogs;
    }
    
    @Path("create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void insertTrip(TripEntity trip){
    	tripLogService.insertTripLogs(trip);
        System.out.println(trip.getTruckId());
    }
    
 // ******************** Test method *********************
    @Path("message")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public void insertTrip(String message){
    	
    	// ******************** Test Data *********************
    	
    	TripEntity trip = new TripEntity();
    	trip.setTripId((int)System.currentTimeMillis());
    	trip.setTruckId(199);
    	trip.setDriverId1(20);
    	trip.setDriverId2(0);
    	trip.setStartDate(new Date());
    	trip.setStartingMiles(1000);
    	trip.setEndingMiles(2000);
//    	trip.setEndDate(new Date("2016-12-12"));
    	
    	// ******************** End of Test Data *********************
    	tripLogService.insertTripLogs(trip);
        System.out.println(message);
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
