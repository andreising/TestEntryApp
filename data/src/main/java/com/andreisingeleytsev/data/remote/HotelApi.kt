package com.andreisingeleytsev.data.remote

import com.andreisingeleytsev.data.remote.dto.BookingInfoDTO
import com.andreisingeleytsev.data.remote.dto.HotelDTO
import com.andreisingeleytsev.data.remote.dto.RoomDTO
import com.andreisingeleytsev.data.remote.dto.RoomDTOList
import retrofit2.http.GET

interface HotelApi {
    @GET("/v3/d144777c-a67f-4e35-867a-cacc3b827473")
    suspend fun getHotel(): HotelDTO

    @GET("/v3/8b532701-709e-4194-a41c-1a903af00195")
    suspend fun getRooms(): RoomDTOList

    @GET("/v3/63866c74-d593-432c-af8e-f279d1a8d2ff")
    suspend fun getBookingInfo(): BookingInfoDTO
}