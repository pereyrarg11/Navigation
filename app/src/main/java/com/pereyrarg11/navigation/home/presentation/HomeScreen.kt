package com.pereyrarg11.navigation.home.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.pereyrarg11.navigation.R
import com.pereyrarg11.navigation.core.presentation.designsystem.AppTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    HomeScreenContent(
        modifier = modifier,
    )
}

@Composable
private fun HomeScreenContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.app_label_home),
        )
    }
}

@Preview
@Composable
private fun HomeScreenContentPreview() {
    AppTheme {
        Surface {
            HomeScreenContent()
        }
    }
}
