package com.eazyci.test.kotlin.service.models

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.util.Date

val jsonMapper = ObjectMapper().apply {
    registerKotlinModule()
}

data class EventData(
    val key: String,
    val type: String,
    val value: String,
    val timestamp: Date
)

fun serializeEventData(eventData: EventData): ByteArray {
    return jsonMapper.writeValueAsBytes(eventData)
}

fun deserializeEventData(byteArray: ByteArray): EventData {
    return jsonMapper.readValue(byteArray, EventData::class.java)
}