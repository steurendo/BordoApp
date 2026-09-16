package com.steurendo.bordo.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.steurendo.bordo.data.db.entities.AlbumEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {
    @Upsert
    suspend fun upsert(albumEntity: AlbumEntity)

    @Delete
    suspend fun delete(albumEntity: AlbumEntity)

    @Query("SELECT * from albums WHERE id = :id")
    fun getAlbum(id: Int): Flow<AlbumEntity>

    @Query("SELECT * from albums ORDER BY creationDate DESC")
    fun getAllAlbums(): Flow<List<AlbumEntity>>
}