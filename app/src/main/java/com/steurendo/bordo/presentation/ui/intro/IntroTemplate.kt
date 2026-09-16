package com.steurendo.bordo.presentation.ui.intro

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.steurendo.bordo.presentation.common.shared_components.AppLogo
import com.steurendo.bordo.presentation.theme.PreviewTheme
import com.steurendo.bordo.presentation.theme.BordoAppTheme


@Composable
fun IntroTemplate(logoAlpha: Float) {
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            AppLogo(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.6f)
                    .alpha(logoAlpha)
            )
        }
    }
}


@PreviewTheme
@Composable
private fun PreviewIntro() {
    BordoAppTheme {
        IntroTemplate(logoAlpha = 1f)
    }
}
