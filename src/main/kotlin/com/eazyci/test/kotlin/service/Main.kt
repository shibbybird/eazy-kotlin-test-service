package com.eazyci.test.kotlin.service

import com.eazyci.test.kotlin.service.services.createEventDataConsumer
import com.eazyci.test.kotlin.service.services.getCassandraSession
import java.util.*

fun main(args: Array<String>) {

    val appEnv = System.getenv("APP_ENV")
    val propertiesFile = if (appEnv.isNullOrBlank()) {
        {}.javaClass.getResource("/config/default.properties").readBytes()
    } else {
        {}.javaClass.getResource("/config/$appEnv.properties").readBytes()
    }

    val props = Properties()
    props.load(propertiesFile.inputStream())
    println(props)

    // Add env
    val cassandraSession = getCassandraSession(props.getProperty("cassandra.contact.point"),
            props.getProperty("cassandra.contact.port").toInt())

    // Start Kafka consumer
    createEventDataConsumer(props.getProperty("kafka.broker.servers"), cassandraSession)
}