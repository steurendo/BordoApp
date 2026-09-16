package com.steurendo.bordo.presentation.ui.crop_photo.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.steurendo.bordo.presentation.common.shared_modules.PinchCornersReactor
import com.steurendo.bordo.presentation.common.shared_modules.PinchMove
import com.steurendo.bordo.domain.model.CropMask
import kotlin.math.roundToInt


@Composable
fun Float.toDp() = with(LocalDensity.current) { this@toDp.toDp() }

@Composable
fun CropFrame(
    modifier: Modifier = Modifier,
    cropRect: CropMask,
    frameWidth: Float = 2f,
    showGrid: Boolean = true,
    pinchCornersReactor: PinchCornersReactor,
    pinchAvailableOnBorders: Boolean,
    moveCropMask: PinchMove
) {
    var width by remember { mutableFloatStateOf(0f) }
    var height by remember { mutableFloatStateOf(0f) }
    Box(modifier = modifier.onGloballyPositioned {
        width = it.size.width.toFloat()
        height = it.size.height.toFloat()
    }) {
        if (showGrid)
            Grid(
                modifier = Modifier
                    .size(
                        width = (width * (1 - (cropRect.left + cropRect.right))).toDp(),
                        height = (height * (1 - (cropRect.top + cropRect.bottom))).toDp()
                    )
                    .offset {
                        IntOffset(
                            x = (width * cropRect.left).roundToInt(),
                            y = (height * cropRect.top).roundToInt()
                        )
                    },
                numberOfLines = 2,
                linesStroke = 3f
            )
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRect(
                color = Color.LightGray,
                topLeft = Offset(width * cropRect.left, height * cropRect.top),
                size = Size(
                    width * (1 - (cropRect.left + cropRect.right)),
                    height * (1 - (cropRect.top + cropRect.bottom))
                ),
                style = Stroke(width = frameWidth)
            )
        }
        Pinches(
            modifier = Modifier
                .size(
                    width = (width * (1 - (cropRect.left + cropRect.right))).toDp(),
                    height = (height * (1 - (cropRect.top + cropRect.bottom))).toDp()
                )
                .offset {
                    IntOffset(
                        x = (width * cropRect.left).roundToInt(),
                        y = (height * cropRect.top).roundToInt()
                    )
                },
            pinchCornersReactor = pinchCornersReactor,
            pinchAvailableOnBorders = pinchAvailableOnBorders,
            moveCropMask = moveCropMask
        )
    }
}

val cornerSize = 16.dp

@Composable
private fun Pinches(
    modifier: Modifier,
    pinchCornersReactor: PinchCornersReactor,
    pinchAvailableOnBorders: Boolean,
    moveCropMask: PinchMove,
) {
    val localDensity = LocalDensity.current
    var cornerWidth by remember { mutableStateOf(0.dp) }
    var cornerHeight by remember { mutableStateOf(0.dp) }
    Box(modifier = modifier.onGloballyPositioned {
        with(localDensity) {
            cornerWidth = it.size.width.toDp() - cornerSize
            cornerHeight = it.size.height.toDp() - cornerSize
        }
    }) {
        // MOVING MASK
        PinchCorner(
            Modifier.fillMaxSize(),
            onPinch = moveCropMask,
            shape = {})
        if (pinchAvailableOnBorders) {
            // LEFT
            PinchCorner(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .fillMaxHeight()
                    .width(cornerSize),
                onPinch = pinchCornersReactor.onPinchLeft,
                shape = {}
            )
            // TOP
            PinchCorner(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .height(cornerSize)
                    .fillMaxWidth(),
                onPinch = pinchCornersReactor.onPinchTop,
                shape = {}
            )
            // RIGHT
            PinchCorner(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .width(cornerSize)
                    .fillMaxHeight(),
                onPinch = pinchCornersReactor.onPinchRight,
                shape = {}
            )
            // BOTTOM
            PinchCorner(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .height(cornerSize)
                    .fillMaxWidth(),
                onPinch = pinchCornersReactor.onPinchBottom,
                shape = {}
            )
        }
        // TOP LEFT
        CircledPinchCorner(
            modifier = Modifier
                .align(Alignment.TopStart)
                .size(cornerSize),
            horizontalAlignment = HorizontalAlignment.Left,
            verticalAlignment = VerticalAlignment.Top,
            onPinch = pinchCornersReactor.onPinchLeftTop,
        )
        // TOP RIGHT
        CircledPinchCorner(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(cornerSize),
            horizontalAlignment = HorizontalAlignment.Right,
            verticalAlignment = VerticalAlignment.Top,
            onPinch = pinchCornersReactor.onPinchRightTop,
        )
        // BOTTOM RIGHT
        CircledPinchCorner(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(cornerSize),
            horizontalAlignment = HorizontalAlignment.Right,
            verticalAlignment = VerticalAlignment.Bottom,
            onPinch = pinchCornersReactor.onPinchBottomRight,
        )
        // BOTTOM LEFT
        CircledPinchCorner(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .size(cornerSize),
            horizontalAlignment = HorizontalAlignment.Left,
            verticalAlignment = VerticalAlignment.Bottom,
            onPinch = pinchCornersReactor.onPinchBottomLeft,
        )
    }
}