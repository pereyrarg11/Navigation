package com.pereyrarg11.navigation.core.presentation.designsystem.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.pereyrarg11.navigation.core.presentation.designsystem.AppTheme

@Composable
fun AppButton(
    label: String,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        contentPadding = PaddingValues(12.dp),
        enabled = isEnabled,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
        )
    }
}

@Preview
@PreviewLightDark
@Composable
private fun AppButtonPreview() {
    AppTheme {
        AppButton(
            label = "Click Me",
            onClick = {},
        )
    }
}
