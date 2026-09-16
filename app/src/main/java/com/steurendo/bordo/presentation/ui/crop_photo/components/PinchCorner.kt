package com.steurendo.bordo.presentation.ui.crop_photo.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import com.steurendo.bordo.presentation.common.shared_modules.PinchMove


enum class HorizontalAlignment { Left, Right }
enum class VerticalAlignment { Top, Bottom }

@Composable
fun PinchCorner(
    modifier: Modifier,
    onPinch: PinchMove,
    shape: DrawScope.() -> Unit
) {
    Canvas(
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    onPinch(dragAmount.x, dragAmount.y)
                }
            }) {
        shape()
    }
}

@Composable
fun CircledPinchCorner(
    modifier: Modifier = Modifier,
    radius: Float = 8f,
    horizontalAlignment: HorizontalAlignment,
    verticalAlignment: VerticalAlignment,
    onPinch: PinchMove
) {
    PinchCorner(
        modifier = modifier,
        onPinch = onPinch
    ) {
        drawCircle(
            color = Color.White,
            radius = radius,
            center = Offset(
                x = if (horizontalAlignment == HorizontalAlignment.Left) 0f else size.width,
                y = if (verticalAlignment == VerticalAlignment.Top) 0f else size.height,
            )
        )
    }
}