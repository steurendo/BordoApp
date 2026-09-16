package com.steurendo.bordo.domain.usecases.albums

import androidx.core.net.toUri
import com.steurendo.bordo.domain.model.Album
import com.steurendo.bordo.domain.repository.IORepository
import java.util.Date
import javax.inject.Inject

class PersistAlbumUseCase @Inject constructor(
    private val ioRepository: IORepository,
    private val upsertAlbumUseCase: UpsertAlbumUseCase
) {
    suspend operator fun invoke(album: Album) {
        val updatedAlbum = album.copy(
            photos = album.photos.map { photo ->
                val newUri = ioRepository.savePhotoToLocal(photoUri = photo.uriString.toUri())
                return@map photo.copy(uriString = newUri.toString())
            },
            creationDate = Date()
        )
        upsertAlbumUseCase.invoke(updatedAlbum)
    }
}