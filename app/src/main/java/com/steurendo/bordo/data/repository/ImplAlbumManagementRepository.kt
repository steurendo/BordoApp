package com.steurendo.bordo.data.repository

import com.steurendo.bordo.domain.model.Album
import com.steurendo.bordo.domain.repository.AlbumManagementRepository
import javax.inject.Inject

/*
Repository used for the interactions inside the application related to the album inside the DB
*/

class ImplAlbumManagementRepository @Inject constructor() : AlbumManagementRepository {
    private lateinit var album: Album

    override fun setAlbum(album: Album) {
        this.album = album
    }

    override fun getAlbum(): Album = album
}