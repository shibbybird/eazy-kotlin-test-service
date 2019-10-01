package com.eazyci.test.kotlin.service


import com.eazyci.test.kotlin.service.services.createEventDataConsumer
import com.eazyci.test.kotlin.service.services.getCassandraSession
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import java.util.*


fun main(args: Array<String>) {

    // USE env to set properties file
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

    //Start health check
    embeddedServer(Netty, 7070) {
        routing {
            get("/health") {
                cassandraSession.execute("SELECT now() FROM system.local;")
                call.respondText("Seems to be working", ContentType.Text.Html)
            }
        }
    }.start(wait = false)

    // Start Kafka consumer
    createEventDataConsumer(props.getProperty("kafka.broker.servers"), cassandraSession)
}