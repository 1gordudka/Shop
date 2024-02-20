package com.igordudka.repository

import com.igordudka.data.network.api.ShopApi
import com.igordudka.data.network.model.Items

class NetworkRepository(private val shopApi: ShopApi){
    suspend fun getItems(): Items = shopApi.getItems()
}