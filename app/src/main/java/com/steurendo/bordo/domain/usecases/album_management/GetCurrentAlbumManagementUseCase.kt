package com.steurendo.bordo.domain.usecases.album_management

import com.steurendo.bordo.domain.model.Album
import com.steurendo.bordo.domain.repository.AlbumManagementRepository
import javax.inject.Inject

class GetCurrentAlbumManagementUseCase @Inject constructor(
    private val albumManagementRepository: AlbumManagementRepository
) {
    operator fun invoke(): Album {
        return albumManagementRepository.getAlbum()
    }
}