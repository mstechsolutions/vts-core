package com.vts.api.vtscore.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;

import com.vts.api.vtscore.model.OrderEntity;
import com.vts.api.vtscore.model.OrderRequest;
import com.vts.api.vtscore.service.api.OrderService;

@Path("truck/orders")
public class OrderResource {
    
    @Autowired
    private OrderService orderService;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<OrderEntity> getOrders() throws JSONException {
        final List<OrderEntity> orderEntityList = orderService.getOrders();
        return orderEntityList;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public OrderRequest insertOrders(final OrderRequest orderRequest){
        System.out.println(orderRequest.getTruckName());
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
}


/*
 * Copyright 2016 MSTech LLC All Rights Reserved.
 *
 * This software contains valuable trade secrets and proprietary information of
 * MSTech LLC and is protected by law. It may not be copied or distributed in
 * any form or medium, disclosed to third parties, reverse engineered or used in
 * any manner without prior written authorization from MSTech LLC.
 */
