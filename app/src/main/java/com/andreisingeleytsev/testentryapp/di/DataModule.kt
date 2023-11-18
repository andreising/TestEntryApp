package com.andreisingeleytsev.testentryapp.di

import com.andreisingeleytsev.data.instance.retrofit
import com.andreisingeleytsev.data.remote.HotelApi
import com.andreisingeleytsev.data.repository.HotelRepositoryImpl
import com.andreisingeleytsev.domain_layer.repository.HotelRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideHotelApi(): HotelApi {
        return retrofit
    }

    @Provides
    @Singleton
    fun provideHotelRepository(api: HotelApi): com.andreisingeleytsev.domain_layer.repository.HotelRepository {
        return HotelRepositoryImpl(api)
    }
}