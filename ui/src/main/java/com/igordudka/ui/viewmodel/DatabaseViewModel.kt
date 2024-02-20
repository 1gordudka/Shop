package com.igordudka.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igordudka.data.database.model.Favourite
import com.igordudka.data.database.model.UserData
import com.igordudka.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatabaseViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
): ViewModel() {

    private val _userdata = MutableStateFlow<UserData>(UserData(name = "", surname = "", number = ""))
    val userdata = _userdata.asStateFlow()
    private val _isDataCollected = MutableStateFlow(false)
    val isDataCollected = _isDataCollected.asStateFlow()

    private val _favourites = MutableStateFlow<List<String>>(listOf())
    val favourites = _favourites.asStateFlow()



    fun getUserData(){
        viewModelScope.launch {
            databaseRepository.getUsers().let {
                if (it.isNotEmpty()){
                    _userdata.value = it[0]
                }
                _isDataCollected.value = true
            }
        }
    }
    fun writeUserData(userData: UserData){
        viewModelScope.launch {
            databaseRepository.addUser(userData)
        }
    }
    fun deleteUserData(userData: UserData){
        viewModelScope.launch {
            databaseRepository.deleteUser(userData)
        }
    }

    fun getFavourites(){
        viewModelScope.launch {
            databaseRepository.getItems()?.let {
                val l = mutableListOf<String>()
                for (i in 0 until it.size){
                    l.add(it[i].itemID)
                }
                l.reverse()
                _favourites.value = l
            }
        }
    }
    fun addFavourite(favourite: Favourite){
        viewModelScope.launch {
            databaseRepository.addItem(favourite)
            getFavourites()
        }
    }
    fun deleteFavourite(favourite: Favourite){
        viewModelScope.launch {
            databaseRepository.deleteItem(favourite)
        }
    }

    fun deleteFavouriteById(id: String){
        viewModelScope.launch {
            databaseRepository.deleteFavouriteById(id)
            getFavourites()
        }
    }

    fun deleteAll(){
        viewModelScope.launch {
            databaseRepository.deleteUser(userdata.value)
            for (i in 0 until favourites.value.size){
                databaseRepository.deleteFavouriteById(favourites.value[i])
            }
            getUserData()
            getFavourites()
        }
    }

}