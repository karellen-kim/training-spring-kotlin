package com.example.boot.repo

import com.example.boot.configuration.HbaseTemplate
import org.apache.hadoop.hbase.Cell
import org.apache.hadoop.hbase.client.Result
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository

@Repository
class HbaseOnlineFeatureRepository(
    @Qualifier("hbaseTemplate") private val hbaseTemplate: HbaseTemplate
) {
    fun getSample(): String? {
        return hbaseTemplate.execute("TEST_TABLE") { rs: Result ->
            var rawCells: Array<Cell> = rs.rawCells()
            "test"
        }.firstOrNull()
    }
}