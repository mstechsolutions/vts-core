package com.vts.api.vtscore.rest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Path("truck/orders")
public class OrderResource {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrders() throws JSONException {
        
        URL url = this.getClass().getClassLoader().getResource("getTrucksInfo.json");

        JSONParser parser = new JSONParser();
        Object obj=null;
        try {
            obj = parser.parse(new FileReader(url.getFile()));
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) obj;        
        return Response.ok(jsonObject.toString())
                .header("Access-Control-Allow-Origin", "*")
                .build();
    }
    
    @Path("/health")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
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
