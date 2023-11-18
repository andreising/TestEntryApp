package com.andreisingeleytsev.data.repository

import android.util.Log
import com.andreisingeleytsev.data.remote.HotelApi
import com.andreisingeleytsev.data.remote.dto.RoomDTO
import com.andreisingeleytsev.data.remote.dto.toBookingInfoModel
import com.andreisingeleytsev.data.remote.dto.toHotelModel
import com.andreisingeleytsev.data.remote.dto.toRoomModel
import com.andreisingeleytsev.domain_layer.model.BookingInfoModel
import com.andreisingeleytsev.domain_layer.model.HotelModel
import com.andreisingeleytsev.domain_layer.model.RoomModel
import com.andreisingeleytsev.domain_layer.repository.HotelRepository


class HotelRepositoryImpl(private val api: HotelApi) :
    com.andreisingeleytsev.domain_layer.repository.HotelRepository {
    override suspend fun getHotel(): com.andreisingeleytsev.domain_layer.model.HotelModel {
        return api.getHotel().toHotelModel()
    }

    override suspend fun getRooms(): List<com.andreisingeleytsev.domain_layer.model.RoomModel> {
        val list = mutableListOf<com.andreisingeleytsev.domain_layer.model.RoomModel>()
        api.getRooms().rooms.forEach { roomDTO -> list.add(roomDTO.toRoomModel()) }
        return list
    }

    override suspend fun getBookingInfo(): com.andreisingeleytsev.domain_layer.model.BookingInfoModel {
        return api.getBookingInfo().toBookingInfoModel()
    }
}