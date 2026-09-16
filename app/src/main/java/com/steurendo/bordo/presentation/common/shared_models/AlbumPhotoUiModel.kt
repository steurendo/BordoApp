package com.steurendo.bordo.presentation.common.shared_models

import android.net.Uri
import com.steurendo.bordo.domain.model.CropMask
import kotlin.math.roundToInt

data class AlbumPhotoUiModel(
    val uri: Uri = Uri.EMPTY,
    val width: Int = 0,
    val height: Int = 0,
    val cropMask: CropMask = CropMask(),
    val placeholderId: Int? = null
)


val AlbumPhotoUiModel.aspectRatio: Float get() = width.toFloat() / height
val AlbumPhotoUiModel.computedWidth: Int get() = (width * (1 - (cropMask.left + cropMask.right))).roundToInt()
val AlbumPhotoUiModel.computedHeight: Int get() = (height * (1 - (cropMask.top + cropMask.bottom))).roundToInt()
val AlbumPhotoUiModel.computedAspectRatio: Float get() = computedWidth.toFloat() / computedHeight