package com.vts.api.vtscore.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class AppConfig {
    
    private String dbUrl = "jdbc:postgresql://mstechlabs-db-instance.ccpouuprsekb.us-east-1.rds.amazonaws.com:5432/vehicletrackingsystem";
    private String dbUser = "mstechlabs";
    private String dbPassword = "Rangers$";
    private String driverClassName = "org.postgresql.Driver";
    
    
    @Bean
    public DataSource getDataSource()
    {
        
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUser);
        dataSource.setPassword(dbPassword);
        return dataSource;
        
    }
    
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager getDataSourceTransactionManager()
    {
        return new DataSourceTransactionManager(getDataSource());
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
