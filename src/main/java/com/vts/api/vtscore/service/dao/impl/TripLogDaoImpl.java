package com.vts.api.vtscore.service.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.vts.api.vtscore.model.TripEntity;
import com.vts.api.vtscore.service.api.TripLogDao;
import com.vts.api.vtscore.service.util.VTSUtil;

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
    public void setDataSource(final DataSource dataSource) {
        namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


    @Override
    public List<TripEntity> getTripLogs(Date startDate, Date endDate) {
        final String query = "SELECT * FROM " + DB_SCHEMA + "." + DB_TRIP_LOG_TABLE_NAME +" WHERE start_date BETWEEN :startDate AND :endDate";
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("startDate", startDate);
        namedParameters.put("endDate", endDate);
        return namedJdbcTemplate.query(query, namedParameters, new TripEntityMapper());
    }

    public class TripEntityMapper implements RowMapper<TripEntity> {

        @Override
        public TripEntity mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {

            final TripEntity tripEntity = new TripEntity();
            
            tripEntity.setTripId(resultSet.getInt("trip_id"));
            tripEntity.setTruckId(Integer.parseInt(resultSet.getString("truck_id")));
            if(!StringUtils.isEmpty(resultSet.getString("driver_id_1"))) {
                tripEntity.setDriverId1(Integer.parseInt(resultSet.getString("driver_id_1")));
            }
            if(!StringUtils.isEmpty(resultSet.getString("driver_id_2"))) {
                tripEntity.setDriverId2(Integer.parseInt(resultSet.getString("driver_id_2")));
            }
            tripEntity.setStartDate(VTSUtil.convertDateToString(resultSet.getDate("start_date")));
            tripEntity.setEndDate(VTSUtil.convertDateToString(resultSet.getDate("end_date")));
            tripEntity.setStartingMiles(resultSet.getInt("starting_miles"));
            tripEntity.setEndingMiles(resultSet.getInt("ending_miles"));
            
            tripEntity.setGasExpense(resultSet.getDouble("gas_expense"));
            tripEntity.setTollExpense(resultSet.getDouble("toll_expense"));
            tripEntity.setMaintenanceExpense(resultSet.getDouble("maintenance_expense"));
            tripEntity.setMiscExpense(resultSet.getDouble("misc_expense"));
            tripEntity.setCreatedTimestamp(resultSet.getTimestamp("created_timestamp"));
            tripEntity.setLastUpdatedTimestamp(resultSet.getTimestamp("last_updated_timestamp"));
            
            return tripEntity;

        }

    }

    @Override
    public void insertTripInfo(final TripEntity trip){

        final String SQL = "INSERT INTO " + DB_SCHEMA + "." + DB_TRIP_LOG_TABLE_NAME +
                " (trip_id, truck_id, driver_id_1, driver_id_2, start_date, end_date, starting_miles, ending_miles, gas_expense, toll_expense, maintenance_expense, misc_expense, created_timestamp) "
                + "VALUES (:tripId, :truckId, :driverId1, :driverId2, :startDate, :endDate, :startingMiles, :endingMiles, :gasExpense, :tollExpense, :maintenanceExpense, :miscExpense, :createdTimestamp)";

        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("tripId", trip.getTripId());
        namedParameters.put("truckId", trip.getTruckId());
        namedParameters.put("driverId1", trip.getDriverId1());
        namedParameters.put("driverId2", trip.getDriverId2());
        Date startDate=null;
        if(trip.getStartDate()!=null){
            startDate=VTSUtil.convertToDate(trip.getStartDate());
        }
        namedParameters.put("startDate", startDate);
        
        Date endDate=null;
        if(trip.getEndDate()!=null){
            endDate=VTSUtil.convertToDate(trip.getEndDate());
        }
        namedParameters.put("endDate", endDate);
        
        

        namedParameters.put("startingMiles", trip.getStartingMiles());
        namedParameters.put("endingMiles", trip.getEndingMiles());
        namedParameters.put("gasExpense", trip.getGasExpense());

        namedParameters.put("tollExpense", trip.getTollExpense());
        namedParameters.put("maintenanceExpense", trip.getMaintenanceExpense());
        namedParameters.put("miscExpense", trip.getMiscExpense());

        namedParameters.put("createdTimestamp", new Timestamp(new Date().getTime()));
        namedJdbcTemplate.update(SQL, namedParameters);
        System.out.println("Created Record trip.getTripId() = " + trip.getTripId() + " trip.getTruckId() = " + trip.getTruckId());
    }

    @Override
    public void updatetTripInfo(final TripEntity trip){

        final String SQL = "UPDATE " + DB_SCHEMA + "." + DB_TRIP_LOG_TABLE_NAME +
                " SET truck_id=:truckId, driver_id_1=:driverId1, driver_id_2= :driverId2, start_date=:startDate, end_date=:endDate, starting_miles=:startingMiles," +
                "ending_miles=:endingMiles, gas_expense=:gasExpense, toll_expense=:tollExpense, maintenance_expense=:maintenanceExpense,misc_expense=:miscExpense, "
                + "last_updated_timestamp=:lastUpdatedTimestamp  WHERE trip_id = :tripId";

        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("tripId", trip.getTripId());
        namedParameters.put("truckId", trip.getTruckId());
        namedParameters.put("driverId1", trip.getDriverId1());

        namedParameters.put("driverId2", trip.getDriverId2());
        namedParameters.put("startDate", VTSUtil.convertToDate(trip.getStartDate()));
        namedParameters.put("endDate", VTSUtil.convertToDate(trip.getEndDate()));

        namedParameters.put("startingMiles", trip.getStartingMiles());
        namedParameters.put("endingMiles", trip.getEndingMiles());
        namedParameters.put("gasExpense", trip.getGasExpense());

        namedParameters.put("tollExpense", trip.getTollExpense());
        namedParameters.put("maintenanceExpense", trip.getMaintenanceExpense());
        namedParameters.put("miscExpense", trip.getMiscExpense());

        namedParameters.put("lastUpdatedTimestamp", new Timestamp(new Date().getTime()));
        namedJdbcTemplate.update(SQL, namedParameters);
        System.out.println("Created Record trip.getTripId() = " + trip.getTripId() + " trip.getTruckId() = " + trip.getTruckId());
    }

    //  @Override
    //  public Long getNextVal(String sequenceName){
    //       String query = "SELECT nextval(:sequenceName) as num";
    //       Object object=namedJdbcTemplate.execute(query, null);
    //       System.out.println("object: "+ object.toString());
    //      return null;
    //  }

}


/*
 * Copyright 2016 MSTech LLC All Rights Reserved.
 *
 * This software contains valuable trade secrets and proprietary information of
 * MSTech LLC and is protected by law. It may not be copied or distributed in
 * any form or medium, disclosed to third parties, reverse engineered or used in
 * any manner without prior written authorization from MSTech LLC.
 */
