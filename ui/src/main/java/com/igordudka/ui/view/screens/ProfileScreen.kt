package com.igordudka.ui.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.igordudka.data.database.model.Favourite
import com.igordudka.data.database.model.UserData
import com.igordudka.ui.R
import com.igordudka.ui.view.components.MediumText
import com.igordudka.ui.view.components.RegularText
import com.igordudka.ui.view.components.Screen
import com.igordudka.ui.view.components.lightGray
import com.igordudka.ui.view.components.lightPink
import com.igordudka.ui.view.components.pink

val categories = listOf(Pair(R.drawable.category2, "Избранное"), Pair(R.drawable.category3, "Магазины"), Pair(R.drawable.category4, "Обратная связь")
,Pair(R.drawable.category5, "Оферта "), Pair(R.drawable.category6, "Возврат товара"))

@Composable
fun ProfileScreen(
    userData: UserData,
    logout: () -> Unit,
    favAmount: Int,
    goToFavourite: () -> Unit
) {
    Screen(name = "Личный кабинет") {
        Spacer(modifier = Modifier.height(20.dp))
        ProfileCard(name = userData.name, surname = userData.surname, number = userData.number) {
            logout()
        }
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn{
            items(categories){
                CategoryCard(name = it.second, desc =
                    if (it.first == R.drawable.category2){
                              if (favAmount > 0){
                                  "$favAmount товар(а/ов)"
                              }else{
                                  "Пока нет товаров"
                              }
                    }else{
                         ""
                    }
                    , pic = it.first) {
                    if (it.first == R.drawable.category2){
                        goToFavourite()
                    }
                }
            }
            item{
                Column {
                    Spacer(modifier = Modifier.weight(1f))
                    GrayBtn(text = "Выйти") {
                        logout()
                    }
                }
            }
        }

    }
}

@Composable
fun GrayBtn(
    text: String,
    onClick: () -> Unit
) {

    Button(onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = lightGray
        ), modifier = Modifier
            .padding(vertical = 12.dp)
            .height(50.dp)
            .fillMaxWidth(), shape = RoundedCornerShape(8.dp)
    ) {
        MediumText(text = text, size = 14, color = Color.Black)
    }
}

@Composable
fun ProfileCard(
    name: String,
    surname: String,
    number: String,
    logout: () -> Unit
) {

    Row(
        Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(lightGray)
            .padding(6.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(painter = painterResource(id = R.drawable.category1), contentDescription = null,
            Modifier
                .padding(8.dp)
                .size(24.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            MediumText(text = "$name $surname", size = 16)
            Spacer(modifier = Modifier.height(3.dp))
            RegularText(text = "+7 ${number.slice(0..2)} ${number.slice(3..5)} ${number.slice(6..7)}" +
                    " ${number.slice(8..9)}", size = 12)
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = logout) {
            Image(painter = painterResource(id = R.drawable.logout), contentDescription = null,
                Modifier.size(32.dp))
        }
    }
}

@Composable
fun CategoryCard(
    name: String,
    desc: String,
    pic: Int,
    onClick: () -> Unit
) {

    Row(
        Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(lightGray)
            .padding(6.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(painter = painterResource(id = pic), contentDescription = null,
            Modifier
                .padding(8.dp)
                .size(24.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            MediumText(text = name, size = 16)
            if (desc != "") {
                Spacer(modifier = Modifier.height(3.dp))
                RegularText(text = desc, size = 12)
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = onClick) {
            IconButton(onClick = onClick) {
                Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
            }
        }
    }
}