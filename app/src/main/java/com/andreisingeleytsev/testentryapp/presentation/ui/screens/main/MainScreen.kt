package com.andreisingeleytsev.testentryapp.presentation.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.andreisingeleytsev.testentryapp.presentation.navigation.MainNavigationGraph
import com.andreisingeleytsev.testentryapp.presentation.ui.theme.SecondaryGrayColor
import com.andreisingeleytsev.testentryapp.presentation.utils.Fonts
import com.andreisingeleytsev.testentryapp.presentation.utils.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        topBar = {
            MyTopBar(
                title = currentRoute,
                isStartScreen = currentRoute == Routes.HOTEL_SCREEN,
                navHostController = navController
            )
        }
    ) {
        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize()){
            MainNavigationGraph(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(title: String?, isStartScreen: Boolean, navHostController: NavHostController) {
    CenterAlignedTopAppBar(title = {
        title?.let {
            Text(
                text = it, style = TextStyle(
                    fontFamily = Fonts.font,
                    color = Color.Black,
                    fontSize = 18.sp
                )
            )
        }
    }, navigationIcon = {
        if (!isStartScreen) {
            IconButton(onClick = {
                navHostController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        }
    })
}
