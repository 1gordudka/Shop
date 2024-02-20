package com.igordudka.ui.view.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.window.Dialog
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.igordudka.data.database.model.Favourite
import com.igordudka.data.network.model.Item
import com.igordudka.ui.R
import com.igordudka.ui.view.components.MediumText
import com.igordudka.ui.view.components.RegularText
import com.igordudka.ui.view.components.Screen
import com.igordudka.ui.view.components.clickableWithoutRipple
import com.igordudka.ui.view.components.darkGray
import com.igordudka.ui.view.components.lightGray
import com.igordudka.ui.view.components.pink
import com.igordudka.ui.view.components.textGray

val tagsNames = listOf("Смотреть все", "Лицо", "Тело", "Загар", "Маски")
val tags = listOf("face", "body", "suntan", "mask")
val imgToID = mapOf<String, Pair<Int, Int>>(
    "cbf0c984-7c6c-4ada-82da-e29dc698bb50" to Pair(R.drawable.item6, R.drawable.item5),
    "54a876a5-2205-48ba-9498-cfecff4baa6e" to Pair(R.drawable.item1, R.drawable.item2),
    "75c84407-52e1-4cce-a73a-ff2d3ac031b3" to Pair(R.drawable.item5, R.drawable.item6),
    "16f88865-ae74-4b7c-9d85-b68334bb97db" to Pair(R.drawable.item3, R.drawable.item4),
    "26f88856-ae74-4b7c-9d85-b68334bb97db" to Pair(R.drawable.item2, R.drawable.item3),
    "15f88865-ae74-4b7c-9d81-b78334bb97db" to Pair(R.drawable.item6, R.drawable.item1),
    "88f88865-ae74-4b7c-9d81-b78334bb97db" to Pair(R.drawable.item4, R.drawable.item3),
    "55f58865-ae74-4b7c-9d81-b78334bb97db" to Pair(R.drawable.item1, R.drawable.item5),
)

@Composable
fun CatalogScreen(
    items: List<Item?>,
    favourites: List<String>,
    addToFavourite: (String) -> Unit,
    deleteFromFavourite: (String) -> Unit,
    goToDetail: (Int) -> Unit,
    sortByDiscount: () -> Unit,
    sortByRating: () -> Unit
) {
    var chosenTag by remember {
        mutableStateOf(0)
    }

    Screen(name = "Каталог") {
        TopBar(sortByDiscount = {
            sortByDiscount()
        }) {
            sortByRating()
        }
        TagsRow(chosen = chosenTag, choose = {
            chosenTag = it

        })
        ItemRow(items = items, favourites = favourites,
            addToFavourite = {addToFavourite(it)}, deleteFromFavourite = {deleteFromFavourite(it)},
            goToDetail = {goToDetail(it)}, tag = chosenTag)
    }
}

@Composable
fun ItemRow(
    items: List<Item?>,
    favourites: List<String>,
    addToFavourite: (String) -> Unit,
    deleteFromFavourite: (String) -> Unit,
    goToDetail: (Int) -> Unit,
    tag: Int
) {


    val items1 = items.toMutableList()
    if (tag != 0){
        for (i in items.indices){
            if (items[i]?.tags?.contains(tags[tag-1]) == false){
                items1.remove(items[i])
            }
        }
    }
    Log.d("FKKFKFKFF", favourites.toString())

    LazyVerticalGrid(columns = GridCells.Fixed(2)){
        items(items1){
            if (it != null) {
                it.price?.price?.let { it1 ->
                    it.price!!.priceWithDiscount?.let { it2 ->
                        it.price!!.unit?.let { it3 ->
                            it.title?.let { it4 ->
                                it.subtitle?.let { it5 ->
                                    it.price!!.discount?.let { it6 ->
                                        imgToID[it.id]?.let { it7 ->
                                            imgToID[it.id]?.let { it8 ->
                                                ItemTile(
                                                    isFavourite = favourites.contains(it.id),
                                                    pic1 = it7.first,
                                                    pic2 = it8.second,
                                                    oldPrice = it1,
                                                    newPrice = it2,
                                                    symbol = it3,
                                                    discount = it6,
                                                    title = it4,
                                                    subtitle = it5,
                                                    rating = it.feedback?.rating.toString(),
                                                    feedback = it.feedback?.count.toString(),
                                                    addToFavourite = { it.id?.let { it9 ->
                                                        addToFavourite(
                                                            it9
                                                        )
                                                    } },
                                                    deleteFromFavourite = { it.id?.let { it9 ->
                                                        deleteFromFavourite(
                                                            it9
                                                        )
                                                    } }) {
                                                    goToDetail(items.indexOf(it))
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ItemTile(
    isFavourite: Boolean,
    pic1: Int,
    pic2: Int,
    oldPrice: String,
    newPrice: String,
    symbol: String,
    discount: Int,
    title: String,
    subtitle: String,
    rating: String,
    feedback: String,
    addToFavourite: () -> Unit,
    deleteFromFavourite: () -> Unit,
    onClick: () -> Unit
) {

    val images = listOf(pic1, pic2)
    val state = rememberPagerState()

    Column(
        Modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, lightGray, RoundedCornerShape(16.dp))
            .sizeIn(minWidth = 168.dp, minHeight = 287.dp, maxWidth = 168.dp, maxHeight = 350.dp)
            .background(Color.White)
            .clickableWithoutRipple(MutableInteractionSource()){
                onClick()
            }

    ) {
        Box(modifier = Modifier.size(168.dp, 144.dp)){
            HorizontalPager(count = images.size,
                state = state, modifier = Modifier.align(Alignment.Center)) {
                Image(painter = painterResource(id = images[state.currentPage]), contentDescription = null,
                    Modifier.size(168.dp, 144.dp), contentScale = ContentScale.FillBounds)
            }
            DotsIndicator(totalDots = 2, selectedIndex = state.currentPage, modifier = Modifier.align(
                Alignment.BottomCenter))
            IconButton(onClick = {
                if (isFavourite){
                    deleteFromFavourite()
                }else{
                    addToFavourite()
                }
            }, modifier = Modifier.align(Alignment.TopEnd)) {
                Image(painter = painterResource(id = if (isFavourite) R.drawable.filled_heart else R.drawable.outlined_heart), contentDescription = null,
                    Modifier
                        .padding(6.dp)
                        .size(24.dp))
            }
        }
        Column(
            Modifier
                .padding(8.dp)
                .clickableWithoutRipple(MutableInteractionSource()) {
                    onClick()
                }) {
            Text(text = "$oldPrice $symbol", fontSize = 12.sp, color = textGray, textDecoration = TextDecoration.LineThrough)
            Row(verticalAlignment = Alignment.CenterVertically) {
                MediumText(text = "$newPrice $symbol", size = 16)
                Spacer(modifier = Modifier.width(7.dp))
                Row(
                    Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(pink)
                        .padding(4.dp)) {
                    RegularText(text = "-$discount%", size = 12, color = Color.White)
                }
            }
            MediumText(text = title, size = 14)
            RegularText(text = subtitle, size = 12, color = textGray)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = R.drawable.star), contentDescription = null,
                    Modifier.size(16.dp))
                RegularText(text = "$rating", size = 12, color = Color(0xFFF9A249))
                Spacer(modifier = Modifier.width(5.dp))
                RegularText(text = "($feedback)", size = 12, color = textGray)
            }

        }
        Spacer(modifier = Modifier.weight(1f))
        Row {
            Spacer(modifier = Modifier.weight(1f))
            Row(
                Modifier
                    .clip(RoundedCornerShape(10.dp, 0.dp, 0.dp, 10.dp))
                    .size(50.dp)
                    .background(pink),
                horizontalArrangement = Arrangement.Absolute.Center, verticalAlignment = Alignment.CenterVertically) {
                RegularText(text = "+", size = 30, color = Color.White)
            }
        }
    }
}

@Composable
fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int,
    modifier: Modifier,
    size: Int = 6
) {

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(), horizontalArrangement = Arrangement.Center
    ) {

        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(size.dp)
                        .clip(CircleShape)
                        .background(color = pink)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(size.dp)
                        .clip(CircleShape)
                        .background(color = Color.LightGray)
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}

@Composable
fun TagsRow(
    chosen: Int,
    choose: (Int) -> Unit
) {

    LazyRow(Modifier.padding(vertical = 8.dp)){
        items(tagsNames){
            TagItem(
                name = it,
                isClicked = chosen == tagsNames.indexOf(it),
                onClick = { choose(tagsNames.indexOf(it)) }) {
                choose(0)
            }
        }
    }
}

@Composable
fun TagItem(
    name: String,
    isClicked: Boolean,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {

    Row(
        Modifier
            .padding(horizontal = 4.dp)
            .clip(RoundedCornerShape(100.dp))
            .background(if (isClicked) darkGray else lightGray)
            .clickableWithoutRipple(MutableInteractionSource()) {
                onClick()
            }
            .padding(vertical = 7.dp, horizontal = 12.dp),
        horizontalArrangement = Arrangement.Absolute.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
        RegularText(text = name, size = 14, color = if (isClicked) Color.White else darkGray)
        if (isClicked){
            Spacer(modifier = Modifier.width(5.dp))
            IconButton(onClick = { onDelete()}, Modifier.size(20.dp)) {
                Icon(
                    imageVector = Icons.Outlined.Clear,
                    contentDescription = null,
                    tint = Color.White,
                )
            }
        }
    }
}

@Composable
fun TopBar(
    sortByDiscount: () -> Unit,
    sortByRating: () -> Unit
) {
    Row(
        Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        SortBtn(sortByRating = sortByRating) {
            sortByDiscount()
        }
        Spacer(modifier = Modifier.weight(1f))
        FiltersBtn()
    }
}

@Composable
fun SortBtn(
    sortByRating: () -> Unit,
    sortByDiscount: () -> Unit,
) {

    var isExpanded by remember {
        mutableStateOf(false)
    }
    var current by remember {
        mutableStateOf(2)
    }
    val sortings = listOf("По рейтингу", "По скидке", "По популярности")
    
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(painter = painterResource(id = R.drawable.popul), contentDescription = null,
            Modifier
                .padding(4.dp)
                .size(24.dp))
        RegularText(text = sortings[current], size = 14)
        IconButton(onClick = { isExpanded = !isExpanded }) {
            Icon(imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown, contentDescription = null)
        }
    }
    if (isExpanded){
        Dialog(onDismissRequest = { isExpanded = false }) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)) {
                repeat(sortings.size){
                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(12.dp)) {
                        RadioButton(selected = it == current, onClick = {
                            current = it
                            if (it == 1) sortByDiscount()
                            if (it == 0) sortByRating()
                            isExpanded = false
                        })
                        Spacer(modifier = Modifier.width(15.dp))
                        MediumText(text = sortings[it], size = 16)
                    }
                }
            }
        }
    }
}

@Composable
fun FiltersBtn() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(painter = painterResource(id = R.drawable.filters), contentDescription = null,
            Modifier
                .padding(4.dp)
                .size(24.dp))
        RegularText(text = "Фильтры", size = 14)
    }
}