package com.nerdhalbkugel.domain

import com.google.api.core.ApiFuture
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.WriteResult
import com.google.firebase.cloud.FirestoreClient
import io.ktor.http.*
import java.util.*


class RegisterDeviceService {
    fun run(registrationToken: String): HttpStatusCode {
        val db: Firestore = FirestoreClient.getFirestore()
        val query = db.collection("tokens").get()
        val querySnapshot = query.get()
        val documents = querySnapshot.documents
        val tokenExists = documents.any { it.data["value"] == registrationToken }

        if (!tokenExists) {
            val docRef: DocumentReference = db.collection("tokens").document(UUID.randomUUID().toString())

            val data: MutableMap<String, Any> = HashMap()
            data["value"] = registrationToken

            val result: ApiFuture<WriteResult> = docRef.set(data)
            return HttpStatusCode.Created
        } else {
            return HttpStatusCode.OK
        }
    }
}
