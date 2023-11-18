package com.andreisingeleytsev.testentryapp.presentation.navigation


import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.andreisingeleytsev.testentryapp.presentation.ui.screens.booking.BookingScreen
import com.andreisingeleytsev.testentryapp.presentation.ui.screens.hotel.HotelScreen
import com.andreisingeleytsev.testentryapp.presentation.ui.screens.rooms.RoomsScreen
import com.andreisingeleytsev.testentryapp.presentation.ui.screens.success.SuccessScreen
import com.andreisingeleytsev.testentryapp.presentation.ui.theme.MainBGColor
import com.andreisingeleytsev.testentryapp.presentation.ui.theme.SecondaryGrayColor
import com.andreisingeleytsev.testentryapp.presentation.utils.Routes


@Composable
fun MainNavigationGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController, startDestination = Routes.HOTEL_SCREEN,
        modifier = Modifier.background(MainBGColor)
    ) {
        composable(Routes.HOTEL_SCREEN) {
            HotelScreen(navHostController = navHostController)
        }
        composable(Routes.ROOMS_SCREEN) {
            RoomsScreen(navHostController = navHostController)
        }
        composable(Routes.BOOKING_SCREEN){
            BookingScreen(navHostController = navHostController)
        }
        composable(Routes.SUCCESS_SCREEN){
            SuccessScreen(navHostController = navHostController)
        }
    }
}
