package com.steurendo.bordo.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import androidx.core.graphics.createBitmap
import androidx.core.graphics.scale
import com.google.android.renderscript.Toolkit
import com.steurendo.bordo.domain.model.BlurEffectParams
import com.steurendo.bordo.domain.model.EffectParams
import com.steurendo.bordo.domain.model.PaddingEffect
import com.steurendo.bordo.domain.model.WhiteBlackEffectParams
import kotlin.math.max
import kotlin.math.min

object ImagePadding {
    private const val MAX_BLUR_RADIUS_ALLOWED = 25
    private const val NORMALIZED_IMAGE_SIZE = 400
    const val RADIUS_PER_SIZE_RATIO = MAX_BLUR_RADIUS_ALLOWED.toFloat() / NORMALIZED_IMAGE_SIZE

    fun getPaddedImage(
        image: Bitmap,
        outputAspectRatio: Float,
        effect: PaddingEffect,
        params: EffectParams
    ): Bitmap {
        val outImg = getPaddingBackground(image, outputAspectRatio, effect, params)
        val offsetW = (outImg.width - image.width) / 2
        val offsetH = (outImg.height - image.height) / 2
        val canvas = Canvas(outImg)
        canvas.drawBitmap(image, offsetW.toFloat(), offsetH.toFloat(), null)
        return outImg
    }

    private fun getPaddingBackground(
        image: Bitmap,
        outputAspectRatio: Float,
        effect: PaddingEffect,
        params: EffectParams
    ): Bitmap {
        val outImg = cropAndScaleImage(image, outputAspectRatio)
        return when (effect) {  // Applying effect
            PaddingEffect.WhiteBlack -> {
                val background = generateWhiteBlackImage(
                    outImg.width,
                    outImg.height,
                    isBlack = (params as WhiteBlackEffectParams).isBlack
                )
                if (outImg !== image) outImg.recycle()
                background
            }

            PaddingEffect.Blur -> outImg.blur(radiusFactor = (params as BlurEffectParams).blurRadius)
        }
    }


    private fun generateWhiteBlackImage(width: Int, height: Int, isBlack: Boolean): Bitmap {
        val outImg = createBitmap(width, height)
        val canvas = Canvas(outImg)
        canvas.drawColor(if (isBlack) Color.BLACK else Color.WHITE)
        return outImg
    }

    fun Bitmap.blur(radiusFactor: Float): Bitmap {
        val radius: Int = (MAX_BLUR_RADIUS_ALLOWED * radiusFactor).toInt()
        if (radius == 0)
            return this

        val targetWidth = this.width
        val targetHeight = this.height
        val scaleFactor: Float =
            NORMALIZED_IMAGE_SIZE.toFloat() / min(this.width, this.height)
        val downscaledImage = this.scale(scaleFactor)
        val blurredImage = Toolkit.blur(downscaledImage, radius)
        if (downscaledImage !== this) downscaledImage.recycle()

        val rescaledImage = blurredImage.scale(targetWidth, targetHeight)
        if (rescaledImage !== blurredImage) blurredImage.recycle()

        return rescaledImage
    }

    private fun cropAndScaleImage(image: Bitmap, aspectRatio: Float): Bitmap {
        if (image.aspectRatio == aspectRatio)
            return image
        val cropRect: Rect = when (image.aspectRatio < aspectRatio) {
            true -> {
                val outHeight = (image.height * image.aspectRatio / aspectRatio).toInt()
                val outTop = ((image.height - outHeight) / 2)
                Rect(
                    0,
                    outTop,
                    image.width,
                    outTop + outHeight
                )
            }

            false -> {
                val outWidth = (image.width / image.aspectRatio * aspectRatio).toInt()
                val outLeft = (image.width - outWidth) / 2
                Rect(
                    outLeft,
                    0,
                    outLeft + outWidth,
                    image.height
                )
            }
        }
        val scaleFactor =
            max(aspectRatio, image.aspectRatio) / min(aspectRatio, image.aspectRatio)
        return image.crop(cropRect).scale(scaleFactor)
    }
}
