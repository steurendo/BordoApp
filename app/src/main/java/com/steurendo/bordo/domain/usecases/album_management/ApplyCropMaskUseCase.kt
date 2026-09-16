package com.steurendo.bordo.domain.usecases.album_management

import com.steurendo.bordo.domain.model.CropMask
import javax.inject.Inject

class ApplyCropMaskUseCase @Inject constructor(
    private val getCurrentAlbumManagementUseCase: GetCurrentAlbumManagementUseCase,
    private val setCurrentAlbumManagementUseCase: SetCurrentAlbumManagementUseCase
) {
    operator fun invoke(photoIndex: Int, cropMask: CropMask) {
        val album = getCurrentAlbumManagementUseCase.invoke()
        val updatedPhoto = album.photos[photoIndex].copy(cropMask = cropMask)
        val updatedPhotos = album.photos.mapIndexed { index, photo -> if (index == photoIndex) updatedPhoto else photo }
        val updatedAlbum = album.copy(photos = updatedPhotos)
        setCurrentAlbumManagementUseCase.invoke(album = updatedAlbum)
    }
}