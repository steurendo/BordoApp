package com.steurendo.bordo.presentation.mapper

import androidx.core.net.toUri
import com.steurendo.bordo.domain.model.AlbumPhoto
import com.steurendo.bordo.presentation.common.shared_models.AlbumPhotoUiModel

fun AlbumPhotoUiModel.toDomain(): AlbumPhoto {
    return AlbumPhoto(
        uriString = this.uri.toString(),
        cropMask = this.cropMask,
        width = this.width,
        height = this.height
    )
}

fun AlbumPhoto.toUiModel(): AlbumPhotoUiModel {
    return AlbumPhotoUiModel(
        uri = this.uriString.toUri(),
        cropMask = this.cropMask,
        width = this.width,
        height = this.height
    )
}