package com.igordudka.data.network.model

data class Item(
    val available: Int?,
    val description: String?,
    val feedback: Feedback?,
    val id: String?,
    val info: List<Info?>?,
    val ingredients: String?,
    val price: Price?,
    val subtitle: String?,
    val tags: List<String?>?,
    val title: String?
)