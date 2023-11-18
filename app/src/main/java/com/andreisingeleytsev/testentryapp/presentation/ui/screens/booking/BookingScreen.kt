package com.andreisingeleytsev.testentryapp.presentation.ui.screens.booking


import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.andreisingeleytsev.testentryapp.R
import com.andreisingeleytsev.domain_layer.model.BookingInfoModel
import com.andreisingeleytsev.domain_layer.utils.Resource
import com.andreisingeleytsev.testentryapp.presentation.ui.theme.ErrorColor
import com.andreisingeleytsev.testentryapp.presentation.ui.theme.MainBGColor
import com.andreisingeleytsev.testentryapp.presentation.ui.theme.PrimaryBlueColor
import com.andreisingeleytsev.testentryapp.presentation.ui.theme.RatingColor
import com.andreisingeleytsev.testentryapp.presentation.ui.theme.RatingColorBg
import com.andreisingeleytsev.testentryapp.presentation.ui.theme.SecondaryGrayColor
import com.andreisingeleytsev.testentryapp.presentation.utils.Fonts
import com.andreisingeleytsev.testentryapp.presentation.utils.IndexStringConverter
import com.andreisingeleytsev.testentryapp.presentation.utils.PhoneNumberFilter
import com.andreisingeleytsev.testentryapp.presentation.utils.UIEvent
import com.andreisingeleytsev.testentryapp.presentation.utils.formatNumberWithSpaces

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun BookingScreen(navHostController: NavHostController) {
    val viewModel: BookingScreenViewModel = hiltViewModel()
    val context = LocalContext.current.applicationContext
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when (it) {
                is UIEvent.Navigate -> {
                    navHostController.navigate(it.route)
                }

                is UIEvent.Toast -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }
    val state =
        viewModel.currentState.collectAsState(initial = Resource.Loading<BookingInfoModel>())
    var focusedKey by remember { mutableStateOf(false) }
    when (state.value) {
        is Resource.Loading -> {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(color = PrimaryBlueColor)
            }
        }

        is Resource.Error -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    state.value.message?.let {
                        Text(
                            text = it, style = TextStyle(
                                fontFamily = Fonts.font,
                                color = Color.Black,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryBlueColor
                        ), shape = RoundedCornerShape(16.dp), onClick = {
                            navHostController.popBackStack()
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.back), style = TextStyle(
                                fontFamily = Fonts.font,
                                color = Color.White,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            ), modifier = Modifier
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }

        is Resource.Success -> {
            state.value.data?.let { bookingInfo ->
                val total =
                    (bookingInfo.tourPrice + bookingInfo.fuelCharge + bookingInfo.serviceCharge) * viewModel.guestItemList.value.size
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Card(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(
                            12.dp
                        ), colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = RatingColorBg
                                )
                            ) {
                                Row(
                                    modifier = Modifier.padding(
                                        horizontal = 10.dp,
                                        vertical = 5.dp
                                    ), verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = RatingColor,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = bookingInfo.horating.toString() + " " + bookingInfo.ratingName,
                                        style = TextStyle(
                                            fontFamily = Fonts.font,
                                            color = RatingColor,
                                            fontSize = 16.sp
                                        )
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = bookingInfo.hotelName,
                                style = TextStyle(
                                    fontFamily = Fonts.font,
                                    color = Color.Black,
                                    fontSize = 22.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = bookingInfo.hotelAddress,
                                style = TextStyle(
                                    fontFamily = Fonts.font,
                                    color = PrimaryBlueColor,
                                    fontSize = 14.sp
                                )
                            )
                        }
                    }
                    Card(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(
                            12.dp
                        ), colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            InfoItem(
                                string1 = stringResource(R.string.arrive_from),
                                string2 = bookingInfo.departure
                            )
                            InfoItem(
                                string1 = stringResource(R.string.geo_info),
                                string2 = bookingInfo.arrivalCountry
                            )
                            InfoItem(
                                string1 = stringResource(R.string.dates),
                                string2 = bookingInfo.tourDateStart + " - " + bookingInfo.tourDateStop
                            )
                            InfoItem(
                                string1 = stringResource(R.string.nights),
                                string2 = bookingInfo.numberOfNights.toString() + stringResource(R.string.nights_1)
                            )
                            InfoItem(
                                string1 = stringResource(R.string.hotel),
                                string2 = bookingInfo.hotelName
                            )
                            InfoItem(
                                string1 = stringResource(R.string.room),
                                string2 = bookingInfo.room
                            )
                            InfoItem(
                                string1 = stringResource(R.string.nutrition),
                                string2 = bookingInfo.nutrition
                            )
                        }
                    }
                    Card(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(
                            12.dp
                        ), colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = stringResource(R.string.buyer_info),
                                style = TextStyle(
                                    fontFamily = Fonts.font,
                                    color = Color.Black,
                                    fontSize = 22.sp
                                )
                            )
                            val keyboardController = LocalSoftwareKeyboardController.current
                            Spacer(modifier = Modifier.height(20.dp))
                            TextField(
                                value = viewModel.phoneNumber.value,
                                onValueChange = { phomeNumber ->
                                    if (phomeNumber.length <= 10) {
                                        viewModel.onEvent(
                                            BookingScreenEvent.OnPhoneNumberChange(
                                                phomeNumber.takeWhile { it.isDigit() })
                                        )
                                    }
                                },
                                label = {
                                    Text(
                                        stringResource(R.string.phone_number),
                                        fontFamily = Fonts.font,
                                        color = SecondaryGrayColor
                                    )
                                },
                                placeholder = {
                                    Text(
                                        "+7 (***) ***-**-**",
                                        fontFamily = Fonts.font,
                                        color = Color.Black
                                    )
                                },
                                visualTransformation = PhoneNumberFilter.phoneNumberFilter,
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number,
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        keyboardController?.hide()
                                    }
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(BorderStroke(0.dp, Color.Transparent))
                                    .onFocusChanged { focusState ->
                                        if (focusedKey) {
                                            viewModel.onEvent(
                                                if (focusState.isFocused) BookingScreenEvent.OnGoPhone
                                                else BookingScreenEvent.OnDonePhone
                                            )
                                        } else {
                                            if (focusState.isFocused) focusedKey = true
                                        }
                                    },
                                shape = RoundedCornerShape(10.dp),
                                colors = TextFieldDefaults.textFieldColors(
                                    containerColor = if (!viewModel.phoneNumberIsError.value) MainBGColor
                                    else ErrorColor,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent
                                ),
                                textStyle = TextStyle(
                                    fontFamily = Fonts.font,
                                    color = Color.Black,
                                    fontSize = 16.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            TextField(
                                value = viewModel.email.value,
                                onValueChange = {
                                    viewModel.onEvent(BookingScreenEvent.OnEmailChanged(it))
                                },
                                label = {
                                    Text(
                                        stringResource(R.string.mail),
                                        fontFamily = Fonts.font,
                                        color = SecondaryGrayColor
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(BorderStroke(0.dp, Color.Transparent))
                                    .onFocusChanged { focusState ->
                                        if (focusedKey) {
                                            viewModel.onEvent(
                                                if (focusState.isFocused) BookingScreenEvent.OnGoEmail
                                                else BookingScreenEvent.OnDoneEmail
                                            )
                                        } else {
                                            if (focusState.isFocused) focusedKey = true
                                        }
                                    },
                                shape = RoundedCornerShape(10.dp),
                                colors = TextFieldDefaults.textFieldColors(
                                    containerColor = if (viewModel.emailIsError.value) ErrorColor
                                    else MainBGColor,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent
                                ),
                                textStyle = TextStyle(
                                    fontFamily = Fonts.font,
                                    color = Color.Black,
                                    fontSize = 16.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                stringResource(R.string.booking_info_1),
                                style = TextStyle(
                                    fontFamily = Fonts.font,
                                    color = SecondaryGrayColor,
                                    fontSize = 14.sp
                                )
                            )
                        }
                    }
                    Column(modifier = Modifier.fillMaxWidth()) {
                        viewModel.guestItemList.value.forEachIndexed { index, item ->
                            GuestUIItem(guestItem = item.value, index = index)
                        }
                    }
                    Card(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(
                            12.dp
                        ), colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(R.string.add_guest),
                                    style = TextStyle(
                                        fontFamily = Fonts.font,
                                        color = Color.Black,
                                        fontSize = 22.sp
                                    )
                                )
                                IconButton(
                                    onClick = {
                                        viewModel.onEvent(BookingScreenEvent.OnAddGuest)
                                    },
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(
                                            PrimaryBlueColor
                                        )
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    }
                    Card(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(
                            12.dp
                        ), colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            PriceUIItem(
                                string = stringResource(R.string.tour),
                                coast = bookingInfo.tourPrice,
                                isTotal = false
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            PriceUIItem(
                                string = stringResource(R.string.fuel_charge),
                                coast = bookingInfo.fuelCharge,
                                isTotal = false
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            PriceUIItem(
                                string = stringResource(R.string.service_charge),
                                coast = bookingInfo.serviceCharge,
                                isTotal = false
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            PriceUIItem(
                                string = stringResource(R.string.to_pay),
                                coast = total,
                                isTotal = true
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                    ) {
                        Button(
                            onClick = {
                                viewModel.onEvent(BookingScreenEvent.OnFinish)
                            },
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PrimaryBlueColor
                            ),
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(R.string.pay) + total.formatNumberWithSpaces() + stringResource(
                                    id = R.string.ruble
                                ),
                                style = TextStyle(
                                    fontFamily = Fonts.font,
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PriceUIItem(string: String, coast: Int, isTotal: Boolean) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = string,
            style = TextStyle(
                fontFamily = Fonts.font,
                color = SecondaryGrayColor,
                fontSize = 16.sp
            )
        )
        Text(
            text = coast.formatNumberWithSpaces() + stringResource(
                id = R.string.ruble
            ),
            style = TextStyle(
                fontFamily = Fonts.font,
                color = if (isTotal) PrimaryBlueColor
                else Color.Black,
                fontSize = 16.sp,
                fontWeight = if (isTotal) FontWeight.SemiBold
                else FontWeight.Normal
            )
        )
    }
}

@Composable
fun InfoItem(string1: String, string2: String) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = string1,
            style = TextStyle(
                fontFamily = Fonts.font,
                color = SecondaryGrayColor,
                fontSize = 16.sp
            ), modifier = Modifier.weight(2f)
        )
        Text(
            text = string2,
            style = TextStyle(
                fontFamily = Fonts.font,
                color = Color.Black,
                fontSize = 16.sp
            ), modifier = Modifier.weight(3f)
        )
    }
}

@Composable
fun GuestUIItem(guestItem: GuestItem, index: Int) {
    val viewModel: BookingScreenViewModel = hiltViewModel()
    val isHidden = remember {
        mutableStateOf(true)
    }
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(
            12.dp
        ), colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = IndexStringConverter.fromIndexToString(index) + stringResource(R.string.guest),
                    style = TextStyle(
                        fontFamily = Fonts.font,
                        color = Color.Black,
                        fontSize = 22.sp
                    )
                )
                Row {
                    if (index != 0) IconButton(onClick = {
                        viewModel.onEvent(BookingScreenEvent.OnDeleteGuest(index))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = PrimaryBlueColor
                        )
                    }
                    IconButton(onClick = {
                        isHidden.value = !isHidden.value
                    }) {
                        Icon(
                            imageVector = if (isHidden.value) Icons.Default.KeyboardArrowDown
                            else Icons.Default.KeyboardArrowUp,
                            contentDescription = null,
                            tint = PrimaryBlueColor
                        )
                    }
                }
            }
            if (!isHidden.value) {
                DefaultTextField(
                    text = guestItem.name.first,
                    description = stringResource(R.string.name),
                    isError = guestItem.name.second,
                    onChange = { string ->
                        viewModel.onEvent(
                            BookingScreenEvent.OnChangeGuestInfo(
                                index = index,
                                category = GuestCategory.NAME,
                                newValue = Pair(string, false)
                            )
                        )
                    })
                DefaultTextField(
                    text = guestItem.surname.first,
                    description = stringResource(R.string.surname),
                    isError = guestItem.surname.second,
                    onChange = { string ->
                        viewModel.onEvent(
                            BookingScreenEvent.OnChangeGuestInfo(
                                index = index,
                                category = GuestCategory.SURNAME,
                                newValue = Pair(string, false)
                            )
                        )
                    })
                DefaultTextField(
                    text = guestItem.birthDate.first,
                    description = stringResource(R.string.birth_date),
                    isError = guestItem.birthDate.second,
                    onChange = { string ->
                        viewModel.onEvent(
                            BookingScreenEvent.OnChangeGuestInfo(
                                index = index,
                                category = GuestCategory.BIRTHDATE,
                                newValue = Pair(string, false)
                            )
                        )
                    })
                DefaultTextField(
                    text = guestItem.citizenship.first,
                    description = stringResource(R.string.citiship),
                    isError = guestItem.citizenship.second,
                    onChange = { string ->
                        viewModel.onEvent(
                            BookingScreenEvent.OnChangeGuestInfo(
                                index = index,
                                category = GuestCategory.CITIZENSHIP,
                                newValue = Pair(string, false)
                            )
                        )
                    })
                DefaultTextField(
                    text = guestItem.passportNumber.first,
                    description = stringResource(R.string.passport_number),
                    isError = guestItem.passportNumber.second,
                    onChange = { string ->
                        viewModel.onEvent(
                            BookingScreenEvent.OnChangeGuestInfo(
                                index = index,
                                category = GuestCategory.PASSPORT_NUMBER,
                                newValue = Pair(string, false)
                            )
                        )
                    })
                DefaultTextField(
                    text = guestItem.passportValidityPeriod.first,
                    description = stringResource(R.string.passport_time),
                    isError = guestItem.passportValidityPeriod.second,
                    onChange = { string ->
                        viewModel.onEvent(
                            BookingScreenEvent.OnChangeGuestInfo(
                                index = index,
                                category = GuestCategory.PASSPORT_PERIOD,
                                newValue = Pair(string, false)
                            )
                        )
                    })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTextField(
    text: String,
    description: String,
    isError: Boolean,
    onChange: (String) -> Unit
) {
    TextField(
        value = text,
        onValueChange = {
            onChange.invoke(it)
        },
        label = {
            Text(
                description,
                fontFamily = Fonts.font,
                color = SecondaryGrayColor
            )
        },
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
            .border(BorderStroke(0.dp, Color.Transparent)),
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = if (!isError) MainBGColor else ErrorColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        textStyle = TextStyle(
            fontFamily = Fonts.font,
            color = Color.Black,
            fontSize = 16.sp
        )
    )
}
