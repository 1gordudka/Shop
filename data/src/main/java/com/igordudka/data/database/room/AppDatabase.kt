package com.igordudka.data.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.igordudka.data.database.model.Favourite
import com.igordudka.data.database.model.UserData

@Database(entities = [Favourite::class, UserData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun databaseDao(): DatabaseDao
}