package test.kotlin.com.eazyci.test.kotlin.service

import com.datastax.oss.driver.api.core.cql.Row
import com.eazyci.test.kotlin.service.Retriable
import com.eazyci.test.kotlin.service.createProducer
import com.eazyci.test.kotlin.service.models.EventData
import com.eazyci.test.kotlin.service.sendMessage
import com.eazyci.test.kotlin.service.services.getCassandraSession
import org.junit.Test
import java.lang.Exception
import java.util.*

class Main {

    @Test
    fun `publish message on kafka and verify in cassandra`() {
        val producer = createProducer()
        val now = Calendar.getInstance().time
        val uuid = UUID.randomUUID()
        val event = EventData(
                key = uuid.toString(),
                value = "good-value",
                type = "best-events",
                timestamp = now
        )

        sendMessage(producer, event)

        val session = getCassandraSession("host.docker.internal", 9042)
        val retriable = object : Retriable<List<Row>>() {
            override fun run(): List<Row> {
                println("SELECT * FROM events WHERE key=\"$uuid\";")
                val resultSet = session.execute("SELECT * FROM events WHERE key='$uuid';")
                val list = resultSet.iterator().asSequence().toList()
                if (list.size != 1) {
                    throw Exception("Did not return single result")
                }
                return list
            }
        }

        val r = retriable.retry()

        assert(r[0].getString("value") == "good-value")
    }
}