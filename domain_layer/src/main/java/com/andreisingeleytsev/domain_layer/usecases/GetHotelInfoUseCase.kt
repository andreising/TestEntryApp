package com.andreisingeleytsev.domain_layer.usecases

import com.andreisingeleytsev.domain_layer.model.HotelModel
import com.andreisingeleytsev.domain_layer.repository.HotelRepository
import com.andreisingeleytsev.domain_layer.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class GetHotelInfoUseCase(
    private val repository: HotelRepository
) {
    operator fun invoke(): Flow<Resource<HotelModel>> = flow {
        try {
            emit(Resource.Loading<HotelModel>())
            val hotel = repository.getHotel()
            emit(Resource.Success<HotelModel>(hotel))
        }  catch (e: IOException) {
            emit(Resource.Error<HotelModel>("Something went wrong"))
        }
    }
}