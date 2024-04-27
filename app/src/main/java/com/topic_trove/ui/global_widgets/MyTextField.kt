package com.topic_trove.ui.global_widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.topic_trove.ui.core.values.AppColors
import com.topic_trove.ui.modules.registerscreen.states.TextFieldState

@Composable
fun MyTextField(
    modifier: Modifier = Modifier,
    textState: TextFieldState,
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {},
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                textState.onFocusChange(focusState.isFocused)
                if (!focusState.isFocused) {
                    textState.enableShowErrors()
                }
            },
        shape = RoundedCornerShape(12.dp),
        value = textState.text,
        onValueChange = {
            textState.text = it
        },
        isError = textState.showErrors(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = AppColors.BorderStroke,
            unfocusedBorderColor = AppColors.BorderStroke,
        ),
        textStyle = TextStyle(
            color = AppColors.Black,
//            fontStyle = FontStyle.Italic,
            fontSize = 14.sp,
            fontWeight = FontWeight(400),
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction,
            keyboardType = KeyboardType.Email
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onImeAction()
            }
        ),
        supportingText = {
            textState.getError()?.let { error -> TextFieldError(textError = error) }
        },
//        singleLine = true,
    )
}