package com.andreisingeleytsev.testentryapp.presentation.ui.screens.success

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SuccessScreenViewModel: ViewModel() {
    private val _orderNumber = mutableStateOf(0)
    val orderNumber: State<Int> = _orderNumber

    init {
        _orderNumber.value = (100000..999999).random()
    }
}