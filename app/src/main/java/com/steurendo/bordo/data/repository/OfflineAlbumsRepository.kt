package com.steurendo.bordo.data.repository

import com.steurendo.bordo.data.db.dao.AlbumDao
import com.steurendo.bordo.data.db.entities.AlbumEntity
import com.steurendo.bordo.data.mapper.toData
import com.steurendo.bordo.data.mapper.toDomain
import com.steurendo.bordo.domain.model.Album
import com.steurendo.bordo.domain.repository.AlbumsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/*
Repository used for the interactions DB-Application related to the albums inside the DB
*/

class OfflineAlbumsRepository @Inject constructor(private val albumDao: AlbumDao) :
    AlbumsRepository {
    override suspend fun upsertAlbum(album: Album) = albumDao.upsert(album.toData())
    override suspend fun deleteAlbum(album: Album) = albumDao.delete(album.toData())
    override fun getAlbumStream(id: Int): Flow<Album> =
        albumDao.getAlbum(id).map(AlbumEntity::toDomain)

    override fun getAllAlbumsStream(): Flow<List<Album>> = albumDao.getAllAlbums().map {
        it.map(AlbumEntity::toDomain)
    }
}