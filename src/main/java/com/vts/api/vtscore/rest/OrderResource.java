package com.vts.api.vtscore.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;

import com.vts.api.vtscore.model.OrderEntity;
import com.vts.api.vtscore.model.OrderRequest;
import com.vts.api.vtscore.service.api.OrderService;
import com.vts.api.vtscore.service.util.VTSConstants;
import com.vts.api.vtscore.service.util.VTSUtil;

@Path("truck/orders")
public class OrderResource {
    
    @Autowired
    private OrderService orderService;

/*    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrders() throws JSONException {

        final URL url = this.getClass().getClassLoader().getResource("getTrucksInfo.json");

        final JSONParser parser = new JSONParser();
        Object obj=null;
        try {
            obj = parser.parse(new FileReader(url.getFile()));
        } catch (final ParseException e) {
            e.printStackTrace();
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        final org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) obj;
        return Response.ok(jsonObject.toString())
                .header("Access-Control-Allow-Origin", "*")
                .build();
    }*/
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<OrderEntity> getOrders(@QueryParam("startDate") String startDate, 
            @QueryParam("endDate") String endDate, @QueryParam("truckId") int truckId) throws JSONException {
        if(StringUtils.isBlank(startDate)){
            startDate=VTSConstants.DEFAULT_START_DATE;
        }
        if(StringUtils.isBlank(endDate)){
            endDate=VTSConstants.DEFAULT_END_DATE;
        }
        final List<OrderEntity> orderEntityList = orderService.getOrders(VTSUtil.convertToDate(startDate),
                VTSUtil.convertToDate(endDate),truckId);
        return orderEntityList;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void insertOrders(final OrderRequest orderRequest){
        System.out.println(orderRequest.getTruckName());
        orderService.processOrderInfo(orderRequest);
    }

    @Path("test")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public Response insertOrders(final String message){
        System.out.println(message);
        return Response.accepted().build();
    }

    @Path("/health")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
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
