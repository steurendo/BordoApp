package com.steurendo.bordo.domain.usecases.albums

import com.steurendo.bordo.domain.model.Album
import com.steurendo.bordo.domain.repository.AlbumsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAlbumUseCase @Inject constructor(
    private val albumsRepository: AlbumsRepository
) {
    operator fun invoke(albumId: Int): Flow<Album> {
        return albumsRepository.getAlbumStream(albumId)
    }
}