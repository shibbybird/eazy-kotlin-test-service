package com.eazyci.test.kotlin.service

import com.eazyci.test.kotlin.service.models.EventData
import com.eazyci.test.kotlin.service.models.serializeEventData
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.ByteArrayDeserializer
import org.apache.kafka.common.serialization.ByteArraySerializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*

abstract class Retriable<X> {
    abstract fun run(): X

    // 10 Seconds default Timeout
    fun retry(retries: Int = 100, time: Long = 100): X {
        for (x in 1..retries-1) {
            try {
                return run()
            } catch (t: Throwable) {
                Thread.sleep(time)
            }
        }
        return run()
    }
}

fun createProducer():  KafkaProducer<String, ByteArray> {
    val props = Properties()
    props["bootstrap.servers"] = "host.docker.internal:9092"
    props["key.serializer"] = StringSerializer::class.java
    props["value.serializer"] = ByteArraySerializer::class.java
    return KafkaProducer<String, ByteArray>(props)
}

fun sendMessage(producer: KafkaProducer<String, ByteArray>, eventData: EventData): Unit {
    val data = serializeEventData(eventData)
    val future = producer.send(ProducerRecord<String, ByteArray>("test-topic-1", data))
    future.get()
}


