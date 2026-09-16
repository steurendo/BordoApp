package com.steurendo.bordo.utils

import android.graphics.Bitmap
import android.graphics.Rect
import androidx.core.graphics.scale
import com.steurendo.bordo.domain.model.CropMask
import kotlin.math.roundToInt

val Bitmap.aspectRatio: Float
    get() = this.width.toFloat() / this.height

fun Bitmap.crop(cropMask: CropMask): Bitmap {
    return Bitmap.createBitmap(
        this,
        (cropMask.left * this.width).roundToInt(),
        (cropMask.top * this.height).roundToInt(),
        (this.width * (1 - (cropMask.left + cropMask.right))).roundToInt(),
        (this.height * (1 - (cropMask.top + cropMask.bottom))).roundToInt()
    )
}

fun Bitmap.crop(rect: Rect): Bitmap =
    Bitmap.createBitmap(this, rect.left, rect.top, rect.width(), rect.height())

fun Bitmap.scale(factor: Float): Bitmap =
    this.scale((this.width * factor).toInt(), (this.height * factor).toInt())