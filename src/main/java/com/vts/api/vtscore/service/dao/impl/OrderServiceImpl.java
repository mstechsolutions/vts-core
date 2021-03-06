package com.vts.api.vtscore.service.dao.impl;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.vts.api.vtscore.model.CustomerEntity;
import com.vts.api.vtscore.model.CustomerProcessDetail;
import com.vts.api.vtscore.model.CustomerRequest;
import com.vts.api.vtscore.model.OrderEntity;
import com.vts.api.vtscore.model.OrderRequest;
import com.vts.api.vtscore.model.TripEntity;
import com.vts.api.vtscore.model.VehicleEntity;
import com.vts.api.vtscore.model.VehicleProcessDetail;
import com.vts.api.vtscore.service.api.GenericDao;
import com.vts.api.vtscore.service.api.OrderDao;
import com.vts.api.vtscore.service.api.OrderService;
import com.vts.api.vtscore.service.api.TripLogService;
import com.vts.api.vtscore.service.util.VTSConstants;
import com.vts.api.vtscore.service.util.VTSUtil;

@Component
public class OrderServiceImpl implements OrderService{
    
    @Autowired
    private GenericDao genericDao;
    
    @Autowired
    private OrderDao orderDao;
    
    @Autowired
    private VTSUtil vtsUtil;
    
    @Inject
    private TripLogService tripLogService;

    @Override
    public OrderRequest processOrderInfo(OrderRequest orderRequest) {

        System.out.println("processing order");
        
        if(isValidOrder(orderRequest.getVehicles()))
        {
            if(orderRequest.getOrderId() < 1)
            {
                final List<CustomerProcessDetail> customerProcessDetails = prepareCustomerEntity(orderRequest);
                for(final CustomerProcessDetail customerProcessDetail : customerProcessDetails)
                {
                    if(customerProcessDetail.getCustomerRole()==VTSConstants.PRIMARY_CUSTOMER_ROLE) {
                        orderRequest.getCustomerInfo().setCustomerId(customerProcessDetail.getCustomerEntity().getCustomerId());
                    }
                    else if(customerProcessDetail.getCustomerRole()==VTSConstants.PICKUP_CUSTOMER_ROLE) {
                        orderRequest.getPickupContactInfo().setCustomerId(customerProcessDetail.getCustomerEntity().getCustomerId());
                    } else if(customerProcessDetail.getCustomerRole()==VTSConstants.DROPOFF_CUSTOMER_ROLE) {
                        orderRequest.getDropoffContactInfo().setCustomerId(customerProcessDetail.getCustomerEntity().getCustomerId());
                    }
                }
                final OrderEntity orderEntity = makeOrderEntity(orderRequest);
                final List<BigInteger> orderIds = genericDao.getSequenceIdList(GenericDaoImpl.GET_ORDER_ID, 1);
                orderEntity.setOrderId(orderIds.get(0).longValue());
                
                final List<VehicleProcessDetail> vehicleProcessDetails = prepareVehicleEntity(orderRequest, orderEntity.getOrderId());
                
                orderDao.upsertOrder(OrderDaoImpl.INSERT_ORDER_QUERY, buildOrderParameters(orderEntity));
                orderDao.upsertVehicles(OrderDaoImpl.INSERT_VEHICLE_QUERY, buildVehicleParameters(vehicleProcessDetails));
                final List<Map<String, Object>> customerParamList = buildCustomerParameters(customerProcessDetails);
                if(customerParamList.size() > 0)
                {
                    orderDao.upsertCustomers(OrderDaoImpl.INSERT_CUSTOMER_QUERY, customerParamList);
                    return buildOrderResponse(orderRequest, orderEntity, customerProcessDetails, vehicleProcessDetails);
                }
            }
            else
            {
                final OrderEntity orderEntity = makeOrderEntity(orderRequest);
                orderDao.upsertOrder(OrderDaoImpl.UPDATE_ORDER_QUERY, buildOrderParameters(orderEntity));
                orderRequest.setTripId(orderEntity.getTripId());
                return orderRequest;
            }
            
        }
        return null;
    }
    protected boolean isValidOrder(List<VehicleEntity> vehicles)
    {
        return vehicles.size() > 0;
    }
    
//    protected OrderEntity makeOrderEntity(OrderRequest orderRequest,
//            List<CustomerProcessDetail> customerProcessDetails,
//            List<VehicleProcessDetail> vehicleProcessDetails)
//    {
//        OrderEntity orderEntity = new OrderEntity();
//        orderEntity.setOrderId(orderRequest.getOrderId());
//        orderEntity.setReferenceOrderId(orderRequest.getReferenceOrderId());
//        orderEntity.setActualMiles(orderRequest.getActualMiles());
//        orderEntity.setExpectedMiles(orderRequest.getExpectedMiles());
//        orderEntity.setTruckId(orderRequest.getTruckId());
//        orderEntity.setOrderDate(orderRequest.getOrderDate());
//        orderEntity.setPickupDate(orderRequest.getPickupDate());
//        orderEntity.setDropoffDate(orderRequest.getDropoffDate());
//        orderEntity.setReferenceOrderId(orderRequest.getReferenceOrderId());
//        orderEntity.setPaymentMode(orderRequest.getPaymentMode());
//        orderEntity.setOrderStatus(orderRequest.getOrderStatus());
//        
//        for(CustomerProcessDetail customerProcessDetail : customerProcessDetails)
//        {
//            if(customerProcessDetail.getCustomerRole().equals(VTSConstants.PRIMARY_CUSTOMER_ROLE))
//                orderEntity.setCustomerContactNum(customerProcessDetail.getCustomerEntity().getContactNumber());
//            if(customerProcessDetail.getCustomerRole().equals(VTSConstants.PICKUP_CUSTOMER_ROLE))
//                orderEntity.setPickupContactNum(customerProcessDetail.getCustomerEntity().getContactNumber());
//            if(customerProcessDetail.getCustomerRole().equals(VTSConstants.DROPOFF_CUSTOMER_ROLE))
//                orderEntity.setDropoffContactNum(customerProcessDetail.getCustomerEntity().getContactNumber());
//        }
//        
//        return orderEntity;
//    }
    protected OrderEntity makeOrderEntity(OrderRequest orderRequest)
    {
        final OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(orderRequest.getOrderId());
        
        orderEntity.setCustomerId(orderRequest.getCustomerInfo().getCustomerId());
        orderEntity.setPickupCustomerId(orderRequest.getPickupContactInfo().getCustomerId());
        orderEntity.setDropoffCustomerId(orderRequest.getDropoffContactInfo().getCustomerId());
        
        orderEntity.setReferenceOrderId(orderRequest.getReferenceOrderId());
        orderEntity.setActualMiles(orderRequest.getActualMiles());
        orderEntity.setExpectedMiles(orderRequest.getExpectedMiles());
        orderEntity.setTruckId(orderRequest.getTruckId());
        orderEntity.setOrderDate(orderRequest.getOrderDate());
        orderEntity.setPickupDate(orderRequest.getPickupDate());
        orderEntity.setDropoffDate(orderRequest.getDropoffDate());
        orderEntity.setReferenceOrderId(orderRequest.getReferenceOrderId());
        orderEntity.setPaymentMode(orderRequest.getPaymentMode());
        orderEntity.setOrderStatus(orderRequest.getOrderStatus());
        orderEntity.setServiceFee(orderRequest.getServiceFee());
        orderEntity.setPaid(orderRequest.isPaid());
        if(orderRequest.getTripId()==0){
            final TripEntity tripEntity = new TripEntity();
            tripEntity.setCreatedTimestamp(new Timestamp(new Date().getTime()));
            if(orderRequest.getPickupDate()!=null){
                tripEntity.setStartDate(VTSUtil.convertDateToString(orderRequest.getPickupDate()));
            }
            tripEntity.setTruckId(orderRequest.getTruckId());
            final List<BigInteger> tripLogId = genericDao.getSequenceIdList(GenericDaoImpl.GET_TRIPLOG_ID, 1);
            tripEntity.setTripId(tripLogId.get(0).intValue());
            tripLogService.insertTripLog(tripEntity);
            orderEntity.setTripId(tripEntity.getTripId());
        }else{
            orderEntity.setTripId(orderRequest.getTripId());
        }
        orderEntity.setDueDate(orderRequest.getDueDate());
        orderEntity.setTruckName(orderRequest.getTruckName());
        
        return orderEntity;
    }
    protected List<VehicleProcessDetail> prepareVehicleEntity(OrderRequest orderRequest, long orderId)
    {
        final List<VehicleEntity> vehicles = orderRequest.getVehicles();
        int numVehicleIdRequired = 0;
        
        for(final VehicleEntity vehicle : vehicles)
        {
            if(vehicle.getVehicleId() < 1) {
                numVehicleIdRequired+=1;
            }
        }
        final List<VehicleProcessDetail> vehicleProcessDetails = new ArrayList<VehicleProcessDetail>();
        final List<BigInteger> vehicleIds = genericDao.getSequenceIdList(GenericDaoImpl.GET_VEHICLE_ID, numVehicleIdRequired);
        for(final VehicleEntity vehicle : vehicles)
        {
            vehicle.setOrderId(orderId);
            final VehicleProcessDetail vehicleProcessDetail = new VehicleProcessDetail();
            vehicleProcessDetail.setVehicleEntity(vehicle);
            if(vehicle.getVehicleId() < 1)
            {
                vehicleProcessDetail.setUpsert(true);
                vehicleProcessDetail.getVehicleEntity().setVehicleId(vehicleIds.get(numVehicleIdRequired-1).longValue());
                numVehicleIdRequired-=1;
                
            }
            vehicleProcessDetails.add(vehicleProcessDetail);    
        }
        return vehicleProcessDetails;
    }
    
    protected List<CustomerProcessDetail> prepareCustomerEntity(OrderRequest orderRequest)
    {
        int numCustomer=0;
        boolean isPickupCCAsSameAsCustomer = true;
        boolean isPrimaryCustomerExist = true;
        boolean isPickupCustomerExist = true;
        boolean isDropoffCustomerExist = true;
        
        final CustomerEntity customerEntity = orderRequest.getCustomerInfo();
        final CustomerEntity pickupCustomerEntity = orderRequest.getPickupContactInfo();
        final CustomerEntity dropoffCustomerEntity = orderRequest.getDropoffContactInfo();
        
        if(!vtsUtil.isExistingCustomer(orderRequest.getCustomerInfo()))
        {
            isPrimaryCustomerExist = false;
            numCustomer+=1;
        }
        if(!vtsUtil.isExistingCustomer(orderRequest.getDropoffContactInfo()))
        {
            isDropoffCustomerExist = false;
            numCustomer+=1;
        }
        if(!vtsUtil.isExistingCustomer(orderRequest.getPickupContactInfo()))
        {
            isPickupCustomerExist=false;
            if(!orderRequest.getCustomerInfo().compare(orderRequest.getPickupContactInfo()))
            {
                isPickupCCAsSameAsCustomer=false;
                numCustomer+=1;
            } else {
                isPickupCCAsSameAsCustomer=true;
            }
                
        }
//        else if(orderRequest.getCustomerInfo().compare(orderRequest.getPickupContactInfo()))
//        {
//            isPickupCCAsSameAsCustomer=true;
//        }
        
        final List<BigInteger> customerIds = genericDao.getSequenceIdList(GenericDaoImpl.GET_CUSTOMER_ID, numCustomer);
        
        if(!isPrimaryCustomerExist)
        {
            customerEntity.setCustomerId(customerIds.get(numCustomer - 1).intValue());
            numCustomer-=1;
        }
        if(!isDropoffCustomerExist)
        {
            dropoffCustomerEntity.setCustomerId(customerIds.get(numCustomer - 1).intValue());
            numCustomer-=1;
        }
        if(!isPickupCCAsSameAsCustomer && !isPickupCustomerExist)
        {
            pickupCustomerEntity.setCustomerId(customerIds.get(numCustomer - 1).intValue());
        }
        else if(isPickupCCAsSameAsCustomer && !isPickupCustomerExist)
        {
            pickupCustomerEntity.setCustomerId(customerEntity.getCustomerId());
        }
        
        final List<CustomerProcessDetail> customerProcessDetails = new ArrayList<CustomerProcessDetail>();
        customerProcessDetails.add(new CustomerProcessDetail(customerEntity, VTSConstants.PRIMARY_CUSTOMER_ROLE, isPrimaryCustomerExist, !isPrimaryCustomerExist));
        customerProcessDetails.add(new CustomerProcessDetail(dropoffCustomerEntity, VTSConstants.DROPOFF_CUSTOMER_ROLE, isDropoffCustomerExist, !isDropoffCustomerExist));
        customerProcessDetails.add(new CustomerProcessDetail(pickupCustomerEntity, VTSConstants.PICKUP_CUSTOMER_ROLE, isPickupCustomerExist, !isPickupCCAsSameAsCustomer));
        return customerProcessDetails;
        
    }
    
    @SuppressWarnings("unchecked")
    protected List<Map<String, Object>> buildCustomerParameters(List<CustomerProcessDetail> customerProcessDetails)
    {
        final List<Map<String, Object>> paramMapList= new ArrayList<Map<String,Object>>();
        for(final CustomerProcessDetail customerProcessDetail : customerProcessDetails)
        {
            if(customerProcessDetail.isUpsert())
            {
                final Timestamp createdTimeStamp = new Timestamp(System.currentTimeMillis());
                final Map<String, Object> params = new HashMap<String, Object>();
                final CustomerEntity customerEntity = customerProcessDetail.getCustomerEntity();
                params.put("customer_id", customerEntity.getCustomerId());
                params.put("first_name", customerEntity.getFirstName());
                params.put("middle_name", customerEntity.getMiddleName());
                params.put("last_name", customerEntity.getLastName());
                params.put("phone_number", customerEntity.getContactNumber());
                params.put("address_line1", customerEntity.getAddressLine1());
                params.put("address_line2", customerEntity.getAddressLine2());
                params.put("state", customerEntity.getState());
                params.put("country", customerEntity.getCountry());
                params.put("zip_code", Integer.valueOf(customerEntity.getZipCode()));
                params.put("email_id", customerEntity.getEmailAddress());
                params.put("city", customerEntity.getCity());
                params.put("created_timestamp", createdTimeStamp);
                paramMapList.add(params);
            }
        }
        return paramMapList;
    }
    
    @SuppressWarnings("unchecked")
    protected List<Map<String, Object>> buildVehicleParameters(List<VehicleProcessDetail> vehicleProcessDetails)
    {
        
        final List<Map<String, Object>> paramMapList= new ArrayList<Map<String,Object>>();
        for(final VehicleProcessDetail vehicleProcessDetail : vehicleProcessDetails)
        {
            if(vehicleProcessDetail.isUpsert())
            {
                final Map<String, Object> params = new HashMap<String, Object>();
                final VehicleEntity vehicleEntity = vehicleProcessDetail.getVehicleEntity();
                
                params.put("order_id", vehicleEntity.getOrderId());
                params.put("vehicle_id", vehicleEntity.getVehicleId());
                params.put("make", vehicleEntity.getMake());
                params.put("model", vehicleEntity.getModel());
                params.put("year", vehicleEntity.getYear());
                params.put("vin", vehicleEntity.getVin());
                params.put("license_plate", vehicleEntity.getLicencePlate());
                params.put("is_owned_by_managing_entity", vehicleEntity.isOwnedByManagingEntity());
                Date expirationDate=null;
                if(vehicleEntity.getRegistrationExpirationDate()!=null){
                    expirationDate=VTSUtil.convertToDate(vehicleEntity.getRegistrationExpirationDate());
                }
                Date lastInspectionDate=null;
                if(vehicleEntity.getRegistrationExpirationDate()!=null){
                    lastInspectionDate=VTSUtil.convertToDate(vehicleEntity.getLastServiceInspectionDate());
                }
                params.put("registration_expiration_date", expirationDate);
                params.put("last_service_inspection_date", lastInspectionDate);
                params.put("vehicle_name", vehicleEntity.getVehicleName());
                paramMapList.add(params);
            }
        }
//        
        return paramMapList;
    }
    protected Map<String, Object> buildOrderParameters(OrderEntity orderEntity)
    {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("order_id", orderEntity.getOrderId());
        params.put("reference_order_id", orderEntity.getReferenceOrderId());
        
        params.put("customer_id", orderEntity.getCustomerId());
        params.put("pickup_contact_id", orderEntity.getPickupCustomerId());
        params.put("dropoff_contact_id", orderEntity.getDropoffCustomerId());
        
        params.put("truck_id", orderEntity.getTruckId());
        params.put("order_date", orderEntity.getOrderDate());
        params.put("pickup_date", orderEntity.getPickupDate());
        params.put("dropoff_date", orderEntity.getDropoffDate());
        params.put("payment_mode", orderEntity.getPaymentMode());
        params.put("expected_miles", orderEntity.getExpectedMiles());
        params.put("actual_miles", orderEntity.getActualMiles());
        params.put("service_fees", orderEntity.getServiceFee());
        params.put("order_status", orderEntity.getOrderStatus());
        params.put("is_paid", orderEntity.isPaid());
        params.put("trip_id", orderEntity.getTripId());
        params.put("due_date", orderEntity.getDueDate());
        return params;
    }
    protected OrderRequest buildOrderResponse(OrderRequest orderRequest,
            OrderEntity orderEntity,
            List<CustomerProcessDetail> customerProcessDetails,
            List<VehicleProcessDetail> vehicleProcessDetailList)
    {
        OrderRequest request=null;
        try {
            request =  (OrderRequest) orderRequest.clone();
            request.setOrderId(orderEntity.getOrderId());
            
            final List<VehicleEntity> vehicles = new ArrayList<VehicleEntity>();
            for(final VehicleProcessDetail vehicleProcessDetail : vehicleProcessDetailList) {
                vehicles.add(vehicleProcessDetail.getVehicleEntity());
            }
            request.setVehicles(vehicles);
            for(final CustomerProcessDetail customerProcessDetail : customerProcessDetails)
            {
                if(customerProcessDetail.getCustomerRole().equals(VTSConstants.PRIMARY_CUSTOMER_ROLE)) {
                    request.setCustomerInfo(customerProcessDetail.getCustomerEntity());
                } else if(customerProcessDetail.getCustomerRole().equals(VTSConstants.PICKUP_CUSTOMER_ROLE)) {
                    request.setPickupContactInfo(customerProcessDetail.getCustomerEntity());
                } else if(customerProcessDetail.getCustomerRole().equals(VTSConstants.DROPOFF_CUSTOMER_ROLE)) {
                    request.setDropoffContactInfo(customerProcessDetail.getCustomerEntity());
                }
            }
            
        } catch (final CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return request;
    }
    
    /* (non-Javadoc)
     * @see com.vts.api.vtscore.service.api.OrderService#getOrders()
     */

    @Override
    public List<OrderRequest> getOrders(Date startDate, Date endDate, int truckId) {
        final List<OrderEntity> orderEntityList= orderDao.getShippingOrders(startDate,endDate, truckId);
        final List<OrderRequest> orderRequestList = new ArrayList<OrderRequest>();
        if(!CollectionUtils.isEmpty(orderEntityList)){
            for(final OrderEntity orderEntity : orderEntityList)
            {
                final OrderRequest order = new OrderRequest();
                order.setActualMiles(orderEntity.getActualMiles());
                order.setCustomerInfo(orderEntity.getCustomerInfo());
                order.setDropoffContactInfo(orderEntity.getDropoffContactInfo());
                order.setDropoffDate(VTSUtil.convertDateToString(orderEntity.getDropoffDate()));
                order.setExpectedMiles(orderEntity.getExpectedMiles());
                order.setOrderDate(VTSUtil.convertDateToString(orderEntity.getOrderDate()));
                order.setOrderId(orderEntity.getOrderId());
                order.setOrderStatus(orderEntity.getOrderStatus());
                order.setPaid(orderEntity.isPaid());
                order.setPaymentMode(orderEntity.getPaymentMode());
                order.setPickupContactInfo(orderEntity.getPickupContactInfo());
                order.setPickupDate(VTSUtil.convertDateToString(orderEntity.getPickupDate()));
                order.setReferenceOrderId(orderEntity.getReferenceOrderId());
                order.setServiceFee(orderEntity.getServiceFee());
                order.setTruckId(orderEntity.getTruckId());
                order.setTruckName(orderEntity.getTruckName());
                order.setVehicles(orderEntity.getVehicles());
                order.setTripId(orderEntity.getTripId());
                order.setDueDate(VTSUtil.convertDateToString(orderEntity.getDueDate()));
                System.out.println(orderEntity.getOrderId());
                System.out.println("orderEntity.getTripId():"+orderEntity.getTripId());
                
                orderRequestList.add(order);
            }
        }
        
        return orderRequestList;
    }
    
    @Override
    public List<CustomerRequest> getCustomers(String phoneNumber){
        final List<CustomerEntity> customerEntityList= orderDao.getCustomers(phoneNumber);
        final List<CustomerRequest> customerRequestList = new ArrayList<CustomerRequest>();
        if(!CollectionUtils.isEmpty(customerEntityList)){
            for(final CustomerEntity customerEntity : customerEntityList)
            {
                final CustomerRequest customerRequest = new CustomerRequest();
                customerRequest.setCustomerId(customerEntity.getCustomerId());
                customerRequest.setFirstName(customerEntity.getFirstName());
                customerRequest.setMiddleName(customerEntity.getMiddleName());
                customerRequest.setLastName(customerEntity.getLastName());
                customerRequest.setAddressLine1(customerEntity.getAddressLine1());
                customerRequest.setAddressLine2(customerEntity.getAddressLine2());
                customerRequest.setCity(customerEntity.getCity());
                customerRequest.setContactNumber(customerEntity.getContactNumber());
                customerRequest.setCountry(customerEntity.getCountry());
                customerRequest.setEmailAddress(customerEntity.getEmailAddress());
                customerRequest.setState(customerEntity.getState());
                customerRequest.setZipCode(customerEntity.getZipCode());
                customerRequest.setCreatedTimestamp(customerEntity.getCreatedTimestamp());
                customerRequestList.add(customerRequest);
            }
        }
        
        return customerRequestList;
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
