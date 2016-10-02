package com.vts.api.vtscore.service.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.vts.api.vtscore.service.api.OrderDao;

@Named
public class OrderDaoImpl implements OrderDao{
    
    public static final String DB_SCHEMA="public";
    public static final String DB_ORDER_TABLE_NAME="order";
    public static final String INSERT_ORDER_QUERY="insert into public.shippingorder"
            + "(order_id, reference_order_id, truck_id, customer_contact_num, pickup_contact_num, dropoff_contact_num, order_date, pickup_date, dropoff_date, payment_mode, expected_miles, actual_miles, service_fees, order_status, is_paid) "
            + "values(:order_id, :reference_order_id, :truck_id, :customer_contact_num, :pickup_contact_num, :dropoff_contact_num, :order_date, :pickup_date, :dropoff_date, :payment_mode, :expected_miles, :actual_miles, :service_fees, :order_status, :is_paid)";
    public static final String UPDATE_ORDER_QUERY="update public.shippingorder "
            + "set order_id=:order_id, reference_order_id=:reference_order_id, truck_id=:truck_id, customer_contact_num=:customer_contact_num, "
            + "pickup_contact_num=:pickup_contact_num, dropoff_contact_num=:dropoff_contact_num, order_date=:order_date, pickup_date=:pickup_date, "
            + "dropoff_date=:dropoff_date, payment_mode=:payment_mode, expected_miles=:expected_miles, actual_miles=:actual_miles, service_fees=:service_fees, "
            + "order_status=:order_status, is_paid=:is_paid "
            + "where order_id=:order_id";
    public static final String INSERT_VEHICLE_QUERY="insert into vehicle "
            + "(vehicle_id,make,model,year,vin,licence_plate,is_owned_by_managing_entity,registration_expiration_date,last_service_inspection_date) "
            + "values(:vehicle_id,:make,:model,:year,:vin,:licence_plate,:is_owned_by_managing_entity,:registration_expiration_date,:last_service_inspection_date)";
    public static final String INSERT_CUSTOMER_QUERY="insert into public.customer"
            + "(customer_id, first_name, middle_name, last_name, phone_number, address_line1, address_line2, state, country, zip_code, email_id) "
            + "values(:customer_id, :first_name, :middle_name, :last_name, :phone_number, :address_line1, :address_line2, :state, :country, :zip_code, :email_id)";
    
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    
    public DataSource getDataSource() {
        return dataSource;
    }
    
    @Inject
    public void setDataSource(DataSource dataSource) {
        namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void upsertOrder(String query, Map<String, Object> params)
    {
        namedJdbcTemplate.update(query, params);
    }
    
    @SuppressWarnings("unchecked")
    public void upsertCustomers(String query, List<Map<String, Object>> paramMapList) {
        Map<String, Object>[] paramMapArray= new HashMap[paramMapList.size()];
        for(int i=0; i < paramMapList.size(); i++)
        {
            paramMapArray[i] = paramMapList.get(i);
        }

        namedJdbcTemplate.batchUpdate(query, paramMapArray);
        
    }

    @SuppressWarnings("unchecked")
    public void upsertVehicles(String query, List<Map<String, Object>> paramMapList) {
        Map<String, Object>[] paramMapArray= new HashMap[paramMapList.size()];
        for(int i=0; i < paramMapList.size(); i++)
        {
            paramMapArray[i] = paramMapList.get(i);
        }

        namedJdbcTemplate.batchUpdate(query, paramMapArray);

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
