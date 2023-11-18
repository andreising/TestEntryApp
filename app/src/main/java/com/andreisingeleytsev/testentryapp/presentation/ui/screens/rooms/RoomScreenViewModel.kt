package com.andreisingeleytsev.testentryapp.presentation.ui.screens.rooms

import androidx.lifecycle.ViewModel
import com.andreisingeleytsev.domain_layer.usecases.GetRoomsInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoomScreenViewModel @Inject constructor(
    private val useCase: com.andreisingeleytsev.domain_layer.usecases.GetRoomsInfoUseCase
): ViewModel() {
    val currentState = useCase.invoke()
}