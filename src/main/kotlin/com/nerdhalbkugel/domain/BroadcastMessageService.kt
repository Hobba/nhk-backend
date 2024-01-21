package com.nerdhalbkugel.domain

import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.QuerySnapshot
import com.google.firebase.cloud.FirestoreClient
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification

class BroadcastMessageService {

    fun run(title: String, body: String) {
        val db: Firestore = FirestoreClient.getFirestore()
        val query = db.collection("tokens").get()
        val querySnapshot: QuerySnapshot = query.get()
        val documents = querySnapshot.documents
        for (document in documents) {
            val message: Message = Message.builder()
                .setNotification(
                    Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build()
                )
                .setToken(document.getString("value"))
                .build()

            FirebaseMessaging.getInstance().send(message)
        }
    }
}
