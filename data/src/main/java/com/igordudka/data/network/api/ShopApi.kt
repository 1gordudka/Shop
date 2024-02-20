package com.igordudka.data.network.api

import com.igordudka.data.network.model.Items
import retrofit2.http.GET

interface ShopApi {

    @GET("97e721a7-0a66-4cae-b445-83cc0bcf9010")
    suspend fun getItems() : Items
}