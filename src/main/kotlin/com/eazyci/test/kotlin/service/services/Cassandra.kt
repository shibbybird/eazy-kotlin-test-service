package com.eazyci.test.kotlin.service.services

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.querybuilder.QueryBuilder.*
import com.eazyci.test.kotlin.service.models.EventData
import java.net.InetSocketAddress


fun getCassandraSession(node: String, port: Int): CqlSession {
    return CqlSession.builder()
            .addContactPoint(InetSocketAddress(node, port))
            .withLocalDatacenter("datacenter1")
            .withKeyspace("events_ks")
            .build()
}

fun writeEventData(session: CqlSession, eventData: EventData): Unit {
    val insert = insertInto("events")
            .value("key", literal(eventData.key))
            .value("value", literal(eventData.value))
            .value("type", literal(eventData.type))
            .value("ts", literal(eventData.timestamp.time))

    session.execute(insert.toString())
}