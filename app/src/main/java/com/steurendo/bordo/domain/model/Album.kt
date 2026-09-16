package com.steurendo.bordo.domain.model

import com.steurendo.bordo.presentation.common.shared_modules.EffectParamsParser
import java.util.Date

data class Album(
    val id: Int = 0,
    val name: String,
    val referencePhotoIndex: Int,
    val photos: List<AlbumPhoto>,
    val paddingEffect: PaddingEffect,
    val effectParamsParser: EffectParamsParser,
    val creationDate: Date
)

fun Album.getReferencePhoto(): AlbumPhoto = photos[referencePhotoIndex]