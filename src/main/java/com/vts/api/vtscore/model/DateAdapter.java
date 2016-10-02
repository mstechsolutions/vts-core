package com.vts.api.vtscore.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public String marshal(final Date v) throws Exception {
        return dateFormat.format(v);
    }

    @Override
    public Date unmarshal(final String v) throws Exception {
        return dateFormat.parse(v);
    }

}

/*
 * Copyright 2016 MSTech LLC All Rights Reserved.
 *
 * This software contains valuable trade secrets and proprietary information of
 * MSTech LLC and is protected by law. It may not be copied or distributed in
 * any form or medium, disclosed to third parties, reverse engineered or used in
 * any manner without prior written authorization from MSTech LLC.
 */