package com.steurendo.bordo.domain.usecases.album_management

import javax.inject.Inject

class RemovePhotoFromCurrentAlbumManagementUseCase @Inject constructor(
    private val getCurrentAlbumManagementUseCase: GetCurrentAlbumManagementUseCase,
    private val setCurrentAlbumManagementUseCase: SetCurrentAlbumManagementUseCase,
) {
    operator fun invoke(photoIndex: Int) {
        val album = getCurrentAlbumManagementUseCase.invoke()
        val updatedPhotos = album.photos.filterIndexed { index, _ -> index != photoIndex }
        val updatedAlbum = album.copy(
            photos = updatedPhotos,
            referencePhotoIndex = if (photoIndex < album.referencePhotoIndex) album.referencePhotoIndex - 1 else album.referencePhotoIndex
        )
        setCurrentAlbumManagementUseCase.invoke(updatedAlbum)
    }
}