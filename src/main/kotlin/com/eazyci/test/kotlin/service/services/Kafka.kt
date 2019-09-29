package com.eazyci.test.kotlin.service.services

import com.datastax.oss.driver.api.core.session.Session
import com.eazyci.test.kotlin.service.models.EventData
import com.eazyci.test.kotlin.service.models.deserializeEventData
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.ByteArrayDeserializer
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.util.*

fun createEventDataConsumer(brokers: String, session: Session): Unit {
    val props = Properties()
    props["bootstrap.servers"] = brokers
    props["group.id"] = "test-group-id-1"
    props["key.deserializer"] = StringDeserializer::class.java
    props["value.deserializer"] = ByteArrayDeserializer::class.java
    val consumer = KafkaConsumer<String, ByteArray>(props)

    consumer.subscribe(listOf("test-topic-1"))

    while (true) {
        val records = consumer.poll(Duration.ofMillis(100))
        records.iterator().forEach {
            val testObj = it.value()
            val event = deserializeEventData(testObj)
            writeEventData(session, event)
        }

    }

}