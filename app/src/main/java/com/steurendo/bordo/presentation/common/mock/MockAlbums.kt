package com.steurendo.bordo.presentation.common.mock

import java.util.Date
import com.steurendo.bordo.R.drawable
import com.steurendo.bordo.presentation.common.shared_models.AlbumPhotoUiModel
import com.steurendo.bordo.presentation.common.shared_models.AlbumUiModel

private val horizontalPhoto =
    AlbumPhotoUiModel(placeholderId = drawable.pic_h, width = 100, height = 75)
private val verticalPhoto =
    AlbumPhotoUiModel(placeholderId = drawable.pic_v, width = 75, height = 100)

val mockAlbum = AlbumUiModel(
    name = "Album 2024-02-14",
    creationDate = Date(),
    placeholderId = drawable.pic_h,
    photos = listOf(
        verticalPhoto,
        horizontalPhoto
    ),
    referencePhotoIndex = 1
)

val mockAlbums = listOf(
    mockAlbum,
    AlbumUiModel(
        name = "Test Album 2",
        creationDate = Date(),
        placeholderId = drawable.pic_v,
        photos = listOf(verticalPhoto),
        referencePhotoIndex = 0
    ),
    AlbumUiModel(
        name = "Test Album 3",
        creationDate = Date(),
        placeholderId = drawable.pic_h,
        photos = listOf(horizontalPhoto),
        referencePhotoIndex = 0
    ),
    AlbumUiModel(
        name = "Test Album 4",
        creationDate = Date(),
        placeholderId = drawable.pic_h,
        photos = listOf(horizontalPhoto),
        referencePhotoIndex = 0
    ),
    AlbumUiModel(
        name = "Test Album 5",
        creationDate = Date(),
        placeholderId = drawable.pic_v,
        photos = listOf(verticalPhoto),
        referencePhotoIndex = 0
    ),
    AlbumUiModel(
        name = "Test Album 6",
        creationDate = Date(),
        placeholderId = drawable.pic_v,
        photos = listOf(verticalPhoto),
        referencePhotoIndex = 0
    ),
    AlbumUiModel(
        name = "Test Album 7",
        creationDate = Date(),
        placeholderId = drawable.pic_h,
        photos = listOf(horizontalPhoto),
        referencePhotoIndex = 0
    ),
    AlbumUiModel(
        name = "Test Album 8",
        creationDate = Date(),
        placeholderId = drawable.pic_h,
        photos = listOf(horizontalPhoto),
        referencePhotoIndex = 0
    ),
    AlbumUiModel(
        name = "Test Album 9",
        creationDate = Date(),
        placeholderId = drawable.pic_h,
        photos = listOf(horizontalPhoto),
        referencePhotoIndex = 0
    ),
    AlbumUiModel(
        name = "Test Album 10",
        creationDate = Date(),
        placeholderId = drawable.pic_v,
        photos = listOf(verticalPhoto),
        referencePhotoIndex = 0
    )
)