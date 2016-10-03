package com.vts.api.vtscore.service.api;

import java.util.List;
import java.util.Map;

import com.vts.api.vtscore.model.OrderEntity;

public interface OrderDao {
    public void upsertOrder(String query, Map<String, Object> params);
    public void upsertVehicles(String query, List<Map<String, Object>> paramMapArray);
    public void upsertCustomers(String query, List<Map<String, Object>> paramMapArray);
    public List<OrderEntity> getOrders();
}


/*
 * Copyright 2016 Capital One Financial Corporation All Rights Reserved.
 * 
 * This software contains valuable trade secrets and proprietary information of
 * Capital One and is protected by law. It may not be copied or distributed in
 * any form or medium, disclosed to third parties, reverse engineered or used in
 * any manner without prior written authorization from Capital One.
 */
