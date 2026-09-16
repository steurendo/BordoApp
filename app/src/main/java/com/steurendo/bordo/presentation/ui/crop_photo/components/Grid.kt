package com.steurendo.bordo.presentation.ui.crop_photo.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned


@Composable
fun Grid(
    modifier: Modifier = Modifier,
    numberOfLines: Int,
    linesStroke: Float = 1f
) {
    var width by remember { mutableFloatStateOf(0f) }
    var height by remember { mutableFloatStateOf(0f) }
    Canvas(modifier = modifier.onGloballyPositioned {
        width = it.size.width.toFloat()
        height = it.size.height.toFloat()
    }) {
        for (i in 1..numberOfLines) {
            drawLine(
                color = Color.DarkGray,
                start = Offset(
                    x = width * i / (numberOfLines + 1),
                    y = 0f
                ),
                end = Offset(
                    x = width * i / (numberOfLines + 1),
                    y = height
                ),
                strokeWidth = linesStroke
            )
            drawLine(
                color = Color.DarkGray,
                start = Offset(
                    x = 0f,
                    height * i / (numberOfLines + 1)
                ),
                end = Offset(
                    x = width,
                    height * i / (numberOfLines + 1)
                ),
                strokeWidth = linesStroke
            )
        }
    }
}