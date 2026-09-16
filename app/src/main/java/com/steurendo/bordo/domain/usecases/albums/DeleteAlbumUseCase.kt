package com.steurendo.bordo.domain.usecases.albums

import androidx.core.net.toUri
import com.steurendo.bordo.domain.repository.AlbumsRepository
import com.steurendo.bordo.domain.repository.IORepository
import com.steurendo.bordo.utils.Logger
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DeleteAlbumUseCase @Inject constructor(
    private val albumsRepository: AlbumsRepository,
    private val ioRepository: IORepository
) {
    suspend operator fun invoke(albumId: Int) {
        val album = albumsRepository.getAlbumStream(albumId).first()
        Logger.i("Deleting album '${album.name}'", "deleteAlbum")
        album.photos.forEach { ioRepository.deletePhotoFromLocal(it.uriString.toUri()) }
        albumsRepository.deleteAlbum(album)
    }
}