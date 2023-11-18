package com.andreisingeleytsev.testentryapp.presentation.ui.screens.booking


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreisingeleytsev.domain_layer.usecases.GetBookingInfoUseCase
import com.andreisingeleytsev.testentryapp.presentation.utils.Routes
import com.andreisingeleytsev.testentryapp.presentation.utils.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingScreenViewModel @Inject constructor(
    useCase: GetBookingInfoUseCase
) : ViewModel() {

    val currentState = useCase.invoke()

    private val _phoneNumber = mutableStateOf("")
    val phoneNumber: State<String> = _phoneNumber

    private val _phoneNumberIsError = mutableStateOf(false)
    val phoneNumberIsError: State<Boolean> = _phoneNumberIsError

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _emailIsError = mutableStateOf(false)
    val emailIsError: State<Boolean> = _emailIsError

    private val _guessItemList = mutableStateOf(
        (listOf(
            mutableStateOf(GuestItem())
        ))
    )

    val guestItemList: State<List<MutableState<GuestItem>>> = _guessItemList

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private fun sendUIEvent(event: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    fun onEvent(event: BookingScreenEvent) {
        when (event) {
            is BookingScreenEvent.OnAddGuest -> {
                addNewGuest()
            }

            is BookingScreenEvent.OnDeleteGuest -> {
                deleteGuest(event.index)
            }

            is BookingScreenEvent.OnChangeGuestInfo -> {
                event.apply {
                    changeGuestInfo(index, category, newValue)
                }
            }

            is BookingScreenEvent.OnEmailChanged -> {
                _email.value = event.text
            }

            is BookingScreenEvent.OnPhoneNumberChange -> {
                _phoneNumber.value = event.text
            }

            is BookingScreenEvent.OnDoneEmail -> {
                _emailIsError.value = !isValidEmail()
            }

            is BookingScreenEvent.OnGoEmail -> {
                _emailIsError.value = false
            }

            is BookingScreenEvent.OnGoPhone -> {
                _phoneNumberIsError.value = false
            }

            is BookingScreenEvent.OnDonePhone -> {
                _phoneNumberIsError.value = _phoneNumber.value.length != 10
            }

            is BookingScreenEvent.OnFinish -> {
                if (isValidEmail() && _phoneNumber.value.length == 10 && checkGuestData()) sendUIEvent(
                    UIEvent.Navigate(Routes.SUCCESS_SCREEN)
                )
                else viewModelScope.launch {
                    sendUIEvent(UIEvent.Toast("Вы заполнили не все поля"))
                    addErrorColor()
                }


            }
        }
    }

    private fun addErrorColor() {
        _guessItemList.value.forEachIndexed { index, guestList ->
            guestList.value.apply {
                if (name.first.isEmpty()) changeGuestInfo(
                    index,
                    GuestCategory.NAME,
                    Pair(name.first, true)
                )
                if (surname.first.isEmpty()) changeGuestInfo(
                    index,
                    GuestCategory.SURNAME,
                    Pair(surname.first, true)
                )
                if (birthDate.first.isEmpty()) changeGuestInfo(
                    index,
                    GuestCategory.BIRTHDATE,
                    Pair(birthDate.first, true)
                )
                if (citizenship.first.isEmpty()) changeGuestInfo(
                    index,
                    GuestCategory.CITIZENSHIP,
                    Pair(citizenship.first, true)
                )
                if (passportNumber.first.isEmpty()) changeGuestInfo(
                    index,
                    GuestCategory.PASSPORT_NUMBER,
                    Pair(passportNumber.first, true)
                )
                if (passportValidityPeriod.first.isEmpty()) changeGuestInfo(
                    index,
                    GuestCategory.PASSPORT_PERIOD,
                    Pair(passportValidityPeriod.first, true)
                )
            }
        }
    }

    private fun checkGuestData(): Boolean {
        var result = true
        _guessItemList.value.forEach { guestList ->
            guestList.value.apply {
                if (name.first.isEmpty()) result = false
                if (surname.first.isEmpty()) result = false
                if (birthDate.first.isEmpty()) result = false
                if (citizenship.first.isEmpty()) result = false
                if (passportNumber.first.isEmpty()) result = false
                if (passportValidityPeriod.first.isEmpty()) result = false
            }
        }
        return result
    }


    private fun changeGuestInfo(
        index: Int,
        category: GuestCategory,
        newValue: Pair<String, Boolean>
    ) {
        var item = _guessItemList.value[index].value
        item = when (category) {
            GuestCategory.NAME -> item.copy(name = newValue)
            GuestCategory.SURNAME -> item.copy(surname = newValue)
            GuestCategory.BIRTHDATE -> item.copy(birthDate = newValue)
            GuestCategory.CITIZENSHIP -> item.copy(citizenship = newValue)
            GuestCategory.PASSPORT_NUMBER -> item.copy(passportNumber = newValue)
            GuestCategory.PASSPORT_PERIOD -> item.copy(passportValidityPeriod = newValue)
        }
        _guessItemList.value[index].value = item
    }

    private fun deleteGuest(index: Int) {
        val list = _guessItemList.value.toMutableList()
        list.removeAt(index)
        _guessItemList.value = list
    }

    private fun addNewGuest() {
        val list = _guessItemList.value.toMutableList()
        list.add(mutableStateOf(GuestItem()))
        _guessItemList.value = list
    }

    private fun isValidEmail(): Boolean {
        val emailRegex = Regex("""^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}$""")
        return emailRegex.matches(_email.value)
    }

}

data class GuestItem(
    val name: Pair<String, Boolean> = Pair("", false),
    val surname: Pair<String, Boolean> = Pair("", false),
    val birthDate: Pair<String, Boolean> = Pair("", false),
    val citizenship: Pair<String, Boolean> = Pair("", false),
    val passportNumber: Pair<String, Boolean> = Pair("", false),
    val passportValidityPeriod: Pair<String, Boolean> = Pair("", false)
)

enum class GuestCategory {
    NAME, SURNAME, BIRTHDATE, CITIZENSHIP, PASSPORT_NUMBER, PASSPORT_PERIOD
}