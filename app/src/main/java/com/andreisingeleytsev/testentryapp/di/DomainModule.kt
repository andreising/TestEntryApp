package com.andreisingeleytsev.testentryapp.di

import com.andreisingeleytsev.domain_layer.repository.HotelRepository
import com.andreisingeleytsev.domain_layer.usecases.GetBookingInfoUseCase
import com.andreisingeleytsev.domain_layer.usecases.GetHotelInfoUseCase
import com.andreisingeleytsev.domain_layer.usecases.GetRoomsInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    @Singleton
    fun provideGetBookingInfoUseCase(repository: HotelRepository): GetBookingInfoUseCase {
        return GetBookingInfoUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideGetHotelInfoUseCase(repository: HotelRepository): GetHotelInfoUseCase {
        return GetHotelInfoUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideGetRoomsInfoUseCase(repository: HotelRepository): GetRoomsInfoUseCase {
        return GetRoomsInfoUseCase(repository)
    }
}