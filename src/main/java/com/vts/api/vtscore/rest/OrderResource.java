package com.vts.api.vtscore.rest;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;

import com.vts.api.vtscore.model.CustomerRequest;
import com.vts.api.vtscore.model.OrderRequest;
import com.vts.api.vtscore.service.api.OrderService;
import com.vts.api.vtscore.service.util.VTSConstants;
import com.vts.api.vtscore.service.util.VTSUtil;

@Path("truck/orders")
public class OrderResource {
    
    @Context
    private HttpServletResponse response;
    
    @Autowired
    private OrderService orderService;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<OrderRequest> getOrders(@QueryParam("startDate") String startDate, 
            @QueryParam("endDate") String endDate, @QueryParam("truckId") int truckId) throws JSONException {
        
        final Date defaultStartDate=new Date();
        defaultStartDate.setDate(1);
        final String defaultStartDateStr = DateFormatUtils.format(defaultStartDate, "yyyy-MM-dd"); 
        
        if(StringUtils.isBlank(startDate)){
            startDate=defaultStartDateStr;
        }
        if(StringUtils.isBlank(endDate)){
            endDate=VTSConstants.DEFAULT_END_DATE;
        }
        final List<OrderRequest> orderList = orderService.getOrders(VTSUtil.convertToDate(startDate),
                VTSUtil.convertToDate(endDate),truckId);
        response.setHeader("Access-Control-Allow-Origin", "*");
        return orderList;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public OrderRequest insertOrders(final OrderRequest orderRequest){
        System.out.println(orderRequest.getTruckName());
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, X-Auth-Token");
        return orderService.processOrderInfo(orderRequest);
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
    
    @Path("customer")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CustomerRequest> getCustomers(@QueryParam("phoneNumber") String phoneNumber) throws JSONException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        final List<CustomerRequest> customersList = orderService.getCustomers(phoneNumber);
        return customersList;
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
