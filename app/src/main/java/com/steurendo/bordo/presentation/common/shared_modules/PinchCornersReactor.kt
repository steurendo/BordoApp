package com.steurendo.bordo.presentation.common.shared_modules

typealias PinchMove = (x: Float, y: Float) -> Unit

data class PinchCornersReactor(
    val onPinchLeftTop: PinchMove,
    val onPinchRightTop: PinchMove,
    val onPinchBottomRight: PinchMove,
    val onPinchBottomLeft: PinchMove,
    val onPinchLeft: PinchMove,
    val onPinchTop: PinchMove,
    val onPinchRight: PinchMove,
    val onPinchBottom: PinchMove,
)