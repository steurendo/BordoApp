package com.steurendo.bordo.domain.repository

import android.graphics.Bitmap
import android.net.Uri
import android.util.Size

interface IORepository {
    fun getBitmapFromUri(uri: Uri): Bitmap
    fun getImageDimensions(uri: Uri): Size
    fun savePhotoToLocal(photoUri: Uri): Uri
    fun deletePhotoFromLocal(photoUri: Uri): Boolean
}