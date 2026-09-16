package com.steurendo.bordo.domain.usecases.albums

import com.steurendo.bordo.domain.repository.AlbumsRepository
import com.steurendo.bordo.utils.Logger
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RenameAlbumUseCase @Inject constructor(
    private val albumsRepository: AlbumsRepository
) {
    suspend operator fun invoke(albumId: Int, newName: String) {
        val album = albumsRepository.getAlbumStream(albumId).first()
        Logger.i("Renaming album '${album.name}' into '$newName'", "renameAlbum")
        val updatedAlbum = album.copy(name = newName)
        albumsRepository.upsertAlbum(updatedAlbum)
    }
}