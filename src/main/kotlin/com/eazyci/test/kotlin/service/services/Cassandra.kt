package com.eazyci.test.kotlin.service.services

import com.datastax.oss.driver.api.core.CqlSessionBuilder
import com.datastax.oss.driver.api.core.session.Session
import com.datastax.oss.driver.api.core.type.reflect.GenericType
import com.datastax.oss.driver.api.querybuilder.QueryBuilder.*
import com.eazyci.test.kotlin.service.models.EventData
import java.net.InetSocketAddress

fun getCassandraSession(node: String, port: Int): Session {
    return CqlSessionBuilder()
            .addContactPoint(InetSocketAddress(node, port))
            .build()
}

fun writeEventData(session: Session, eventData: EventData): Unit {
    val insert = insertInto("event_data_ts")
            .value("key", literal(eventData.key))
            .value("value", literal(eventData.value))
            .value("type", literal(eventData.type))
            .value("ts", literal(eventData.timestamp.time))
            .build()

    session.execute(insert, GenericType.INTEGER)
}