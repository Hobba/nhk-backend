package com.nerdhalbkugel.plugins

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import io.ktor.server.application.*
import java.io.FileInputStream

fun Application.configureFirebase(){
    val serviceAccountFile = FileInputStream("/Users/robin/Dev/service-account-file.json")

    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccountFile))
        .build()

    FirebaseApp.initializeApp(options)
}
