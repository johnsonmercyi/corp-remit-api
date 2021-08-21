package com.soft.cr.datasource;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostgreSqlDatasource {
    
    @Bean
    @ConfigurationProperties("app.postgresdatasource")
    public HikariDataSource hikariDataSource () {
        return DataSourceBuilder
        .create()
        .type(HikariDataSource.class)
        .build();
    }

}
