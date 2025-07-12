package com.pereyrarg11.navigation.core.presentation.designsystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.pereyrarg11.navigation.core.presentation.designsystem.AppTheme
import com.pereyrarg11.navigation.core.presentation.tools.UiText

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: UiText,
    hint: UiText,
    modifier: Modifier = Modifier,
    errorMessage: UiText? = null,
    isInputSecret: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    var isPasswordVisible by remember {
        mutableStateOf(isInputSecret)
    }
    Column(
        modifier = modifier,
    ) {
        Text(
            text = label.asString(),
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (isPasswordVisible) {
                PasswordVisualTransformation(mask = '*')
            } else VisualTransformation.None,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.Transparent,
            ),
            placeholder = {
                Text(
                    text = hint.asString(),
                    style = MaterialTheme.typography.bodyLarge,
                )
            },
            textStyle = MaterialTheme.typography.bodyLarge,
            shape = RoundedCornerShape(10.dp),
            trailingIcon = {
                if (isInputSecret) {
                    IconButton(
                        onClick = {
                            isPasswordVisible = !isPasswordVisible
                        }
                    ) {
                        when {
                            isPasswordVisible -> {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = "Hide password"
                                )
                            }

                            !isPasswordVisible -> {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Show password"
                                )
                            }
                        }

                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
            )
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

class AppTextFieldParameterProvider : PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean>
        get() = sequenceOf(false, true)
}

@Preview
@PreviewLightDark
@Composable
private fun AppTextFieldPreview(
    @PreviewParameter(AppTextFieldParameterProvider::class) isInputSecret: Boolean,
) {
    AppTheme {
        AppTextField(
            value = "Gabriel_",
            onValueChange = {},
            label = UiText.StaticString("Name"),
            hint = UiText.StaticString("Your name"),
            errorMessage = UiText.StaticString("Special characters are not allowed"),
            isInputSecret = isInputSecret,
        )
    }
}
