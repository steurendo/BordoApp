package com.steurendo.bordo.domain.repository

import com.steurendo.bordo.domain.model.Album
import kotlinx.coroutines.flow.Flow

interface AlbumsRepository {
    suspend fun upsertAlbum(album: Album)
    suspend fun deleteAlbum(album: Album)
    fun getAlbumStream(id: Int): Flow<Album>
    fun getAllAlbumsStream(): Flow<List<Album>>
}