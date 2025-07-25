val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val serialization_json_version: String by project
val exposed_version: String by project
val hikari_version: String by project
val postgres_version: String by project
val h2_version: String by project
val koin_version: String by project
val bcrypt_version: String by project
val firebase_admin_version: String by project
val joda_time_version: String by project
val mockk_version: String by project
val junit_version: String by project
val otp_version: String by project
val kotlinx_html: String by project
val coroutines_version: String by project

plugins {
    kotlin("jvm") version "2.2.0"
    kotlin("plugin.serialization") version "2.2.0"
    id("org.jlleitschuh.gradle.ktlint") version "11.5.1"

    application
}

application {
    mainClass.set("ApplicationKt")
    ktlint {
        enableExperimentalRules.set(true)
    }
}

repositories {
    mavenCentral()
}

tasks.create("stage").dependsOn("installDist")

tasks.test {
    useJUnit()
}

dependencies {
    // Ktor server
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("io.ktor:ktor-server-websockets:$ktor_version")
    implementation("io.ktor:ktor-serialization:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("io.ktor:ktor-server-auth:$ktor_version")
    implementation("io.ktor:ktor-server-sse:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt:$ktor_version")
    implementation("io.ktor:ktor-server-html-builder:$ktor_version")

    // Ktor client
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-client-okhttp-jvm:$ktor_version")
    implementation("io.ktor:ktor-client-logging:$ktor_version")

    implementation("ch.qos.logback:logback-classic:$logback_version")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serialization_json_version")

    // Database
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("com.zaxxer:HikariCP:$hikari_version")
    implementation("org.postgresql:postgresql:$postgres_version")

    // DI
    implementation("io.insert-koin:koin-ktor:$koin_version")

    // Hashing
    implementation("com.ToxicBakery.library.bcrypt:bcrypt:$bcrypt_version")

    // Firebase
    implementation("com.google.firebase:firebase-admin:$firebase_admin_version") {
        exclude("com.google.guava")
    }

    // DateTime
    implementation("joda-time:joda-time:$joda_time_version")

    // OTP
    implementation("dev.turingcomplete:kotlin-onetimepassword:$otp_version")

    // html
    implementation("org.jetbrains.kotlinx:kotlinx-html:$kotlinx_html")

    // Test
    testImplementation("io.mockk:mockk:$mockk_version")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutines_version")
    testImplementation(kotlin("test"))
}
