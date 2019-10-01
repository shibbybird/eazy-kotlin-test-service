import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.50"
    application
    id("org.unbroken-dome.test-sets") version "2.2.0"
    id("com.github.johnrengelman.shadow") version "5.1.0"
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
    implementation("org.slf4j:slf4j-api:1.7.28")
    implementation("org.slf4j:slf4j-log4j12:1.7.28")
    implementation("io.ktor:ktor-server-core:1.2.4")
    implementation("io.ktor:ktor-server-netty:1.2.4")
    testImplementation("junit:junit:4.12")
}

application {
    mainClassName = "com.eazyci.test.kotlin.service.MainKt"
}

testSets {
    "integration"()
}

tasks.withType<KotlinCompile>().all {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

tasks.withType<Jar>{
    archiveName = "eazy-kotlin-test-service.jar"
    manifest {
        attributes(
                "Main-Class" to "com.eazyci.test.kotlin.service.MainKt"
        )
    }
}