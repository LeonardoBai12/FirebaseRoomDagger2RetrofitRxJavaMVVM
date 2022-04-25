package io.lb.firebaseexample.notification_feature.data.data_source

import io.lb.firebaseexample.notification_feature.domain.model.PushNotification
import io.lb.firebaseexample.util.GeneralConstants.CONTENT_TYPE
import io.lb.firebaseexample.util.GeneralConstants.SERVER_KEY
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationService {
    @Headers("Authorization: key=$SERVER_KEY","Content_Type:$CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification: PushNotification
    ): Response<ResponseBody>
}