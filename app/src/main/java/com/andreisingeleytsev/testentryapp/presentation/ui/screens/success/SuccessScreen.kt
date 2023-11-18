package com.andreisingeleytsev.testentryapp.presentation.ui.screens.success

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.andreisingeleytsev.testentryapp.R
import com.andreisingeleytsev.testentryapp.presentation.ui.theme.PrimaryBlueColor
import com.andreisingeleytsev.testentryapp.presentation.utils.Fonts
import com.andreisingeleytsev.testentryapp.presentation.utils.Routes

@Composable
fun SuccessScreen(navHostController: NavHostController) {
    val viewModel: SuccessScreenViewModel = hiltViewModel()
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .align(
                    Alignment.Center
                )
                .padding(horizontal = 24.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.image_success),
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = stringResource(R.string.order_in_progress),
                style = TextStyle(
                    fontFamily = Fonts.font,
                    color = Color.Black,
                    fontSize = 22.sp
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.succes_screen_title_1)
                        +
                        viewModel.orderNumber.value
                        +
                        stringResource(R.string.succes_screen_title_2),
                style = TextStyle(
                    fontFamily = Fonts.font,
                    color = Color.Gray,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            )
        }

        Button(
            onClick = {
                navHostController.navigate(Routes.HOTEL_SCREEN) {
                    popUpTo(Routes.HOTEL_SCREEN) {
                        inclusive = true
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp), colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryBlueColor
            )
        ) {
            Text(
                text = stringResource(R.string.great),
                style = TextStyle(
                    fontFamily = Fonts.font,
                    color = Color.White,
                    fontSize = 16.sp
                )
            )
        }
    }
}