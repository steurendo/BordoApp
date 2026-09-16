package com.steurendo.bordo.domain.usecases.album_management

import javax.inject.Inject

class SetReferencePhotoToCurrentAlbumManagementUseCase @Inject constructor(
    private val getCurrentAlbumManagementUseCase: GetCurrentAlbumManagementUseCase,
    private val setCurrentAlbumManagementUseCase: SetCurrentAlbumManagementUseCase
) {
    operator fun invoke(photoIndex: Int) {
        val album = getCurrentAlbumManagementUseCase()
        val updatedAlbum = album.copy(referencePhotoIndex = photoIndex)
        setCurrentAlbumManagementUseCase(updatedAlbum)
    }
}