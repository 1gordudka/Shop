package com.igordudka.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igordudka.data.network.model.Item
import com.igordudka.data.network.model.Items
import com.igordudka.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NetworkViewModel @Inject constructor(
    private val networkRepository: NetworkRepository
): ViewModel() {

    private val _items = MutableStateFlow<List<Item?>>(listOf())
    val items = _items.asStateFlow()

    fun sortByDiscount(){
        _items.value = _items.value.sortedBy { it?.price?.discount }
    }
    fun sortByRating(){
        _items.value = _items.value.sortedBy { it?.feedback?.rating }
    }

    fun getItems(){
        viewModelScope.launch {
            networkRepository.getItems().items?.let {
                _items.value = it
            }
        }
    }
}