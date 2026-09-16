package com.steurendo.bordo.domain.repository

import com.steurendo.bordo.domain.model.Album

interface AlbumManagementRepository {
    fun setAlbum(album: Album)
    fun getAlbum(): Album
}