package com.pereyrarg11.navigation.core.presentation.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.pereyrarg11.navigation.core.presentation.designsystem.AppTheme

@Composable
fun AppOutlinedButton(
    label: String,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    onClick: () -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(12.dp),
        enabled = isEnabled,
        border = BorderStroke(
            width = ButtonDefaults.outlinedButtonBorder(isEnabled).width,
            color = MaterialTheme.colorScheme.primary
        )
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
private fun AppOutlinedButtonPreview() {
    AppTheme {
        AppOutlinedButton(
            label = "Click Me",
            onClick = {},
        )
    }
}
