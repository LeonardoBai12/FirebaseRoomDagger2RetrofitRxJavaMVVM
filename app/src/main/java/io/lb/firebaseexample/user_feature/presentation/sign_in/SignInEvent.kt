package io.lb.firebaseexample.user_feature.presentation.sign_in

sealed class SignInEvent {
    data class EnteredEmail(val value: String): SignInEvent()
    data class EnteredPassword(val value: String): SignInEvent()
    data class EnteredRepeatPassword(val value: String): SignInEvent()
    data class EnteredName(val value: String): SignInEvent()
    object PressedSignIn : SignInEvent()
}