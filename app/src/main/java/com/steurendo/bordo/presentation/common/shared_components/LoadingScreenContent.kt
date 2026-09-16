package com.steurendo.bordo.presentation.common.shared_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.steurendo.bordo.presentation.theme.PreviewTheme
import com.steurendo.bordo.presentation.theme.BordoAppTheme

@Composable
fun LoadingScreenContent(modifier: Modifier = Modifier, message: String? = null) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        LoadingPanel(message = message)
    }
}

@PreviewTheme
@Composable
fun PreviewLoadingScreenContent() {
    BordoAppTheme {
        LoadingScreenContent(message = "Loading albums...")
    }
}