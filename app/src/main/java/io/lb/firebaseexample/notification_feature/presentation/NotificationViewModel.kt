package io.lb.firebaseexample.notification_feature.presentation

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.lb.firebaseexample.notification_feature.domain.use_case.NotificationUseCases
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val useCases: NotificationUseCases,
): AndroidViewModel(context as Application) {
    fun onEvent(event: NotificationEvent) {
        viewModelScope.launch {
            when (event) {
                is NotificationEvent.OnNotificationSent -> {
                    useCases.sendPushNotificationToUseCase(
                        event.title,
                        event.message,
                        event.topic,
                    )
                }
                is NotificationEvent.OnInitializeMessaging -> {
                    useCases.initializeFireBaseMessagingUseCase(event.topic)
                }
                is NotificationEvent.OnScheduleNotification -> {
                    useCases.sendScheduledNotificationUseCase(
                        event.title,
                        event.day,
                        event.month,
                        event.year,
                    )
                }
                is NotificationEvent.OnScheduleNotificationDeactivate -> {
                    useCases.deactivateScheduledNotificationUseCase(
                        event.day,
                        event.month,
                        event.year,
                    )
                }
            }
        }
    }
}