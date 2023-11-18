package com.andreisingeleytsev.data.instance

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofit = Retrofit.Builder()
    .baseUrl(com.andreisingeleytsev.data.common.Constants.BASE_URL)

    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(com.andreisingeleytsev.data.remote.HotelApi::class.java)