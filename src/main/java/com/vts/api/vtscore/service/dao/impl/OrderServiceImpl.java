package com.vts.api.vtscore.service.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vts.api.vtscore.model.CustomerEntity;
import com.vts.api.vtscore.model.CustomerProcessDetail;
import com.vts.api.vtscore.model.OrderEntity;
import com.vts.api.vtscore.model.OrderRequest;
import com.vts.api.vtscore.model.VehicleEntity;
import com.vts.api.vtscore.model.VehicleProcessDetail;
import com.vts.api.vtscore.service.api.GenericDao;
import com.vts.api.vtscore.service.api.OrderDao;
import com.vts.api.vtscore.service.api.OrderService;
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

    @Override
    public OrderRequest processOrderInfo(OrderRequest orderRequest) {

        System.out.println("processing order");
//        OrderEntity orderEntity = makeOrderEntity(orderRequest, customerProcessDetails, vehicleProcessDetails);
        final OrderEntity orderEntity = makeOrderEntity(orderRequest);
        
        if(isValidOrder(orderRequest.getVehicles()))
        {
            if(orderEntity.getOrderId() < 1)
            {
                
                
                final List<BigInteger> orderIds = genericDao.getSequenceIdList(GenericDaoImpl.GET_CUSTOMER_ID, 1);
                orderEntity.setOrderId(orderIds.get(0).longValue());
                
                final List<VehicleProcessDetail> vehicleProcessDetails = prepareVehicleEntity(orderRequest, orderEntity.getOrderId());
                final List<CustomerProcessDetail> customerProcessDetails = prepareCustomerEntity(orderRequest);
                orderDao.upsertOrder(OrderDaoImpl.INSERT_ORDER_QUERY, buildOrderParameters(orderEntity));
                orderDao.upsertVehicles(OrderDaoImpl.INSERT_VEHICLE_QUERY, buildVehicleParameters(vehicleProcessDetails));
                final List<Map<String, Object>> vehicleParamList = buildCustomerParameters(customerProcessDetails);
                if(vehicleParamList.size() > 0)
                {
                    orderDao.upsertCustomers(OrderDaoImpl.INSERT_CUSTOMER_QUERY, vehicleParamList);
                    return buildOrderResponse(orderRequest, orderEntity, customerProcessDetails, vehicleProcessDetails);
                }
            }
            else
            {
                orderDao.upsertOrder(OrderDaoImpl.UPDATE_ORDER_QUERY, buildOrderParameters(orderEntity));
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
        orderEntity.setReferenceOrderId(orderRequest.getReferenceOrderId());
        orderEntity.setActualMiles(orderRequest.getActualMiles());
        orderEntity.setExpectedMiles(orderRequest.getExpectedMiles());
        orderEntity.setTruckId(orderRequest.getTruckId());
        orderEntity.setOrderDate(VTSUtil.convertDateToString(orderRequest.getOrderDate()));
        orderEntity.setPickupDate(VTSUtil.convertDateToString(orderRequest.getPickupDate()));
        orderEntity.setDropoffDate(VTSUtil.convertDateToString(orderRequest.getDropoffDate()));
        orderEntity.setReferenceOrderId(orderRequest.getReferenceOrderId());
        orderEntity.setPaymentMode(orderRequest.getPaymentMode());
        orderEntity.setOrderStatus(orderRequest.getOrderStatus());
        orderEntity.setServiceFee(orderRequest.getServiceFee());
        orderEntity.setPaid(orderRequest.isPaid());
        
        orderEntity.setCustomerContactNum(orderRequest.getCustomerInfo().getContactNumber());
        orderEntity.setPickupContactNum(orderRequest.getPickupContactInfo().getContactNumber());
        orderEntity.setDropoffContactNum(orderRequest.getDropoffContactInfo().getContactNumber());
        
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
                params.put("licence_plate", vehicleEntity.getLicencePlate());
                params.put("is_owned_by_managing_entity", vehicleEntity.isOwnedByManagingEntity());
                params.put("registration_expiration_date", vehicleEntity.getRegistrationExpirationDate());
                params.put("last_service_inspection_date", vehicleEntity.getLastServiceInspectionDate());
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
        params.put("truck_id", orderEntity.getTruckId());
        params.put("customer_contact_num", orderEntity.getCustomerContactNum());
        params.put("pickup_contact_num", orderEntity.getPickupContactNum());
        params.put("dropoff_contact_num", orderEntity.getDropoffContactNum());
        params.put("order_date", orderEntity.getOrderDate());
        params.put("pickup_date", orderEntity.getPickupDate());
        params.put("dropoff_date", orderEntity.getDropoffDate());
        params.put("payment_mode", orderEntity.getPaymentMode());
        params.put("expected_miles", orderEntity.getExpectedMiles());
        params.put("actual_miles", orderEntity.getActualMiles());
        params.put("service_fees", orderEntity.getServiceFee());
        params.put("order_status", orderEntity.getOrderStatus());
        params.put("is_paid", orderEntity.isPaid());
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
        for(final OrderEntity orderEntity : orderEntityList)
        {
            final OrderRequest order = new OrderRequest();
            order.setActualMiles(orderEntity.getActualMiles());
            order.setCustomerInfo(orderEntity.getCustomerInfo());
            order.setDropoffContactInfo(orderEntity.getDropoffContactInfo());
            order.setDropoffDate(orderEntity.getDropoffDate());
            order.setExpectedMiles(orderEntity.getExpectedMiles());
            order.setOrderDate(orderEntity.getOrderDate());
            order.setOrderId(orderEntity.getOrderId());
            order.setOrderStatus(orderEntity.getOrderStatus());
            order.setPaid(orderEntity.isPaid());
            order.setPaymentMode(orderEntity.getPaymentMode());
            order.setPickupContactInfo(orderEntity.getPickupContactInfo());
            order.setPickupDate(orderEntity.getPickupDate());
            order.setReferenceOrderId(orderEntity.getReferenceOrderId());
            order.setServiceFee(orderEntity.getServiceFee());
            order.setTruckId(orderEntity.getTruckId());
//            order.setTruckName();
            order.setVehicles(orderEntity.getVehicles());
            System.out.println(orderEntity.getOrderId());
            
            orderRequestList.add(order);
        }
        return orderRequestList;
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
