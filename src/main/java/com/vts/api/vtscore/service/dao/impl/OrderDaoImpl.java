package com.vts.api.vtscore.service.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.vts.api.vtscore.model.CustomerEntity;
import com.vts.api.vtscore.model.OrderEntity;
import com.vts.api.vtscore.model.VehicleEntity;
import com.vts.api.vtscore.service.api.OrderDao;

@Named
public class OrderDaoImpl implements OrderDao{
    
    public static final String DB_SCHEMA="public";
    public static final String DB_ORDER_TABLE_NAME = DB_SCHEMA + "." +"shippingorder";
    public static final String DB_VEHICLE_TABLE_NAME = DB_SCHEMA + "." +"vehicle";
    public static final String DB_CUSTOMER_TABLE_NAME = DB_SCHEMA + "." +"customer";
    
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
            + "(vehicle_id,make,model,year,vin,licence_plate,is_owned_by_managing_entity,registration_expiration_date,last_service_inspection_date, order_id) "
            + "values(:vehicle_id,:make,:model,:year,:vin,:licence_plate,:is_owned_by_managing_entity,:registration_expiration_date,:last_service_inspection_date, :order_id)";
    public static final String INSERT_CUSTOMER_QUERY="insert into public.customer"
            + "(customer_id, first_name, middle_name, last_name, phone_number, address_line1, address_line2, state, country, zip_code, email_id, city) "
            + "values(:customer_id, :first_name, :middle_name, :last_name, :phone_number, :address_line1, :address_line2, :state, :country, :zip_code, :email_id, :city)";
    
    public static final String SELECT_ORDER_QUERY = "SELECT * FROM " + DB_ORDER_TABLE_NAME + " o INNER JOIN " +
            DB_VEHICLE_TABLE_NAME+ " v ON o.order_id=v.order_id INNER JOIN "+ 
            DB_CUSTOMER_TABLE_NAME+" c ON o.customer_id = c.customer_id";
    
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
        final Map<String, Object>[] paramMapArray= new HashMap[paramMapList.size()];
        for(int i=0; i < paramMapList.size(); i++)
        {
            paramMapArray[i] = paramMapList.get(i);
        }

        namedJdbcTemplate.batchUpdate(query, paramMapArray);
        
    }

    @SuppressWarnings("unchecked")
    public void upsertVehicles(String query, List<Map<String, Object>> paramMapList) {
        final Map<String, Object>[] paramMapArray= new HashMap[paramMapList.size()];
        for(int i=0; i < paramMapList.size(); i++)
        {
            paramMapArray[i] = paramMapList.get(i);
        }

        namedJdbcTemplate.batchUpdate(query, paramMapArray);

    }
     
    public List<OrderEntity> getOrders() {
        return namedJdbcTemplate.query(SELECT_ORDER_QUERY, new ResultSetExtractor<List<OrderEntity>>(){

        /* (non-Javadoc)
         * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
         */
        public List<OrderEntity> extractData(ResultSet resultSet) throws SQLException, DataAccessException {

         final Map<Integer, OrderEntity> map = new HashMap<Integer, OrderEntity>();
         OrderEntity orderEntity=null;
         while (resultSet.next()) {
             final Integer orderId= resultSet.getInt("order_id");
             orderEntity= map.get(orderId);
             
             if(orderEntity==null){
                 orderEntity = new OrderEntity();
                 orderEntity.setOrderId(orderId);
                 orderEntity.setReferenceOrderId(resultSet.getString("reference_order_id"));
                 orderEntity.setTruckId(resultSet.getInt("truck_id"));
                 orderEntity.setCustomerContactNum(resultSet.getString("customer_contact_num"));
                 orderEntity.setPickupContactNum(resultSet.getString("pickup_contact_num")); 
                 orderEntity.setDropoffContactNum(resultSet.getString("dropoff_contact_num"));
                 orderEntity.setOrderDate(resultSet.getDate("order_date"));
                 orderEntity.setPickupDate(resultSet.getDate("pickup_date"));
                 orderEntity.setDropoffDate(resultSet.getDate("dropoff_date"));
                 orderEntity.setPaymentMode(resultSet.getString("payment_mode"));
                 orderEntity.setExpectedMiles(resultSet.getInt("expected_miles"));
                 orderEntity.setActualMiles(resultSet.getInt("actual_miles"));
                 orderEntity.setServiceFee(resultSet.getDouble("service_fees"));
                 orderEntity.setOrderStatus(resultSet.getString("order_status"));
                 orderEntity.setPaid(resultSet.getBoolean("is_paid"));

              // setting customer info
                 final CustomerEntity customerInfo = new CustomerEntity();
                 customerInfo.setCustomerId(resultSet.getInt("customer_id"));
                 customerInfo.setFirstName(resultSet.getString("first_name"));
                 customerInfo.setMiddleName(resultSet.getString("middle_name"));
                 customerInfo.setLastName(resultSet.getString("last_name"));
                 customerInfo.setContactNumber(resultSet.getString("phone_number"));
                 customerInfo.setEmailAddress(resultSet.getString("email_id"));
                 customerInfo.setAddressLine1(resultSet.getString("address_line1"));
                 customerInfo.setAddressLine2(resultSet.getString("address_line2"));
                 customerInfo.setCity(resultSet.getString("city"));
                 customerInfo.setZipCode(resultSet.getInt("zip_code"));
                 customerInfo.setState(resultSet.getString("state"));
                 customerInfo.setCountry(resultSet.getString("country"));
                 orderEntity.setCustomerInfo(customerInfo);
                 
                 orderEntity.setVehicles(new ArrayList<VehicleEntity>());
                 
                 map.put(orderId, orderEntity);
                 
             }
             
          // setting vehicle details
             final String contactNumber=resultSet.getString("phone_number");
             if(StringUtils.isNotBlank(contactNumber)){
                 final VehicleEntity vehicle = new VehicleEntity();
                 vehicle.setVehicleId(resultSet.getInt("vehicle_id"));
                 vehicle.setMake(resultSet.getString("make"));
                 vehicle.setModel(resultSet.getString("model"));
                 vehicle.setYear(resultSet.getString("year"));
                 vehicle.setVin(resultSet.getString("vin"));
                 vehicle.setLicencePlate(resultSet.getString("licence_plate"));
                 vehicle.setOwnedByManagingEntity(resultSet.getBoolean("is_owned_by_managing_entity"));
                 vehicle.setRegistrationExpirationDate(resultSet.getDate("registration_expiration_date"));
                 vehicle.setLastServiceInspectionDate(resultSet.getDate("last_service_inspection_date"));
                 vehicle.setOrderId(orderId);
                 orderEntity.getVehicles().add(vehicle);
                 
             }
         }
         
         return new ArrayList<OrderEntity> (map.values());

        }

    });
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
