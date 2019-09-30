CREATE KEYSPACE IF NOT EXISTS events_ks
WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '1' } AND durable_writes = true;

CREATE TABLE IF NOT EXISTS events_ks.events (
    key text,
    type text,
    value text,
    ts timestamp,
    PRIMARY KEY (key, ts)
);
