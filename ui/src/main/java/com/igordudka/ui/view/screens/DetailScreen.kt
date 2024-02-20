package com.igordudka.ui.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.igordudka.data.network.model.Item
import com.igordudka.ui.R
import com.igordudka.ui.view.components.MediumText
import com.igordudka.ui.view.components.RegularText
import com.igordudka.ui.view.components.clickableWithoutRipple
import com.igordudka.ui.view.components.darkGray
import com.igordudka.ui.view.components.lightGray
import com.igordudka.ui.view.components.pink
import com.igordudka.ui.view.components.textGray

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DetailScreen(
    item: Item,
    deleteFromFavourite: (String) -> Unit,
    addToFavourite: (String) -> Unit,
    goBack: () -> Unit,
    isFavourite: Boolean
) {


    val state = rememberPagerState()
    val images = imgToID[item.id]?.let { listOf(it.first, it.second) }
    var isDescription by remember {
        mutableStateOf(false)
    }
    var isSostav by remember {
        mutableStateOf(false)
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = goBack) {
                Icon(imageVector = Icons.Default.KeyboardArrowLeft
                    , contentDescription = null, Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { /*TODO*/ }) {
                Image(painter = painterResource(id = R.drawable.share), contentDescription = null,
                    Modifier.size(24.dp))
            }
        }
        LazyColumn(horizontalAlignment = Alignment.Start){
            item{
                if (images != null) {
                    Box(contentAlignment = Alignment.Center){

                        HorizontalPager(count = images.size,
                            state = state) {
                            Image(painter = painterResource(id = images[state.currentPage]), contentDescription = null,
                                Modifier.size(340.dp, 368.dp), contentScale = ContentScale.FillBounds)
                        }
                        IconButton(onClick = {
                            if (isFavourite){
                                item.id?.let { deleteFromFavourite(it) }
                            }else{
                                item.id?.let { addToFavourite(it) }
                            }
                        }, modifier = Modifier.align(Alignment.TopEnd)) {
                            Image(painter = painterResource(id = if (isFavourite) R.drawable.filled_heart else R.drawable.outlined_heart), contentDescription = null,
                                Modifier
                                    .padding(6.dp)
                                    .size(24.dp))
                        }

                    }
                    DotsIndicator(
                        totalDots = images.size,
                        selectedIndex = state.currentPage,
                        modifier = Modifier,
                        size = 10
                    )
                }
                item.title?.let { MediumText(text = it, size = 18, color = textGray, padding = 6) }
                item.subtitle?.let { MediumText(text = it, size = 22, padding = 6) }
                RegularText(text = "Доступно для заказа ${item.available} шт.", size = 14, color = textGray, padding = 6)
                Row {
                    item.feedback?.rating?.toInt()?.let { repeat(it){
                        Image(painter = painterResource(id = R.drawable.star), contentDescription = null,
                            Modifier
                                .padding(2.dp)
                                .size(20.dp))
                    } }
                    Spacer(modifier = Modifier.width(5.dp))
                    RegularText(
                        text = "${item.feedback?.rating} - ${item.feedback?.count} отз.",
                        size = 14,
                        color = textGray, padding = 6
                    )
                }
                Row (verticalAlignment = Alignment.CenterVertically){
                    MediumText(text = "${item.price?.priceWithDiscount} ${item.price?.unit}", size = 20, padding = 6)
                    Text(text = "${item.price?.price} ${item.price?.unit}", fontSize = 16.sp, color = textGray,
                        textDecoration = TextDecoration.LineThrough, modifier = Modifier.padding(6.dp))
                    Row(
                        Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(pink)
                            .padding(4.dp)) {
                        RegularText(text = "-${item.price?.discount}%", size = 12, color = Color.White)
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                MediumText(text = "Описание", size = 18, padding = 6)
                if (isDescription){
                    Row(
                        Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(lightGray)
                            .padding(6.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            MediumText(text = "${item.title}", size = 16, padding = 6)
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = {  }) {
                            IconButton(onClick = {  }) {
                                Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
                            }
                        }
                    }
                    RegularText(text = "${item.description}", size = 14, color = Color.Black.copy(alpha = 0.8f), padding = 6)
                }
                Row(modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .clickableWithoutRipple(MutableInteractionSource()) {
                        isDescription = !isDescription
                    }) {
                    MediumText(text = if (isDescription) "Скрыть" else "Подробнее", size = 14,
                        color = textGray, padding = 6)
                }
                Spacer(modifier = Modifier.height(10.dp))
                MediumText(text = "Характеристики", size = 18, padding = 6)
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(10.dp)) {
                    RegularText(text = "${item.info?.get(0)?.title}", size = 14)
                    Spacer(modifier = Modifier.weight(1f))
                    RegularText(text = "${item.info?.get(0)?.value}", size = 14)
                }
                Divider(
                    Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp))
                MediumText(text = "Состав", size = 18, padding = 6)
                if (isSostav){
                    Row (Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                        RegularText(text = "${item.ingredients}", size = 14, color = Color.Black.copy(alpha = 0.8f), padding = 6)
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = { /*TODO*/ }) {
                            Image(painter = painterResource(id = R.drawable.copy), contentDescription = null,
                                Modifier.size(24.dp))
                        }
                    }
                }
                Row(modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .clickableWithoutRipple(MutableInteractionSource()) {
                        isSostav = !isSostav
                    }) {
                    MediumText(text = if (isSostav) "Скрыть" else "Подробнее", size = 14,
                        color = textGray, padding = 6)
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = pink
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        MediumText(text = "${item.price?.priceWithDiscount} ${item.price?.unit}", size = 16, color = Color.White,
                            padding = 8)
                        Text(text = "${item.price?.price} ${item.price?.unit}", fontSize = 12.sp, color = textGray,
                            textDecoration = TextDecoration.LineThrough)
                        Spacer(modifier = Modifier.weight(1f))
                        MediumText(text = "Добавить в корзину", size = 14, color = Color.White,
                            padding = 6)
                    }
                }
            }
        }
    }
}