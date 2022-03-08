package com.example.boot.configuration

import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.HConstants
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties
class HbaseConfiguration {

    @Bean("hbaseConfig")
    fun configuration(): org.apache.hadoop.conf.Configuration? {
        val configuration = HBaseConfiguration.create()
        configuration.set(HConstants.ZOOKEEPER_QUORUM, "zk1,zk2,zk3")
        configuration.set(HConstants.ZOOKEEPER_CLIENT_PORT, "2181")
        configuration.set(HConstants.ZOOKEEPER_ZNODE_PARENT, "/hbase-unsecure")

        return configuration
    }

    @Bean("hbaseTemplate")
    fun hbaseTemplate(@Qualifier("hbaseConfig") config: org.apache.hadoop.conf.Configuration): HbaseTemplate =
        HbaseTemplate(config)


}