package com.steurendo.bordo.domain.usecases.albums

import com.steurendo.bordo.domain.repository.AlbumsRepository
import com.steurendo.bordo.utils.Logger
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RemovePhotoFromAlbumUseCase @Inject constructor(
    private val albumsRepository: AlbumsRepository
) {
    suspend operator fun invoke(albumId: Int, photoIndex: Int) {
        val album = albumsRepository.getAlbumStream(albumId).first()
        Logger.i(
            "Removing a photo from album '${album.name}'",
            "removePhotoFromAlbum"
        )
        val updatedPhotos = album.photos.filterIndexed { index, _ -> index != photoIndex }
        val updatedReferencePhotoIndex =
            if (photoIndex < album.referencePhotoIndex) album.referencePhotoIndex - 1 else album.referencePhotoIndex
        val updatedAlbum = album.copy(
            photos = updatedPhotos,
            referencePhotoIndex = updatedReferencePhotoIndex
        )
        albumsRepository.upsertAlbum(updatedAlbum)
    }
}