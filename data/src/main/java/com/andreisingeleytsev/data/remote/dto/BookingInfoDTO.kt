package com.andreisingeleytsev.data.remote.dto

import com.andreisingeleytsev.domain_layer.model.BookingInfoModel


data class BookingInfoDTO(
    val arrival_country: String,
    val departure: String,
    val fuel_charge: Int,
    val horating: Int,
    val hotel_adress: String,
    val hotel_name: String,
    val id: Int,
    val number_of_nights: Int,
    val nutrition: String,
    val rating_name: String,
    val room: String,
    val service_charge: Int,
    val tour_date_start: String,
    val tour_date_stop: String,
    val tour_price: Int
)

fun BookingInfoDTO.toBookingInfoModel(): com.andreisingeleytsev.domain_layer.model.BookingInfoModel {
    return com.andreisingeleytsev.domain_layer.model.BookingInfoModel(
        arrivalCountry = this.arrival_country,
        departure = this.departure,
        fuelCharge = this.fuel_charge,
        horating = this.horating,
        hotelAddress = this.hotel_adress,
        hotelName = this.hotel_name,
        id = this.id,
        numberOfNights = this.number_of_nights,
        nutrition = this.nutrition,
        ratingName = this.rating_name,
        room = this.room,
        serviceCharge = this.service_charge,
        tourDateStart = this.tour_date_start,
        tourDateStop = this.tour_date_stop,
        tourPrice = this.tour_price
    )
}