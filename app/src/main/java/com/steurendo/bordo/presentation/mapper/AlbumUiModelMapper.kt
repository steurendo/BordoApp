package com.steurendo.bordo.presentation.mapper

import com.steurendo.bordo.domain.model.Album
import com.steurendo.bordo.domain.model.AlbumPhoto
import com.steurendo.bordo.presentation.common.shared_models.AlbumPhotoUiModel
import com.steurendo.bordo.presentation.common.shared_models.AlbumUiModel
import com.steurendo.bordo.presentation.common.shared_modules.EffectParamsParser

fun Album.toUiModel(): AlbumUiModel = AlbumUiModel(
    id = id,
    name = this.name,
    referencePhotoIndex = this.referencePhotoIndex,
    photos = this.photos.map(AlbumPhoto::toUiModel),
    paddingEffect = this.paddingEffect,
    effectParameters = EffectParamsParser.parseTo(this.effectParamsParser),
    creationDate = this.creationDate
)

fun AlbumUiModel.toDomain(): Album = Album(
    id = this.id,
    name = this.name,
    referencePhotoIndex = this.referencePhotoIndex,
    photos = photos.map(AlbumPhotoUiModel::toDomain),
    paddingEffect = this.paddingEffect,
    effectParamsParser = EffectParamsParser.parseFrom(this.effectParameters),
    creationDate = this.creationDate
)