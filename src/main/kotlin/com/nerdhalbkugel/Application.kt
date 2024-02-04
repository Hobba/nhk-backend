package com.nerdhalbkugel

import com.nerdhalbkugel.domain.BroadcastMessageService
import com.nerdhalbkugel.domain.RegisterDeviceService
import com.nerdhalbkugel.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.core.logger.Level
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.startKoin

val modules = listOf(
    module {
        single { RegisterDeviceService() }
        single { BroadcastMessageService() }
    }
)

val appEnv = System.getenv("APP_ENV") ?: "local"

fun main() {
    startKoin(modules, Level.INFO)
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
    configureFirebase(appEnv)
}


