package com.steurendo.bordo.domain.repository

import android.graphics.Bitmap

interface GalleryRepository {
    suspend fun prepareAlbumDirectory(albumName: String): Boolean
    suspend fun savePhoto(albumName: String, fileName: String, photo: Bitmap): Boolean
}