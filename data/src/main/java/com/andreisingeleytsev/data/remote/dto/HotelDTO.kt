package com.andreisingeleytsev.data.remote.dto

import com.andreisingeleytsev.domain_layer.model.HotelModel


data class HotelDTO(
    val about_the_hotel: AboutTheHotel,
    val adress: String,
    val id: Int,
    val image_urls: List<String>,
    val minimal_price: Int,
    val name: String,
    val price_for_it: String,
    val rating: Int,
    val rating_name: String
)

fun HotelDTO.toHotelModel(): com.andreisingeleytsev.domain_layer.model.HotelModel {

    return com.andreisingeleytsev.domain_layer.model.HotelModel(
        id = this.id,
        name = this.name,
        address = this.adress,
        minPrice = this.minimal_price,
        priceForIt = this.price_for_it,
        rating = this.rating,
        ratingName = this.rating_name,
        images = this.image_urls,
        description = this.about_the_hotel.description,
        peculiarities = this.about_the_hotel.peculiarities
    )
}