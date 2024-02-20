package com.igordudka.ui.view.navigation

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.igordudka.data.database.model.Favourite
import com.igordudka.data.database.model.UserData
import com.igordudka.ui.view.components.BottomNavBar
import com.igordudka.ui.view.components.MediumText
import com.igordudka.ui.view.components.Screen
import com.igordudka.ui.view.screens.CatalogScreen
import com.igordudka.ui.view.screens.DetailScreen
import com.igordudka.ui.view.screens.FavouritesScreen
import com.igordudka.ui.view.screens.LoginScreen
import com.igordudka.ui.view.screens.ProfileScreen
import com.igordudka.ui.viewmodel.DatabaseViewModel
import com.igordudka.ui.viewmodel.NetworkViewModel

private fun isInternetAvailable(context: Context): Boolean {
    var result = false
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = connectivityManager.activeNetwork ?: return false
    val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
    result = when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
    return result
}

val LOGIN = -1
val MAIN = 0
val CATALOG = 1
val BIN = 2
val SALES = 3
val PROFILE = 4
val DETAIL = 5
val FAVOURITES = 6

@Composable
fun Navigation(
    networkViewModel: NetworkViewModel = hiltViewModel(),
    databaseViewModel: DatabaseViewModel = hiltViewModel()
) {


    LaunchedEffect(key1 = true){
        databaseViewModel.getUserData()
        databaseViewModel.getFavourites()
    }
    val isDataCollected by databaseViewModel.isDataCollected.collectAsState()
    val context = LocalContext.current
    val isNetwork = isInternetAvailable(context)
    if(isDataCollected){

        val items by networkViewModel.items.collectAsState()
        val favourites by databaseViewModel.favourites.collectAsState()
        val userdata by databaseViewModel.userdata.collectAsState()
        var screen by rememberSaveable {
            mutableStateOf(if (userdata.name == "") LOGIN else CATALOG)
        }
        var currentDetail by rememberSaveable {
            mutableStateOf(-1)
        }
        if (screen == CATALOG){
            LaunchedEffect(key1 = Unit){
                if (isNetwork){
                    networkViewModel.getItems()
                }
            }
        }
        when(screen){
            LOGIN -> {
                LoginScreen(goToNext = {
                    screen = CATALOG
                }, addUser = {
                    databaseViewModel.writeUserData(UserData(name = it.name,
                        surname = it.surname, number = it.number)) })
            }
            else -> {
                Scaffold(bottomBar = {
                    BottomNavBar(screen = screen, onClick = { screen = it })
                }) {padding->
                    if (isNetwork){
                        Column(Modifier.padding(padding)) {
                            when(screen){
                                CATALOG -> {
                                    CatalogScreen(
                                        items = items,
                                        favourites = favourites,
                                        addToFavourite = { databaseViewModel.addFavourite(Favourite(itemID = it)) },
                                        deleteFromFavourite = { databaseViewModel.deleteFavouriteById(it) },
                                        goToDetail = {
                                            currentDetail = it
                                            screen = DETAIL
                                        },
                                        sortByDiscount = {
                                            networkViewModel.sortByDiscount()
                                        },
                                        sortByRating = {
                                            networkViewModel.sortByRating()
                                        }
                                    )
                                }
                                PROFILE -> {
                                    ProfileScreen(
                                        userData = userdata,
                                        logout = { databaseViewModel.deleteAll()
                                            screen = LOGIN},
                                        favAmount = favourites.size
                                    ) {
                                        screen = FAVOURITES
                                    }
                                }
                                FAVOURITES -> FavouritesScreen(
                                    items = items,
                                    deleteFromFavourite = { databaseViewModel.deleteFavouriteById(it) },
                                    goToDetail = { currentDetail = it
                                        screen = DETAIL},
                                    favourites = favourites
                                ) {
                                    screen = PROFILE
                                }
                                DETAIL -> {
                                    items[currentDetail]?.let {item->
                                        DetailScreen(
                                            item = item,
                                            deleteFromFavourite = { databaseViewModel.deleteFavouriteById(it) },
                                            addToFavourite = {
                                                databaseViewModel.addFavourite(
                                                    Favourite(
                                                        itemID = it
                                                    )
                                                )
                                            },
                                            goBack = { screen = CATALOG },
                                            isFavourite = favourites.contains(item.id)
                                        )
                                    }
                                }
                            }
                        }
                    }else{
                        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceAround,
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            MediumText(text = "Нет подключения", size = 22)
                        }
                    }

                }

            }
        }
    }
}
