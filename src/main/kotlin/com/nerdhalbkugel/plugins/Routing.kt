package com.nerdhalbkugel.plugins

import com.nerdhalbkugel.domain.BroadcastMessageService
import com.nerdhalbkugel.domain.RegisterDeviceService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    @Serializable
    data class RegistrationRequest(
        @SerialName("token") val token: String
    )

    @Serializable
    data class BroadcastRequest(
        @SerialName("title") val title: String,
        @SerialName("body") val body: String
    )

    val registerDeviceService: RegisterDeviceService by inject()
    val broadcastMessageService: BroadcastMessageService by inject()

    routing {
        post("/register") {
            val request = call.receive<RegistrationRequest>()
            call.respond(registerDeviceService.run(registrationToken = request.token))
        }
        post("/broadcast"){
            val request = call.receive<BroadcastRequest>()
            broadcastMessageService.run(
                title = request.title,
                body = request.body
            )
            call.respond(HttpStatusCode.OK)
        }
    }
}
