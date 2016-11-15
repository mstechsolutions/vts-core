package com.vts.api.vtscore.service.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.vts.api.vtscore.model.CustomerEntity;
import com.vts.api.vtscore.model.OrderEntity;
import com.vts.api.vtscore.model.VehicleEntity;
import com.vts.api.vtscore.service.api.OrderDao;
import com.vts.api.vtscore.service.util.VTSUtil;

@Named
public class OrderDaoImpl implements OrderDao{
    
    public static final String DB_SCHEMA="public";
    public static final String DB_ORDER_TABLE_NAME = DB_SCHEMA + "." +"shippingorder";
    public static final String DB_VEHICLE_TABLE_NAME = DB_SCHEMA + "." +"vehicle";
    public static final String DB_CUSTOMER_TABLE_NAME = DB_SCHEMA + "." +"customer";
    
    public static final String INSERT_ORDER_QUERY="insert into public.shippingorder"
            + "(order_id, reference_order_id, trip_id, customer_id, pickup_contact_id, dropoff_contact_id, truck_id, order_date, pickup_date, dropoff_date, payment_mode, expected_miles, actual_miles, service_fees, order_status, is_paid, due_date) "
            + "values(:order_id, :reference_order_id, :trip_id, :customer_id, :pickup_contact_id, :dropoff_contact_id, :truck_id, :order_date, :pickup_date, :dropoff_date, :payment_mode, :expected_miles, :actual_miles, :service_fees, :order_status, :is_paid, :due_date)";
    public static final String UPDATE_ORDER_QUERY="update public.shippingorder "
            + "set order_id=:order_id, reference_order_id=:reference_order_id, trip_id=:trip_id, truck_id=:truck_id, "
            + "order_date=:order_date, pickup_date=:pickup_date, "
            + "dropoff_date=:dropoff_date, payment_mode=:payment_mode, expected_miles=:expected_miles, actual_miles=:actual_miles, service_fees=:service_fees, "
            + "order_status=:order_status, is_paid=:is_paid, due_date=:due_date "
            + "where order_id=:order_id";
    public static final String INSERT_VEHICLE_QUERY="insert into vehicle "
            + "(vehicle_id,vehicle_name, make,model,year,vin,license_plate,is_owned_by_managing_entity,registration_expiration_date,last_service_inspection_date, order_id) "
            + "values(:vehicle_id,:vehicle_name, :make,:model,:year,:vin,:license_plate,:is_owned_by_managing_entity,:registration_expiration_date,:last_service_inspection_date, :order_id)";
    public static final String INSERT_CUSTOMER_QUERY="insert into public.customer"
            + "(customer_id, first_name, middle_name, last_name, phone_number, address_line1, address_line2, state, country, zip_code, email_id, city) "
            + "values(:customer_id, :first_name, :middle_name, :last_name, :phone_number, :address_line1, :address_line2, :state, :country, :zip_code, :email_id, :city)";
    
   /* public static  String SELECT_ORDER_QUERY = "SELECT * FROM " + DB_ORDER_TABLE_NAME + " o INNER JOIN " +
            DB_VEHICLE_TABLE_NAME+ " v ON o.order_id=v.order_id INNER JOIN "+ 
            DB_CUSTOMER_TABLE_NAME+" c ON o.customer_id = c.customer_id WHERE o.order_date BETWEEN :startDate AND :endDate";
  */  
   
          
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private DataSource dataSource;
    
    public DataSource getDataSource() {
        return dataSource;
    }
    
    @Inject
    public void setDataSource(DataSource dataSource) {
        namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void upsertOrder(String query, Map<String, Object> params)
    {
        namedJdbcTemplate.update(query, params);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public void upsertCustomers(String query, List<Map<String, Object>> paramMapList) {
        final Map<String, Object>[] paramMapArray= new HashMap[paramMapList.size()];
        for(int i=0; i < paramMapList.size(); i++)
        {
            paramMapArray[i] = paramMapList.get(i);
        }

        namedJdbcTemplate.batchUpdate(query, paramMapArray);
        
    }

    @Override
    @SuppressWarnings("unchecked")
    public void upsertVehicles(String query, List<Map<String, Object>> paramMapList) {
        final Map<String, Object>[] paramMapArray= new HashMap[paramMapList.size()];
        for(int i=0; i < paramMapList.size(); i++)
        {
            paramMapArray[i] = paramMapList.get(i);
        }

        namedJdbcTemplate.batchUpdate(query, paramMapArray);

    }
     
    @Override
    public List<OrderEntity> getShippingOrders(Date startDate, Date endDate, int truckId) {
         
        String SELECT_ORDER_QUERY = "SELECT o.*,v.*,c.*, pc.customer_id AS pc_customer_id, pc.first_name AS pc_first_name, pc.middle_name AS pc_middle_name, pc.last_name AS pc_last_name, pc.phone_number AS pc_phone_number, pc.address_line1 AS pc_address_line1, pc.address_line2 AS pc_address_line2, pc.state AS pc_state, pc.country AS pc_country, pc.zip_code AS pc_zip_code, pc.email_id AS pc_email_id, pc.city AS pc_city, dc.customer_id AS dc_customer_id, dc.first_name AS dc_first_name, dc.middle_name AS dc_middle_name, dc.last_name AS dc_last_name, dc.phone_number AS dc_phone_number, dc.address_line1 AS dc_address_line1, dc.address_line2 AS dc_address_line2, dc.state AS dc_state, dc.country AS dc_country, dc.zip_code AS dc_zip_code, dc.email_id AS dc_email_id, dc.city AS dc_city, "
                + " tr.vehicle_name as truck_name, tr.vehicle_id as truck_id, tr.license_plate as truck_license_plate  "
                + "FROM shippingorder o INNER JOIN vehicle v ON o.order_id=v.order_id "
                + "INNER JOIN vehicle as tr ON o.truck_id = tr.vehicle_id "
                + "INNER JOIN customer c ON o.customer_id = c.customer_id "
                + "INNER JOIN customer as pc ON o.pickup_contact_id = pc.customer_id "
                + "INNER JOIN customer as dc ON o.dropoff_contact_id = dc.customer_id "
                + "WHERE o.order_date BETWEEN :startDate AND :endDate";
     
        
        if(truckId!=0){
            SELECT_ORDER_QUERY=SELECT_ORDER_QUERY+" AND truck_id=:truckId";
        }
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("startDate", startDate);
        namedParameters.put("endDate", endDate);
        namedParameters.put("truckId", truckId);
        return namedJdbcTemplate.query(SELECT_ORDER_QUERY, namedParameters, new ResultSetExtractor<List<OrderEntity>>(){

        /* (non-Javadoc)
         * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
         */
        @Override
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
                 orderEntity.setOrderDate(resultSet.getDate("order_date"));
                 orderEntity.setPickupDate((resultSet.getDate("pickup_date")));
                 orderEntity.setDropoffDate((resultSet.getDate("dropoff_date")));
                 orderEntity.setPaymentMode(resultSet.getString("payment_mode"));
                 orderEntity.setExpectedMiles(resultSet.getInt("expected_miles"));
                 orderEntity.setActualMiles(resultSet.getInt("actual_miles"));
                 orderEntity.setServiceFee(resultSet.getDouble("service_fees"));
                 orderEntity.setOrderStatus(resultSet.getString("order_status"));
                 orderEntity.setPaid(resultSet.getBoolean("is_paid"));
                 orderEntity.setTripId(resultSet.getInt("trip_id"));
                 orderEntity.setTruckName(resultSet.getString("truck_name"));
                 orderEntity.setDueDate(resultSet.getDate("due_date"));

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
                 customerInfo.setCreatedTimestamp(resultSet.getTimestamp("created_timestamp"));
                 orderEntity.setCustomerInfo(customerInfo);
                 
              // setting pick up contact info
                 final CustomerEntity pickupContactInfo = new CustomerEntity();
                 pickupContactInfo.setCustomerId(resultSet.getInt("pc_customer_id"));
                 pickupContactInfo.setFirstName(resultSet.getString("pc_first_name"));
                 pickupContactInfo.setMiddleName(resultSet.getString("pc_middle_name"));
                 pickupContactInfo.setLastName(resultSet.getString("pc_last_name"));
                 pickupContactInfo.setContactNumber(resultSet.getString("pc_phone_number"));
                 pickupContactInfo.setEmailAddress(resultSet.getString("pc_email_id"));
                 pickupContactInfo.setAddressLine1(resultSet.getString("pc_address_line1"));
                 pickupContactInfo.setAddressLine2(resultSet.getString("pc_address_line2"));
                 pickupContactInfo.setCity(resultSet.getString("pc_city"));
                 pickupContactInfo.setZipCode(resultSet.getInt("pc_zip_code"));
                 pickupContactInfo.setState(resultSet.getString("pc_state"));
                 pickupContactInfo.setCountry(resultSet.getString("pc_country"));
                 pickupContactInfo.setCreatedTimestamp(resultSet.getTimestamp("created_timestamp"));
                 orderEntity.setPickupContactInfo(pickupContactInfo);
                 
              // setting drop off contact info
                 final CustomerEntity dropOffContactInfo = new CustomerEntity();
                 dropOffContactInfo.setCustomerId(resultSet.getInt("dc_customer_id"));
                 dropOffContactInfo.setFirstName(resultSet.getString("dc_first_name"));
                 dropOffContactInfo.setMiddleName(resultSet.getString("dc_middle_name"));
                 dropOffContactInfo.setLastName(resultSet.getString("dc_last_name"));
                 dropOffContactInfo.setContactNumber(resultSet.getString("dc_phone_number"));
                 dropOffContactInfo.setEmailAddress(resultSet.getString("dc_email_id"));
                 dropOffContactInfo.setAddressLine1(resultSet.getString("dc_address_line1"));
                 dropOffContactInfo.setAddressLine2(resultSet.getString("dc_address_line2"));
                 dropOffContactInfo.setCity(resultSet.getString("dc_city"));
                 dropOffContactInfo.setZipCode(resultSet.getInt("dc_zip_code"));
                 dropOffContactInfo.setState(resultSet.getString("dc_state"));
                 dropOffContactInfo.setCountry(resultSet.getString("dc_country"));
                 dropOffContactInfo.setCreatedTimestamp(resultSet.getTimestamp("created_timestamp"));
                 orderEntity.setDropoffContactInfo(dropOffContactInfo);
                 
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
                 vehicle.setLicencePlate(resultSet.getString("license_plate"));
                 vehicle.setOwnedByManagingEntity(resultSet.getBoolean("is_owned_by_managing_entity"));
                 vehicle.setRegistrationExpirationDate(VTSUtil.convertDateToString(resultSet.getDate("registration_expiration_date")));
                 vehicle.setLastServiceInspectionDate(VTSUtil.convertDateToString(resultSet.getDate("last_service_inspection_date")));
                 vehicle.setOrderId(orderId);
                 vehicle.setVehicleName(resultSet.getString("vehicle_name"));
                 orderEntity.getVehicles().add(vehicle);
                 
             }
         }
         
         return new ArrayList<OrderEntity> (map.values());

        }

    });
    }

    @Override
    public List<CustomerEntity> getCustomers(String phoneNumber) {
        String SELECT_CUSTOMER_QUERY = "SELECT * FROM public.customer LIMIT 10";
        if(StringUtils.isNotBlank(phoneNumber)){
            phoneNumber= phoneNumber.replaceAll("-", "").trim();
            SELECT_CUSTOMER_QUERY=SELECT_CUSTOMER_QUERY+" WHERE phone_number=:phoneNumber ORDERBY created_timestamp DESC";
        }
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("phoneNumber", phoneNumber);
        return namedJdbcTemplate.query(SELECT_CUSTOMER_QUERY, namedParameters, new CustomerEntityMapper());
    }

    public class CustomerEntityMapper implements RowMapper<CustomerEntity> {
        @Override
        public CustomerEntity mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
            final CustomerEntity customerEntity = new CustomerEntity();
            customerEntity.setFirstName(resultSet.getString("first_name"));
            customerEntity.setMiddleName(resultSet.getString("middle_name"));
            customerEntity.setLastName(resultSet.getString("last_name"));
            customerEntity.setAddressLine1(resultSet.getString("address_line1"));
            customerEntity.setAddressLine2(resultSet.getString("address_line2"));
            customerEntity.setCity(resultSet.getString("city"));
            customerEntity.setState(resultSet.getString("state"));
            customerEntity.setZipCode(resultSet.getInt("zip_code"));
            customerEntity.setContactNumber(resultSet.getString("phone_number"));
            customerEntity.setCountry(resultSet.getString("country"));
            customerEntity.setCustomerId(resultSet.getInt("customer_id"));
            customerEntity.setEmailAddress(resultSet.getString("email_id"));
            customerEntity.setCreatedTimestamp(resultSet.getTimestamp("created_timestamp"));
            return customerEntity;

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
