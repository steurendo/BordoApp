package com.steurendo.bordo.presentation.ui.crop_photo

import com.steurendo.bordo.presentation.common.shared_models.AlbumUiModel
import com.steurendo.bordo.domain.model.CropMask

data class PhotoCroppingUiState(
    val album: AlbumUiModel = AlbumUiModel(),
    val photoIndex: Int = 0,
    val newCropMask: CropMask = CropMask(),
    val croppingMode: PhotoCroppingMode = PhotoCroppingMode.FreeTransform
)