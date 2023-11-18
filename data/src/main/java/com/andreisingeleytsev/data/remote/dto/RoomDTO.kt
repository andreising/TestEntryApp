package com.andreisingeleytsev.data.remote.dto

import com.andreisingeleytsev.domain_layer.model.RoomModel


data class RoomDTO(
    val id: Int,
    val image_urls: List<String>,
    val name: String,
    val peculiarities: List<String>,
    val price: Int,
    val price_per: String
)

fun RoomDTO.toRoomModel(): com.andreisingeleytsev.domain_layer.model.RoomModel {
    return com.andreisingeleytsev.domain_layer.model.RoomModel(
        id = this.id,
        name = this.name,
        price = this.price,
        pricePer = this.price_per,
        peculiarities = this.peculiarities,
        images = this.image_urls
    )
}