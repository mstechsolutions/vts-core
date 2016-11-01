package com.vts.api.vtscore.service.dao.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.vts.api.vtscore.service.api.GenericDao;
@Component
public class GenericDaoImpl implements GenericDao{
    public static final String GET_ORDER_ID="select nextval('order_id_sequence') from generate_series(1,:count)";
    public static final String GET_CUSTOMER_ID="select nextval('customer_id_sequence') from generate_series(1,:count)";
    public static final String GET_VEHICLE_ID="select nextval('vehicle_id_sequence') from generate_series(1,:count)";
    public static final String GET_TRIPLOG_ID="select nextval('triplog_trip_id_seq') from generate_series(1,:count)";
    
    
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    
    public DataSource getDataSource() {
        return dataSource;
    }
    
    @Inject
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    
    @Override
    @Transactional
    public List<BigInteger> getSequenceIdList(String query, int count) {
        System.out.println("is Active Transaction? " +TransactionSynchronizationManager.isActualTransactionActive());
        
       final Map<String, Object> params = new HashMap<String, Object>();
       params.put("count", count);
       final List<BigInteger> ids = namedJdbcTemplate.queryForList(query, params, BigInteger.class);
       
       for(final BigInteger id : ids)
       {
           System.out.println("Look at the customer Id's : " + id);
       }
       
       return ids;
        
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
