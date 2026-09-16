package com.steurendo.bordo.domain.usecases.albums

import com.steurendo.bordo.domain.model.Album
import com.steurendo.bordo.domain.repository.AlbumsRepository
import javax.inject.Inject

class UpsertAlbumUseCase @Inject constructor(
    private val albumsRepository: AlbumsRepository
) {
    suspend operator fun invoke(album: Album) {
        albumsRepository.upsertAlbum(album)
    }
}