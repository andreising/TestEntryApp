package com.andreisingeleytsev.testentryapp.presentation.ui.screens.hotel


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun CarouselUIItem(imageId: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp), shape = RoundedCornerShape(16.dp)
    ) {
        AsyncImage(
            model = imageId,
            contentDescription = "img_hotel",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
