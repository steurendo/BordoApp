package com.steurendo.bordo.presentation.ui.home.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutExpo
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds


@Composable
fun SplashSuccessPanel(onFinished: () -> Unit) {
    val alphaPanel = remember { Animatable(0f) }
    val alphaCheck = remember { Animatable(0f) }
    val circleProgress = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        alphaPanel.animateTo(1f, animationSpec = tween(200))
        circleProgress.animateTo(1f, animationSpec = tween(400))
        alphaCheck.animateTo(1f, animationSpec = tween(500, easing = EaseOutExpo))
        delay(200.milliseconds)
        alphaPanel.animateTo(0f, animationSpec = tween(200))
        onFinished()
    }

    SuccessPanel(
        modifier = Modifier
            .fillMaxSize()
            .alpha(alphaPanel.value),
        circleProgress = circleProgress.value,
        alphaCheck = alphaCheck.value
    )
}

@Composable
fun SuccessPanel(
    modifier: Modifier,
    circleProgress: Float = 1f,
    alphaCheck: Float = 1f
) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.7f)
        ) { Box(modifier = Modifier.fillMaxSize().background(color = Color.Gray)) }
        SuccessIcon(
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.Center),
            circleProgress = circleProgress,
            alphaCheck = alphaCheck
        )
    }
}

@Composable
fun SuccessIcon(
    modifier: Modifier = Modifier,
    circleProgress: Float,
    alphaCheck: Float
) {
    Box(modifier = modifier) {
        Icon(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize(0.8f)
                .alpha(alphaCheck),
            imageVector = Icons.Filled.Check,
            tint = Color.Green,
            contentDescription = null
        )
        Canvas(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
        ) {
            drawArc(
                color = Color.Green,
                startAngle = 0f,
                sweepAngle = circleProgress * 360,
                useCenter = false,
                style = Stroke(width = 32f)
            )
        }
    }
}


@Preview
@Composable
fun PreviewSuccessPanel() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        SuccessPanel(modifier = Modifier.fillMaxSize())
    }
}