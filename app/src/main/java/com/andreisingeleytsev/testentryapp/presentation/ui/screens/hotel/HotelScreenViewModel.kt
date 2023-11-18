package com.andreisingeleytsev.testentryapp.presentation.ui.screens.hotel

import androidx.lifecycle.ViewModel
import com.andreisingeleytsev.domain_layer.usecases.GetHotelInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HotelScreenViewModel @Inject constructor(
    useCase: GetHotelInfoUseCase
): ViewModel() {
    val currentState = useCase.invoke()
}