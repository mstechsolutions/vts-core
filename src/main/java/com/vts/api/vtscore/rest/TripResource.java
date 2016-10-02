package com.vts.api.vtscore.rest;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;

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
        final List<TripEntity> tripLogs = tripLogService.getTripLogs();
        return tripLogs;
    }

    @Path("create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void insertTrip(final TripEntity trip){
        tripLogService.insertTripLog(trip);
        System.out.println(trip.getTruckId());
    }

    // ******************** Test method *********************
    @Path("message")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public void insertTrip(final String message){

        // ******************** Test Data *********************

        final TripEntity trip = new TripEntity();
        trip.setTripId((int)System.currentTimeMillis());
        trip.setTruckId(199);
        trip.setDriverId1(20);
        trip.setDriverId2(0);
        trip.setStartDate(new Date());
        trip.setStartingMiles(1000);
        trip.setEndingMiles(2000);
        //    	trip.setEndDate(new Date("2016-12-12"));

        // ******************** End of Test Data *********************
        tripLogService.insertTripLog(trip);
        System.out.println(message);
    }

    @Path("update")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateTrip(final TripEntity trip){
        tripLogService.updateTripLog(trip);
        System.out.println("trip id: "+trip.getTripId());
    }


    @Path("updateTest")
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    public void updateTrip(final String message){

        // ******************** Test Data *********************

        final TripEntity trip = new TripEntity();
        trip.setTripId(101);
        trip.setTruckId(100);
        trip.setDriverId1(10);
        trip.setDriverId2(20);
        trip.setStartDate(new Date());
        trip.setStartingMiles(2000);
        trip.setEndingMiles(5000);

        // ******************** End of Test Data *********************
        tripLogService.updateTripLog(trip);
        System.out.println(message + " TripdId: " + trip.getTripId() +" updated successfully *******");
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
