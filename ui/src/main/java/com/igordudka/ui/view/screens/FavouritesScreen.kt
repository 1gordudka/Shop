package com.igordudka.ui.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.igordudka.data.database.model.Favourite
import com.igordudka.data.network.model.Item
import com.igordudka.ui.view.components.MediumText
import com.igordudka.ui.view.components.Screen
import com.igordudka.ui.view.components.clickableWithoutRipple
import com.igordudka.ui.view.components.lightGray
import com.igordudka.ui.view.components.textGray

@Composable
fun FavouritesScreen(
    items: List<Item?>,
    deleteFromFavourite: (String) -> Unit,
    goToDetail: (Int) -> Unit,
    favourites: List<String>,
    goBack: () -> Unit
    ) {
    
    val favouritesList = items.filter { it?.id in favourites }
    var isProducts by remember {
        mutableStateOf(true)
    }
    
    Column(
        Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Color.White), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = goBack) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = null,
                    Modifier
                        .padding(6.dp)
                        .size(24.dp)
                )
            }
            MediumText(text = "Избранное", size = 18)
        }

        Selector(isProducts = isProducts, changeStatus = {isProducts = it})
        if (isProducts){
            ItemRow(
                items = favouritesList,
                favourites = favourites,
                addToFavourite = {},
                deleteFromFavourite = { deleteFromFavourite(it) },
                goToDetail = { goToDetail(it) },
                tag = 0
            )
        }
    }
    
}

@Composable
fun Selector(
    isProducts: Boolean,
    changeStatus: (Boolean) -> Unit
) {

    Row(
        Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(lightGray)
            .padding(3.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Row(
            Modifier
                .weight(1f)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White.copy(if (isProducts) 1f else 0f))
                .clickableWithoutRipple(MutableInteractionSource()) {
                    changeStatus(true)
                }
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            MediumText(text = "Товары", size = 16, color = if (isProducts) Color.Black else textGray)
        }
        Row(
            Modifier
                .weight(1f)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White.copy(if (isProducts) 0f else 1f))
                .clickableWithoutRipple(MutableInteractionSource()) {
                    changeStatus(false)
                }
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            MediumText(text = "Бренды", size = 16, color = if (isProducts) textGray else Color.Black)
        }
    }
}