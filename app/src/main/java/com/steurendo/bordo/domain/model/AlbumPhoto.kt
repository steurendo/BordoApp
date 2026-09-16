package com.steurendo.bordo.domain.model

import kotlin.math.roundToInt

data class AlbumPhoto(
    val uriString: String = "",
    val width: Int = 0,
    val height: Int = 0,
    val cropMask: CropMask = CropMask()
)

val AlbumPhoto.aspectRatio: Float get() = width.toFloat() / height
val AlbumPhoto.computedWidth: Int get() = (width * (1 - (cropMask.left + cropMask.right))).roundToInt()
val AlbumPhoto.computedHeight: Int get() = (height * (1 - (cropMask.top + cropMask.bottom))).roundToInt()
val AlbumPhoto.computedAspectRatio: Float get() = computedWidth.toFloat() / computedHeight