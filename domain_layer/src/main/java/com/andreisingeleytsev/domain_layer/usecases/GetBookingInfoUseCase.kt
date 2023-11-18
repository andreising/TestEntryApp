package com.andreisingeleytsev.domain_layer.usecases

import com.andreisingeleytsev.domain_layer.model.BookingInfoModel
import com.andreisingeleytsev.domain_layer.repository.HotelRepository
import com.andreisingeleytsev.domain_layer.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class GetBookingInfoUseCase(private val repository: HotelRepository) {
    operator fun invoke(): Flow<Resource<BookingInfoModel>> = flow {
        try {
            emit(Resource.Loading<BookingInfoModel>())
            val bookingInfoModel = repository.getBookingInfo()
            emit(Resource.Success<BookingInfoModel>(bookingInfoModel))
        }  catch (e: IOException) {
            emit(Resource.Error<BookingInfoModel>("Something went wrong"))
        }
    }
}