package com.muldbtrans;


import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


public class DataSourceHolder {
    private static final ThreadLocal<String> dataSources = new ThreadLocal<String>();

    public static void setDataSource(String customerType){
        dataSources.set(customerType);
    }

    public static void setDataSource(String customerType, JdbcTemplate template, DataSource ds){
        setDataSource(customerType);
        template.setDataSource(ds);
    }

    public static String getDataSource(){
        return dataSources.get();
    }

    public static void cleanDataSource(){
        dataSources.remove();
    }
}
