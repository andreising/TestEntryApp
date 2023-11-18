package com.andreisingeleytsev.testentryapp.presentation.ui.screens.hotel

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.andreisingeleytsev.domain_layer.model.HotelModel
import com.andreisingeleytsev.domain_layer.utils.Resource
import com.andreisingeleytsev.testentryapp.R
import com.andreisingeleytsev.testentryapp.presentation.ui.theme.DetailsGrayColor
import com.andreisingeleytsev.testentryapp.presentation.ui.theme.PrimaryBlueColor
import com.andreisingeleytsev.testentryapp.presentation.ui.theme.RatingColor
import com.andreisingeleytsev.testentryapp.presentation.ui.theme.RatingColorBg
import com.andreisingeleytsev.testentryapp.presentation.ui.theme.SecondaryGrayColor
import com.andreisingeleytsev.testentryapp.presentation.utils.Fonts
import com.andreisingeleytsev.testentryapp.presentation.utils.Routes
import com.andreisingeleytsev.testentryapp.presentation.utils.formatNumberWithSpaces

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun HotelScreen(navHostController: NavHostController) {
    val viewModel: HotelScreenViewModel = hiltViewModel()
    val state = viewModel.currentState.collectAsState(initial = Resource.Loading<HotelModel>())
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
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = PrimaryBlueColor
                        ), shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.try_again), style = TextStyle(
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
            state.value.data?.let { info ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    val imageList = info.images
                    val pagerState = rememberPagerState(pageCount = {
                        imageList.size
                    })
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomStart = 12.dp,
                            bottomEnd = 12.dp
                        ), colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
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
                            Spacer(modifier = Modifier.height(16.dp))
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
                                        text = info.rating.toString() + " " + info.ratingName,
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
                                text = info.name, style = TextStyle(
                                    fontFamily = Fonts.font,
                                    color = Color.Black,
                                    fontSize = 22.sp
                                )
                            )
                            TextButton(
                                onClick = { },
                                contentPadding = PaddingValues(horizontal = 3.dp, vertical = 0.dp)
                            ) {
                                Text(
                                    text = info.address,
                                    style = TextStyle(
                                        fontFamily = Fonts.font,
                                        color = PrimaryBlueColor,
                                        fontSize = 14.sp
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = stringResource(R.string.from)
                                            + info.minPrice.formatNumberWithSpaces()
                                            + stringResource(R.string.ruble),
                                    style = TextStyle(
                                        fontFamily = Fonts.font,
                                        color = Color.Black,
                                        fontSize = 30.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = info.priceForIt, style = TextStyle(
                                        fontFamily = Fonts.font,
                                        color = SecondaryGrayColor,
                                        fontSize = 16.sp
                                    )
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = stringResource(R.string.about_hotel), style = TextStyle(
                                    fontFamily = Fonts.font,
                                    color = Color.Black,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            FlowRow(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                info.peculiarities.forEach {
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
                            Text(
                                text = info.description, style = TextStyle(
                                    fontFamily = Fonts.font,
                                    color = Color.Black,
                                    fontSize = 18.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = DetailsGrayColor
                                )
                            ) {
                                val detailsList = listOf(
                                    Triple(
                                        R.drawable.icon_smile,
                                        stringResource(R.string.facilities),
                                        stringResource(R.string.essentials)
                                    ),
                                    Triple(
                                        R.drawable.icon_add,
                                        stringResource(R.string.uncluded),
                                        stringResource(R.string.essentials)
                                    ),
                                    Triple(
                                        R.drawable.icon_not_add,
                                        stringResource(R.string.not_included),
                                        stringResource(R.string.essentials)
                                    )
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                detailsList.forEach { detail ->
                                    DetailsUIItem(
                                        image = detail.first,
                                        name = detail.second,
                                        description = detail.third
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                            }
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
                                navHostController.navigate(Routes.ROOMS_SCREEN)
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
                                text = stringResource(R.string.to_choose_room), style = TextStyle(
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
fun DetailsUIItem(image: Int, name: String, description: String) {
    Row(
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 5.dp)
            .fillMaxWidth()
            .clickable(indication = rememberRipple(radius = 1000.dp), interactionSource = remember {
                MutableInteractionSource()
            }, onClick = {}),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = name, style = TextStyle(
                        fontFamily = Fonts.font,
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                )
                Text(
                    text = description, style = TextStyle(
                        fontFamily = Fonts.font,
                        color = SecondaryGrayColor,
                        fontSize = 14.sp
                    )
                )
            }
        }
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.Black
        )
    }
}

