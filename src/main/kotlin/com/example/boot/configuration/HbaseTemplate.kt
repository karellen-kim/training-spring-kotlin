package com.example.boot.configuration

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.TableName
import org.apache.hadoop.hbase.client.*
import java.io.IOException
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class HbaseTemplate(val configuration: Configuration) {
    @Volatile
    private var connection: Connection? = null

    fun <T> execute(tableName: String, action: (Result) -> T): MutableList<T> {
        this.getConnection()
            ?.getTable(TableName.valueOf(tableName))
            .use { table ->
                val scan = Scan()
                val caching: Int = scan.getCaching()
                if (caching == 1) {
                    scan.setCaching(5000)
                }
                table?.getScanner(scan).use { scanner: ResultScanner? ->
                    val rs: MutableList<T> = ArrayList()
                    var rowNum = 0
                    for (result in scanner!!) {
                        rs.add(action(result))
                    }
                    return rs
                }
            }
    }

    fun getConnection(): Connection? {
        if (null == this.connection || this.connection?.isClosed() ?: true) {
            synchronized(this) {
                if (null == this.connection || this.connection?.isClosed() ?: true) {
                    try {
                        val poolExecutor = ThreadPoolExecutor(
                            200, Int.MAX_VALUE,
                            60L, TimeUnit.SECONDS,
                            SynchronousQueue<Runnable>())
                        poolExecutor.prestartCoreThread()
                        this.connection = ConnectionFactory.createConnection(configuration, poolExecutor)
                    } catch (e: IOException) {
                    }
                }
            }
        }
        return this.connection
    }
}