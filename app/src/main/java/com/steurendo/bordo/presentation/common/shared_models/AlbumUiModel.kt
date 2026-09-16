package com.steurendo.bordo.presentation.common.shared_models

import com.steurendo.bordo.domain.model.EffectParams
import com.steurendo.bordo.domain.model.PaddingEffect
import com.steurendo.bordo.domain.model.WhiteBlackEffectParams
import java.util.Date

data class AlbumUiModel(
    val id: Int = 0,
    val name: String = "",
    val referencePhotoIndex: Int = 0,
    val photos: List<AlbumPhotoUiModel> = emptyList(),
    val paddingEffect: PaddingEffect = PaddingEffect.WhiteBlack,
    val effectParameters: EffectParams = WhiteBlackEffectParams(),
    val creationDate: Date = Date(),
    val placeholderId: Int? = null
)

fun AlbumUiModel.getPhoto(photoIndex: Int): AlbumPhotoUiModel = photos[photoIndex]
fun AlbumUiModel.getReferencePhoto(): AlbumPhotoUiModel = photos[referencePhotoIndex]