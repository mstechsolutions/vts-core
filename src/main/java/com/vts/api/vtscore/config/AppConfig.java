package com.vts.api.vtscore.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class AppConfig {

    private final String dbUrl = "jdbc:postgresql://mstechlabs-db-instance.ccpouuprsekb.us-east-1.rds.amazonaws.com:5432/vehicletrackingsystem";
    private final String dbUser = "mstechlabs";
    private final String dbPassword = "Rangers$";
    private final String driverClassName = "org.postgresql.Driver";


    @Bean
    public DataSource getDataSource()
    {

        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUser);
        dataSource.setPassword(dbPassword);
        return dataSource;

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
