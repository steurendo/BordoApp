package com.steurendo.bordo.presentation.common.mock

import com.steurendo.bordo.R
import com.steurendo.bordo.presentation.common.shared_models.AlbumPhotoUiModel

private val photoVertical =
    AlbumPhotoUiModel(placeholderId = R.drawable.pic_v, width = 3000, height = 4000)
private val photoHorizontal =
    AlbumPhotoUiModel(placeholderId = R.drawable.pic_h, width = 4000, height = 3000)
private val photoLogo =
    AlbumPhotoUiModel(placeholderId = R.drawable.logo, width = 512, height = 474)

val testPhotos: List<AlbumPhotoUiModel> = listOf(
    photoHorizontal,
    photoVertical,
    photoLogo
)