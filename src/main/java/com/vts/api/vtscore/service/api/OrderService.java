package com.vts.api.vtscore.service.api;

import java.util.List;

import com.vts.api.vtscore.model.OrderEntity;
import com.vts.api.vtscore.model.OrderRequest;

public interface OrderService {
    
    public void processOrderInfo(OrderRequest orderRequest);
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
