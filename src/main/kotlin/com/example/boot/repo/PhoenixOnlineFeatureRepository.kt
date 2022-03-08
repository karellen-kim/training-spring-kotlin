package com.example.boot.repo

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class PhoenixOnlineFeatureRepository(
    @Qualifier("phoenixJdbcTemplate") private val jdbcTemplate: JdbcTemplate
) {

    fun getSample(): String {
        val query = """
            select COL1 from TEST_TABLE limit 1
        """.trimIndent()

        val result = jdbcTemplate.query(query) { rs, _ ->
            rs.getString("COL1")
        }.get(0)

        return result
    }
}
