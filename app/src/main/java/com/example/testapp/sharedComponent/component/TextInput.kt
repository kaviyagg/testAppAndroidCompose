import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun TextInput(
    label: String,
    modifier: Modifier = Modifier,
    placeHolder: String = "",
    enabled: Boolean = true,
    readOnly: Boolean = false,
    onValueChange: ((String) -> Unit)? = null,
    value: String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textStyle: TextStyle = TextStyle.Default,
    isError: Boolean = false,
    errorMessage: String? = null,
    maxLines: Int = Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions? = null,
    singleLine: Boolean = true,
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(),
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    focusRequester: FocusRequester = FocusRequester(),
    onBlur: (() -> Unit?)? = null,
    onNext: (() -> Unit?)? = null
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { focusRequester }
    var isFocused by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val updatedModifier = modifier
        .focusRequester(focusRequester)
        .onFocusChanged {
            if (!it.isFocused && isFocused) {
                if (onBlur != null) {
                    onBlur()
                }
                isFocused = false
            } else if (it.isFocused) {
                isFocused = true
            }
        }

    val keyboardActionsMerged = KeyboardActions(
        onDone = {
            keyboardActions?.onDone?.let { it() }
            focusManager.clearFocus()
            keyboardController?.hide()
        },
        onGo = keyboardActions?.onGo,
        onNext = keyboardActions?.onNext,
        onPrevious = keyboardActions?.onPrevious,
        onSearch = keyboardActions?.onSearch,
        onSend = keyboardActions?.onSend
    )

    if (onValueChange != null) {
        TextField(value = value,
            label = { Text(text = label) },
            placeholder = { Text(text = placeHolder) },
            enabled = enabled,
            readOnly = readOnly,
            modifier = updatedModifier,
            textStyle = textStyle,
            isError = isError,
            maxLines = maxLines,
            keyboardActions = keyboardActionsMerged,
            keyboardOptions = keyboardOptions,
            singleLine = singleLine,
            colors = colors,
            trailingIcon = trailingIcon,
            leadingIcon = leadingIcon,
            onValueChange = onValueChange,
            visualTransformation = visualTransformation,
            supportingText = {
                if (errorMessage != null) {
                    Text(text = errorMessage)
                }
            })
    }
}

