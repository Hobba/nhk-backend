package com.nerdhalbkugel.plugins

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import io.ktor.server.application.*

fun Application.configureFirebase(appEnv: String){
    if (appEnv == "local") {
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.getApplicationDefault())
            .setProjectId("nerdhalbkugel-backend")
            .build()

        FirebaseApp.initializeApp(options)
    } else {
        FirebaseApp.initializeApp()
    }
}
