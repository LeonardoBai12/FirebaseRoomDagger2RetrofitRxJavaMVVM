package io.lb.firebaseexample.user_feature.presentation.login

sealed class LoginEvent {
    data class EnteredEmail(val value: String): LoginEvent()
    data class EnteredPassword(val value: String): LoginEvent()
    object PressedLogin : LoginEvent()
    object PressedSignIn : LoginEvent()
}