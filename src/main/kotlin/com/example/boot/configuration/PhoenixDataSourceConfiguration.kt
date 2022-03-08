package com.example.boot.configuration

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate

@Configuration
class PhoenixDataSourceConfiguration {

    @Bean("phoenixDataSource")
    @ConfigurationProperties(
        prefix = "spring.datasource.phoenix.hikari",
        ignoreInvalidFields = false,
        ignoreUnknownFields = true,
    )
    fun phoenixDataSource(): HikariDataSource = HikariDataSource()

    @Bean("phoenixJdbcTemplate")
    fun phoenixJdbcTemplate(@Qualifier("phoenixDataSource") dataSource: HikariDataSource): JdbcTemplate =
        JdbcTemplate(dataSource)

}