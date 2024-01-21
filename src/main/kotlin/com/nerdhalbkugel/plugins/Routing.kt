package com.nerdhalbkugel.plugins

import com.nerdhalbkugel.domain.RegisterDeviceService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.koin.java.KoinJavaComponent.inject
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    @Serializable
    data class RegistrationRequest(
        @SerialName("token") val token: String
    )

    val registerDeviceService: RegisterDeviceService by inject()

    routing {
        post("/register") {
            val request = call.receive<RegistrationRequest>()
            registerDeviceService.register(registrationToken = request.token)
        }
    }
}
