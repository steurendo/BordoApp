package com.steurendo.bordo.data.mapper

import com.steurendo.bordo.data.db.entities.AlbumEntity
import com.steurendo.bordo.domain.model.Album

fun AlbumEntity.toDomain(): Album = Album(
    id = id,
    name = name,
    referencePhotoIndex = referencePhotoIndex,
    photos = photos,
    paddingEffect = paddingEffect,
    effectParamsParser = effectParamsParser,
    creationDate = creationDate
)

fun Album.toData(): AlbumEntity = AlbumEntity(
    id = id,
    name = name,
    referencePhotoIndex = referencePhotoIndex,
    photos = photos,
    paddingEffect = paddingEffect,
    effectParamsParser = effectParamsParser,
    creationDate = creationDate
)