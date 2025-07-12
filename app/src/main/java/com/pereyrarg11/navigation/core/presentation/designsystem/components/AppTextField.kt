package com.pereyrarg11.navigation.core.presentation.designsystem.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.TextFieldLineLimits
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.pereyrarg11.navigation.core.presentation.designsystem.AppTheme
import com.pereyrarg11.navigation.core.presentation.tools.UiText

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppTextField(
    state: TextFieldState,
    label: UiText,
    modifier: Modifier = Modifier,
    errorMessage: UiText? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    var isFocused by remember {
        mutableStateOf(false)
    }
    val inputShape = RoundedCornerShape(8.dp)
    val errorMessageStr = errorMessage?.asString().orEmpty()

    Column(
        modifier = modifier,
    ) {
        Text(
            text = label.asString(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.height(6.dp))
        BasicTextField2(
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .clip(inputShape)
                .background(
                    if (isFocused) {
                        MaterialTheme.colorScheme.primary.copy(
                            alpha = 0.05f,
                        )
                    } else {
                        MaterialTheme.colorScheme.surface
                    }
                )
                .border(
                    width = 1.dp,
                    color = when {
                        errorMessageStr.isNotEmpty() -> MaterialTheme.colorScheme.error
                        else -> MaterialTheme.colorScheme.primary
                    },
                    shape = inputShape,
                )
                .padding(16.dp)
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            textStyle = LocalTextStyle.current.copy(
                color = MaterialTheme.colorScheme.onBackground,
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
            ),
            lineLimits = TextFieldLineLimits.SingleLine,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
        )
        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = errorMessage.asString(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
            )
        }
    }
}

class AppTextFieldErrorMessageProvider : PreviewParameterProvider<UiText?> {
    override val values: Sequence<UiText?>
        get() = sequenceOf(
            null,
            UiText.StaticString("Error message")
        )
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@PreviewLightDark
@Composable
private fun AppTextFieldPreview(
    @PreviewParameter(AppTextFieldErrorMessageProvider::class) errorMessage: UiText?,
) {
    AppTheme {
        Surface {
            AppTextField(
                state = TextFieldState(initialText = "Text"),
                label = UiText.StaticString("Label"),
                errorMessage = errorMessage,
            )
        }
    }
}
