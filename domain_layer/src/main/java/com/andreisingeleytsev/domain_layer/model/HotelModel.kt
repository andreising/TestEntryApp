package com.andreisingeleytsev.domain_layer.model

import android.media.Rating

data class HotelModel(
    val id: Int,
    val name: String,
    val address: String,
    val minPrice: Int,
    val priceForIt: String,
    val rating: Int,
    val ratingName: String,
    val images: List<String>,
    val description: String,
    val peculiarities: List<String>
)