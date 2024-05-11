package com.topic_trove.ui.modules.profilescreen.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.topic_trove.ui.core.values.AppColors
import com.topic_trove.ui.core.values.CustomTextStyle

@Composable
fun TextFieldCard(
    title: String,
    isPasswordField: Boolean = false,
    placeholder: String = title,
    onTextChange: (String) -> Unit,
    validateInput: (String) -> Boolean
) {
    var value by remember {
        mutableStateOf("")
    }
    var isValid by remember { mutableStateOf(true) }
    Column {
        Text(text = title, style = CustomTextStyle.titleFieldCreatePost())
        Spacer(modifier = Modifier.height(15.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = AppColors.TextFieldColor,
                    shape = RoundedCornerShape(12.dp)
                )
                .clip(RoundedCornerShape(12.dp))
        ) {
            TextField(
                value = value, onValueChange = {
                    onTextChange(it)
                    value = it
                    isValid = validateInput(it)
                },
                
                textStyle = CustomTextStyle.textInputField(),
                modifier = Modifier
                    .padding(2.dp,1.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = AppColors.TextFieldColor,
                    disabledContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent
                ),

                placeholder = {
                    Text(text = placeholder, style = CustomTextStyle.hintTextCreatePost())
                },
                visualTransformation = if (isPasswordField) PasswordVisualTransformation() else VisualTransformation.None
            )
        }
    }
}


