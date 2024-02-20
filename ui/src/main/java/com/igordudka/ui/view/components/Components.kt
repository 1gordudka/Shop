package com.igordudka.ui.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.igordudka.ui.R


val lightPink = Color(0xFFFF8AC9)
val pink = Color(0xFFD62F89)
val darkGray = Color(0xFF52606D)
val lightGray = Color(0xFFF8F8F8)
val textGray = Color(0xFFA0A1A3)


val bottomIcons = listOf(R.drawable.bottom1, R.drawable.bottom2, R.drawable.bottom3, R.drawable.bottom4, R.drawable.bottom5)
val bottomNames = listOf("Главная", "Каталог", "Корзина", "Акции", "Профиль")

fun Modifier.clickableWithoutRipple(
    interactionSource: MutableInteractionSource,
    onClick: () -> Unit
) = composed(
    factory = {
        this.then(
            Modifier.clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { onClick() }
            )
        )
    }
)

@Composable
fun BottomNavBar(
    screen: Int,
    onClick: (Int) -> Unit
) {

    Column(verticalArrangement = Arrangement.Top, modifier = Modifier
        .navigationBarsPadding()
        .padding(horizontal = 8.dp)
        .background(Color.White)) {
        Divider(
            Modifier
                .height(1.dp)
                .fillMaxWidth(), color = textGray.copy(alpha = 0.5f))
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween) {
            repeat(bottomIcons.size){
                Column(
                    Modifier
                        .padding(8.dp)
                        .clickableWithoutRipple(MutableInteractionSource()) {
                            onClick(it)
                        }, horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painter = painterResource(id = bottomIcons[it]), contentDescription = null,
                        Modifier
                            .padding(6.dp)
                            .size(24.dp)
                    )
                    RegularText(text = bottomNames[it], size = 10,
                        color = if (screen == it) pink else Color.Black)
                }
            }
        }
    }
}


@Composable
fun MediumText(
    text: String,
    size: Int,
    color: Color = Color.Black,
    padding: Int = 0
) {

    Text(text = text, fontSize = size.sp, fontWeight = FontWeight.Medium, color = color,
        modifier = Modifier.padding(padding.dp))
}

@Composable
fun RegularText(
    text: String,
    size: Int,
    color: Color = Color.Black,
    padding: Int = 0
) {

    Text(text = text, fontSize = size.sp, fontWeight = FontWeight.Normal, color = color,
        modifier = Modifier.padding(padding.dp))
}




@Composable
fun Screen(
    name: String,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    content: @Composable () -> Unit,
) {

    Column(
        Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(horizontal = 12.dp)
            .statusBarsPadding(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = verticalArrangement) {
        MediumText(text = name, size = 18)
        content()
    }
}