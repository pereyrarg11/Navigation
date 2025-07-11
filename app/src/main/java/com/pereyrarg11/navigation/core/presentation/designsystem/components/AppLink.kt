package com.pereyrarg11.navigation.core.presentation.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.pereyrarg11.navigation.ui.theme.AppTheme

@Composable
fun AppLink(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(
        text = label,
        modifier = modifier
            .clickable(onClick = onClick),
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Center,
    )
}

@Preview
@PreviewLightDark
@Composable
private fun AppLinkPreview() {
    AppTheme {
        AppLink(
            label = "Click Me",
            onClick = {},
        )
    }
}