package com.steurendo.bordo.presentation.ui.intro

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun IntroPage(
    onSplashFinished: (showOnboarding: Boolean) -> Unit,
    vm: IntroScreenViewModel
) {
    val uiState by vm.uiState.collectAsState()
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(true) {
        alpha.animateTo(1f, animationSpec = tween(1500))
        delay(500.milliseconds)
        onSplashFinished(uiState.showOnboarding)
    }

    IntroTemplate(logoAlpha = alpha.value)
}