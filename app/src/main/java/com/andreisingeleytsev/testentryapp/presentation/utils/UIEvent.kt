package com.andreisingeleytsev.testentryapp.presentation.utils

sealed class UIEvent{
    object PopBackStack: UIEvent()
    data class Navigate(val route: String): UIEvent()
    data class Toast(val message: String): UIEvent()
}
