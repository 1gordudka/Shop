package com.igordudka.data.database.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.igordudka.data.database.model.Favourite
import com.igordudka.data.database.model.UserData

@Dao
interface DatabaseDao {

    @Query("SELECT * FROM favourite")
    suspend fun getFavourites(): List<Favourite>

    @Query("DELETE FROM favourite WHERE itemID = :id")
    suspend fun deleteFavouriteById(id: String)

    @Insert
    suspend fun insertFavourite(favourite: Favourite)

    @Delete
    suspend fun deleteFavourite(favourite: Favourite)

    @Insert
    suspend fun insertUser(userData: UserData)

    @Delete
    suspend fun deleteUser(userData: UserData)

    @Query("SELECT * FROM userdata")
    suspend fun getAllUsers() : List<UserData>
}