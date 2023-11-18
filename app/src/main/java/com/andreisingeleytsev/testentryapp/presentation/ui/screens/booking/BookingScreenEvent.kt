package com.andreisingeleytsev.testentryapp.presentation.ui.screens.booking

sealed class BookingScreenEvent {
    object OnAddGuest : BookingScreenEvent()

    object OnDoneEmail: BookingScreenEvent()

    object OnGoEmail: BookingScreenEvent()

    object OnDonePhone: BookingScreenEvent()

    object OnGoPhone: BookingScreenEvent()
    data class OnDeleteGuest(val index: Int) : BookingScreenEvent()
    data class OnChangeGuestInfo(
        val index: Int,
        val category: GuestCategory,
        val newValue: Pair<String, Boolean>
    ) : BookingScreenEvent()
    data class OnPhoneNumberChange(val text: String): BookingScreenEvent()
    data class OnEmailChanged(val text: String): BookingScreenEvent()
    object OnFinish: BookingScreenEvent()
}
