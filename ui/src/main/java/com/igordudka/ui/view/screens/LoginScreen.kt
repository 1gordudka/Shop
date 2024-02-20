package com.igordudka.ui.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.igordudka.data.database.model.UserData
import com.igordudka.ui.R
import com.igordudka.ui.view.components.MediumText
import com.igordudka.ui.view.components.RegularText
import com.igordudka.ui.view.components.Screen
import com.igordudka.ui.view.components.darkGray
import com.igordudka.ui.view.components.lightGray
import com.igordudka.ui.view.components.lightPink
import com.igordudka.ui.view.components.pink
import com.igordudka.ui.view.components.textGray
import kotlin.math.absoluteValue

fun isValidate(text: String) : Boolean{
    val ruAlphabet = listOf(
        "а", "б", "в", "г", "д", "е", "ё", "ж", "з", "и", "й", "к", "л", "м",
        "н", "о", "п", "р", "с", "т", "у", "ф", "х", "ц", "ч", "ш", "щ", "ъ",
        "ы", "ь", "э", "ю", "я"
    )
    var isValidate = true
    for (i in text.indices){
        if (text[i].lowercase() !in ruAlphabet){
            isValidate = false
        }
    }
    return isValidate
}

fun isValidateNumber(number: String) : Boolean{
    val nums = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
    for (i in number.indices){
        if (number[i].toString() !in nums){
            return false
        }
    }
    return true
}

@Composable
fun LoginScreen(
    goToNext: () -> Unit,
    addUser: (UserData) -> Unit
) {

    var name by remember {
        mutableStateOf("")
    }
    var surname by remember {
        mutableStateOf("")
    }
    var number by remember {
        mutableStateOf("")
    }
    var isValidateName by remember {
        mutableStateOf(true)
    }
    var isValidateSurname by remember {
        mutableStateOf(true)
    }
    var isValidateNumber by remember {
        mutableStateOf(true)
    }

    Screen(name = "Вход", verticalArrangement = Arrangement.SpaceBetween) {
        Column {
            StringTextField(
                name = "Имя",
                text = name,
                onValueChange = {
                                name = it
                    isValidateName = isValidate(name)
                },
                isValidate = isValidateName
            ) {
                name = ""
            }
            StringTextField(name = "Фамилия", text = surname, onValueChange = {
                      surname = it
                isValidateSurname = isValidate(surname)
            }, isValidate = isValidateSurname) {
                surname = ""
            }
            NumberTextField(
                name = "Номер телефона",
                text = number,
                onValueChange = {
                                if (number.length >= 10){

                                }else{
                                    if (it == "7" && number.isEmpty()){
                                        number += "9"
                                    }else{
                                        number = it
                                        isValidateNumber = isValidateNumber(number)
                                    }
                                }
                },
                isValidate = isValidateNumber
            ) {
                number = ""
            }
            PinkBtn(
                text = "Войти",
                isLight = !(isValidateName && isValidateSurname && isValidateNumber && name != "" && surname != "" && number.length >= 10)
            ) {
                if(isValidateName && isValidateSurname && isValidateNumber && name != "" && surname != "" && number.length >= 10){
                    addUser(
                        UserData(name = name, number = number, surname = surname)
                    )
                    goToNext()
                }
            }
        }
        PolicyText()
    }
}

@Composable
fun PolicyText() {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.navigationBarsPadding()) {
        Text(text = "Нажимая кнопку “Войти”, Вы принимаете", color = textGray, fontSize = 10.sp,)
        Text(text = "условия программы лояльности", color = textGray, fontSize = 10.sp,
            textDecoration = TextDecoration.Underline)
    }
}

@Composable
fun PinkBtn(
    text: String,
    isLight: Boolean,
    onClick: () -> Unit
) {

    Button(onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isLight) lightPink else pink
        ), modifier = Modifier
            .padding(vertical = 12.dp)
            .height(50.dp)
            .fillMaxWidth(), shape = RoundedCornerShape(8.dp)
    ) {
        MediumText(text = text, size = 14, color = Color.White)
    }
}

@Composable
fun StringTextField(
    name: String,
    text: String,
    onValueChange: (String) -> Unit,
    isValidate: Boolean,
    onDelete: () -> Unit
) {
    OutlinedTextField(
        value = text
        , onValueChange = {onValueChange(it)},
        modifier = Modifier
            .padding(vertical = 6.dp)
            .fillMaxWidth(),
        isError = !isValidate,
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = lightGray,
            focusedContainerColor = lightGray,
            unfocusedBorderColor = Color.White,
            focusedBorderColor = Color.White,
            errorBorderColor = Color.Red,
            cursorColor = Color.Black
        ),
        placeholder = {
            RegularText(text = name, size = 18, color = textGray)
        },
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
        textStyle = TextStyle(fontWeight = FontWeight.Normal, color = Color.Black, fontSize = 18.sp),
        trailingIcon = {
            if (text != ""){
                IconButton(onClick = { onDelete() }) {
                    Icon(painter = painterResource(id = R.drawable.close), contentDescription = null,
                        Modifier.size(28.dp))
                }
            }
        }
    )
}

@Composable
fun NumberTextField(
    name: String,
    text: String,
    onValueChange: (String) -> Unit,
    isValidate: Boolean,
    onDelete: () -> Unit
) {
    OutlinedTextField(
        value = text
        , onValueChange = {onValueChange(it)},
        modifier = Modifier
            .padding(vertical = 6.dp)
            .fillMaxWidth(),
        isError = !isValidate,
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = lightGray,
            focusedContainerColor = lightGray,
            unfocusedBorderColor = Color.White,
            focusedBorderColor = Color.White,
            errorBorderColor = Color.Red,
            cursorColor = Color.Black
        ),
        placeholder = {
            RegularText(text = name, size = 18, color = textGray)
        },
        textStyle = TextStyle(fontWeight = FontWeight.Normal, color = Color.Black, fontSize = 18.sp),
        trailingIcon = {
            if (text != ""){
                IconButton(onClick = { onDelete() }) {
                    Icon(painter = painterResource(id = R.drawable.close), contentDescription = null,
                        Modifier.size(28.dp))
                }
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        visualTransformation = MaskVisualTransformation(NumberDefaults.MASK)
    )
}

class MaskVisualTransformation(private val mask: String) : VisualTransformation {

    private val specialSymbolsIndices = mask.indices.filter { mask[it] != '#' }

    override fun filter(text: AnnotatedString): TransformedText {
        var out = ""
        var maskIndex = 0
        text.forEach { char ->
            while (specialSymbolsIndices.contains(maskIndex)) {
                out += mask[maskIndex]
                maskIndex++
            }
            out += char
            maskIndex++

        }
        return TransformedText(AnnotatedString(out), offsetTranslator())
    }

    private fun offsetTranslator() = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            val offsetValue = offset.absoluteValue
            if (offsetValue == 0) return 0
            var numberOfHashtags = 0
            val masked = mask.takeWhile {
                if (it == '#') numberOfHashtags++
                numberOfHashtags < offsetValue
            }
            return masked.length + 1
        }

        override fun transformedToOriginal(offset: Int): Int {
            return mask.take(offset.absoluteValue).count { it == '#' }
        }
    }
}

object NumberDefaults {
    const val MASK = "+7 ###-###-##-##"
    const val INPUT_LENGTH = 15 // Equals to "#####-###".count { it == '#' }
}