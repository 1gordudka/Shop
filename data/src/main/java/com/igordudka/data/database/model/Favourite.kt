package com.igordudka.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("favourite")
data class Favourite(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("itemID")
    val itemID: String
)
