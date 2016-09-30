package com.vts.api.vtscore.service.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.vts.api.vtscore.model.TripEntity;
import com.vts.api.vtscore.service.api.TripLogDao;

@Named
public class TripLogDaoImpl implements TripLogDao{
    
    public static final String DB_SCHEMA="public";
    public static final String DB_TRIP_LOG_TABLE_NAME="triplog";
    
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private DataSource dataSource;
    
    public DataSource getDataSource() {
        return dataSource;
    }
    
    @Inject
    public void setDataSource(DataSource dataSource) {
        namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<TripEntity> getTripLogs() {
        String query = "SELECT * FROM " + DB_SCHEMA + "." + DB_TRIP_LOG_TABLE_NAME;
        return (List<TripEntity>) namedJdbcTemplate.query(query, new TripEntityMapper());
    }
    
    public class TripEntityMapper implements RowMapper<TripEntity> {

        public TripEntity mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            
            TripEntity tripEntity = new TripEntity();
            tripEntity.setTruckId(Integer.parseInt(resultSet.getString("truck_id")));
            if(!StringUtils.isEmpty(resultSet.getString("driver_id_1")))
                tripEntity.setDriverId1(Integer.parseInt(resultSet.getString("driver_id_1")));
            if(!StringUtils.isEmpty(resultSet.getString("driver_id_2")))
                tripEntity.setDriverId2(Integer.parseInt(resultSet.getString("driver_id_2")));
            tripEntity.setStartDate(resultSet.getDate("start_date"));
            tripEntity.setEndDate(resultSet.getDate("end_date"));
            tripEntity.setStartingMiles(resultSet.getInt("starting_miles"));
            tripEntity.setEndingMiles(resultSet.getInt("Ending_Miles"));
            return tripEntity;
            
        } 
        
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
