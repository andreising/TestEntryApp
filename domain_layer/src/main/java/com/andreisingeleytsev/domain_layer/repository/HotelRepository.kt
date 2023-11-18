package com.andreisingeleytsev.domain_layer.repository


import com.andreisingeleytsev.domain_layer.model.BookingInfoModel
import com.andreisingeleytsev.domain_layer.model.HotelModel
import com.andreisingeleytsev.domain_layer.model.RoomModel

interface HotelRepository {

    suspend fun getHotel(): HotelModel

    suspend fun getRooms(): List<RoomModel>

    suspend fun getBookingInfo(): BookingInfoModel
}