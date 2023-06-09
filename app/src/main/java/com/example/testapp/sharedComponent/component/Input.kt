package com.example.testapp.sharedComponent.component

import TextInput
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.testapp.util.Form


enum class InputType {
    TEXT, EMAIL, PASSWORD, CHECKBOX
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Input(
    type: InputType = InputType.TEXT,
    modifier: Modifier = Modifier,
    form: Form? = null,
    name: String,
    label: String,
    placeHolder: String = "",
    onValueChange: ((Any) -> Unit)? = null,
    value: Any = "",
    imeAction: ImeAction? = null,
    focusRequester: FocusRequester = FocusRequester(),
    onDone: (() -> Unit)? = null,
) {
    fun onValueChanged(value: Any) {
        if (form != null) {
            form.update(name, value)
        } else if (onValueChange != null) {
            onValueChange(value)
        }
    }

    fun onBlur() {
        form?.touched(name)
    }

    fun getKeyboardOption(keyboardType: KeyboardType): KeyboardOptions {
        return if (imeAction != null) {
            KeyboardOptions(
                keyboardType = keyboardType, imeAction = imeAction
            )
        } else {
            KeyboardOptions(
                keyboardType = keyboardType
            )
        }
    }

    fun getValue(): String = if (form != null) form.getValue<String>(name)!!
    else value as String

    fun getCheckValue(): Boolean = if (form != null) form.getValue<Boolean>(name)!!
    else false

    fun isError(): Boolean = form?.getField(name)?.showError ?: false

    fun getErrorMessage(): String? = form?.getField(name)?.errorMessage

    val keyboardActions = KeyboardActions(onDone = {
        if (onDone != null) {
            onDone()
        }
    })

    when (type) {
        InputType.EMAIL -> {
            TextInput(
                label = label,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                value = getValue(),
                keyboardOptions = getKeyboardOption(KeyboardType.Email),
                onValueChange = {
                    onValueChanged(it)
                },
                onBlur = {
                    onBlur()
                },
                isError = isError(),
                errorMessage = getErrorMessage(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent, unfocusedIndicatorColor = Color.LightGray
                ),
                keyboardActions = keyboardActions,
                focusRequester = focusRequester
            )
        }





        else -> {
            TextInput(
                label = label,
                modifier = Modifier.fillMaxWidth(),
                value = getValue(),
                keyboardOptions = getKeyboardOption(KeyboardType.Text),
                onValueChange = {
                    onValueChanged(it)
                },
                isError = isError(),
                errorMessage = getErrorMessage(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent, unfocusedIndicatorColor = Color.LightGray
                ),
                onBlur = {
                    onBlur()
                },
                keyboardActions = keyboardActions,
                focusRequester = focusRequester
            )
        }
    }


}