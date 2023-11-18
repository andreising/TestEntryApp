package com.andreisingeleytsev.domain_layer.model

data class RoomModel(
    val id: Int,
    val name: String,
    val price: Int,
    val pricePer: String,
    val peculiarities: List<String>,
    val images: List<String>
)
