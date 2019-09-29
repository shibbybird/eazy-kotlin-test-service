plugins {
    kotlin("jvm") version "1.2.71"
    application
}

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib", "1.2.31"))
    implementation("com.fasterxml.jackson.core:jackson-databind:2.9.+")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.+")
    implementation("org.apache.kafka:kafka-clients:2.3.+")
    implementation("com.datastax.cassandra:cassandra-driver-core:4.0.0")
    implementation("com.datastax.oss:java-driver-query-builder:4.0.0")
    implementation("org.cassandraunit:cassandra-unit:3.11.2.0")
    testImplementation("junit:junit:4.12")
}

application {
    mainClassName = "com.eazyci.test.kotlin.service.MainKt"
}