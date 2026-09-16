package com.steurendo.bordo.domain.usecases.albums

import com.steurendo.bordo.domain.model.Album
import com.steurendo.bordo.domain.repository.AlbumsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllAlbumsUseCase @Inject constructor(
    private val albumsRepository: AlbumsRepository
) {
    operator fun invoke(): Flow<List<Album>> {
        return albumsRepository.getAllAlbumsStream()
    }
}