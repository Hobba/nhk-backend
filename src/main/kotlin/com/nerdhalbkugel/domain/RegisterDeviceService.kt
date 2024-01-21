package com.nerdhalbkugel.domain

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import java.io.FileInputStream


class RegisterDeviceService {

    fun register(registrationToken: String) {
        val refreshToken = FileInputStream("/Users/robin/Dev/service-account-file.json")

        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(refreshToken))
            .build()

        FirebaseApp.initializeApp(options)

        val message: Message = Message.builder()
            .setNotification(
                Notification.builder()
                    .setTitle("Title")
                    .setBody("Body")
                    .build()
            )
            .setToken(registrationToken)
            .build()

        FirebaseMessaging.getInstance().send(message)
    }

}
