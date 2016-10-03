package com.vts.api.vtscore.service.util;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import com.vts.api.vtscore.model.CustomerEntity;

@Component
public class VTSUtil {
    public static final String API_DATE_PATTERN="yyyy-MM-dd";
    
    public static Date convertToDate(String dateString)
    {
        Date resultDate = null;
        try {
            if(StringUtils.isNotBlank(dateString)){
                resultDate=DateUtils.parseDate(dateString, API_DATE_PATTERN);
            }
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return resultDate;
    }
    public boolean isExistingCustomer(CustomerEntity customerEntity)
    {
        return customerEntity.getCustomerId() > 0;
    }
    
    public static String convertDateToString(Date date)
    {
        String dateString = "";
        try {
            if(date!=null){
                dateString=date.toString();
            }
            
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return dateString;
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
