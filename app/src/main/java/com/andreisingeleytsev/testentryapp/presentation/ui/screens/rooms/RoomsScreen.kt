package com.andreisingeleytsev.testentryapp.presentation.ui.screens.rooms

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.andreisingeleytsev.testentryapp.R
import com.andreisingeleytsev.domain_layer.model.RoomModel
import com.andreisingeleytsev.domain_layer.utils.Resource
import com.andreisingeleytsev.testentryapp.presentation.ui.screens.hotel.CarouselUIItem
import com.andreisingeleytsev.testentryapp.presentation.ui.theme.DetailsGrayColor
import com.andreisingeleytsev.testentryapp.presentation.ui.theme.PrimaryBlueColor
import com.andreisingeleytsev.testentryapp.presentation.ui.theme.SecondaryGrayColor
import com.andreisingeleytsev.testentryapp.presentation.utils.Fonts
import com.andreisingeleytsev.testentryapp.presentation.utils.Routes
import com.andreisingeleytsev.testentryapp.presentation.utils.formatNumberWithSpaces

@Composable
fun RoomsScreen(navHostController: NavHostController) {
    val viewModel: RoomScreenViewModel = hiltViewModel()
    val state = viewModel.currentState.collectAsState(initial = Resource.Loading<List<RoomModel>>())
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
            state.value.data?.let { roomList ->
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(roomList) { room ->
                        RoomUIItem(room = room, navHostController = navHostController)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun RoomUIItem(room: RoomModel, navHostController: NavHostController) {
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
        val imageList = room.images
        val pagerState = rememberPagerState(pageCount = {
            imageList.size
        })
        Column(modifier = Modifier.padding(16.dp)) {
            Box {
                HorizontalPager(state = pagerState) { index ->
                    CarouselUIItem(imageId = imageList[index])
                }
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 8.dp),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(
                            horizontal = 10.dp,
                            vertical = 5.dp
                        )
                    ) {
                        for (i in imageList.indices) {
                            Card(
                                shape = CircleShape,
                                colors = CardDefaults.cardColors(
                                    containerColor = if (i == pagerState.currentPage) Color.Black
                                    else Color.Gray
                                ),
                                modifier = Modifier
                                    .padding(horizontal = 3.dp)
                                    .size(7.dp)
                            ) {

                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = room.name,
                style = TextStyle(
                    fontFamily = Fonts.font,
                    color = Color.Black,
                    fontSize = 22.sp
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                room.peculiarities.forEach {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = DetailsGrayColor
                        ),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.padding(end = 8.dp, bottom = 8.dp)
                    ) {
                        Text(
                            text = it,
                            style = TextStyle(
                                fontFamily = Fonts.font,
                                color = SecondaryGrayColor,
                                fontSize = 16.sp
                            ),
                            modifier = Modifier.padding(
                                horizontal = 10.dp,
                                vertical = 5.dp
                            )
                        )
                    }
                }
            }
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = PrimaryBlueColor.copy(alpha = 0.1F)
                ), shape = RoundedCornerShape(6.dp), modifier = Modifier.clickable(
                    indication = rememberRipple(radius = 1000.dp),
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    onClick = {})
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.more_info),
                        style = TextStyle(
                            fontFamily = Fonts.font,
                            color = PrimaryBlueColor,
                            fontSize = 18.sp
                        ),
                        modifier = Modifier
                            .padding(
                                horizontal = 10.dp,
                                vertical = 5.dp
                            )

                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        tint = PrimaryBlueColor
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = room.price.formatNumberWithSpaces() + stringResource(R.string.ruble),
                    style = TextStyle(
                        fontFamily = Fonts.font,
                        color = Color.Black,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = room.pricePer, style = TextStyle(
                        fontFamily = Fonts.font,
                        color = SecondaryGrayColor,
                        fontSize = 16.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    navHostController.navigate(Routes.BOOKING_SCREEN)
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryBlueColor
                ),
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.choose_room), style = TextStyle(
                        fontFamily = Fonts.font,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    ), modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}