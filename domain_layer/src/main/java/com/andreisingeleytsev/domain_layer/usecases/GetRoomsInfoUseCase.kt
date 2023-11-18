package com.andreisingeleytsev.domain_layer.usecases

import com.andreisingeleytsev.domain_layer.model.RoomModel
import com.andreisingeleytsev.domain_layer.repository.HotelRepository
import com.andreisingeleytsev.domain_layer.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class GetRoomsInfoUseCase(private val repository: HotelRepository) {
    operator fun invoke(): Flow<Resource<List<RoomModel>>> = flow {
        try {
            emit(Resource.Loading<List<RoomModel>>())
            val rooms = repository.getRooms()
            emit(Resource.Success<List<RoomModel>>(rooms))
        } catch (e: IOException) {
            emit(Resource.Error<List<RoomModel>>("Something went wrong"))
        }
    }
}