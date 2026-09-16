package com.steurendo.bordo.domain.usecases.albums

import android.net.Uri
import com.steurendo.bordo.domain.model.AlbumPhoto
import com.steurendo.bordo.domain.repository.AlbumsRepository
import com.steurendo.bordo.domain.repository.IORepository
import com.steurendo.bordo.domain.usecases.io.GetImageDimensionsUseCase
import com.steurendo.bordo.utils.Logger
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AddPhotosToAlbumUseCase @Inject constructor(
    private val ioRepository: IORepository,
    private val albumsRepository: AlbumsRepository,
    private val getImageDimensionsUseCase: GetImageDimensionsUseCase
) {
    suspend operator fun invoke(albumId: Int, uriPhotos: List<Uri>) {
        val album = albumsRepository.getAlbumStream(albumId).first()
        Logger.i("Adding ${uriPhotos.size} photos into album '${album.name}'", "addPhotosToAlbum")
        val newPhotos = uriPhotos.map { uriPhoto ->
            val photoSize = getImageDimensionsUseCase.invoke(uriPhoto)
            val newUri = ioRepository.savePhotoToLocal(uriPhoto)
            return@map AlbumPhoto(
                uriString = newUri.toString(),
                width = photoSize.width,
                height = photoSize.height
            )
        }
        val updatedAlbum = album.copy(photos = album.photos + newPhotos)
        albumsRepository.upsertAlbum(updatedAlbum)
    }
}