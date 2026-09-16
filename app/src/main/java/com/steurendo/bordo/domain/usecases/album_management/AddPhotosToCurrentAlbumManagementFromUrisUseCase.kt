package com.steurendo.bordo.domain.usecases.album_management

import android.net.Uri
import com.steurendo.bordo.domain.model.AlbumPhoto
import com.steurendo.bordo.domain.usecases.io.GetImageDimensionsUseCase
import javax.inject.Inject

class AddPhotosToCurrentAlbumManagementFromUrisUseCase @Inject constructor(
    private val getCurrentAlbumManagementUseCase: GetCurrentAlbumManagementUseCase,
    private val setCurrentAlbumManagementUseCase: SetCurrentAlbumManagementUseCase,
    private val getImageDimensionsUseCase: GetImageDimensionsUseCase
) {
    operator fun invoke(uriPhotos: List<Uri>) {
        val album = getCurrentAlbumManagementUseCase.invoke()
        val currentPhotos = album.photos
        val currentPhotosUri = currentPhotos.map { it.uriString }
        val newPhotos: List<AlbumPhoto> = uriPhotos
            .filter { it.toString() !in currentPhotosUri }
            .map { uriPhoto ->
                val photoSize = getImageDimensionsUseCase.invoke(uri = uriPhoto)
                return@map AlbumPhoto(
                    uriString = uriPhoto.toString(),
                    width = photoSize.width,
                    height = photoSize.height
                )
            }
        val updatedAlbum = album.copy(photos = currentPhotos + newPhotos)
        setCurrentAlbumManagementUseCase.invoke(updatedAlbum)
    }
}