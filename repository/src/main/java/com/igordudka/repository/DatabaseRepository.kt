package com.igordudka.repository

import com.igordudka.data.database.model.Favourite
import com.igordudka.data.database.model.UserData
import com.igordudka.data.database.room.DatabaseDao


class DatabaseRepository(private val databaseDao: DatabaseDao) {

    suspend fun getItems() = databaseDao.getFavourites()
    suspend fun addItem(favourite: Favourite) = databaseDao.insertFavourite(favourite)
    suspend fun deleteItem(favourite: Favourite) = databaseDao.deleteFavourite(favourite)
    suspend fun addUser(userData: UserData) = databaseDao.insertUser(userData)
    suspend fun getUsers() = databaseDao.getAllUsers()
    suspend fun deleteUser(userData: UserData) = databaseDao.deleteUser(userData)
    suspend fun deleteFavouriteById(id: String) = databaseDao.deleteFavouriteById(id)
}