package com.igordudka.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("userdata")
data class UserData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("surname")
    val surname: String,
    @ColumnInfo("number")
    val number: String
)
