package com.steurendo.bordo.data.repository

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.provider.MediaStore
import com.steurendo.bordo.domain.repository.GalleryRepository
import com.steurendo.bordo.utils.Logger
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class ImplGalleryRepository @Inject constructor(
    @ApplicationContext context: Context
) : GalleryRepository {
    private val contentResolver = context.contentResolver

    companion object {
        private const val USE_DCIM = true
        private const val WORKING_FOLDER_NAME = "Bordo"
        private val WORKING_DIRECTORY =
            File(
                Environment.getExternalStoragePublicDirectory(if (USE_DCIM) Environment.DIRECTORY_DCIM else Environment.DIRECTORY_PICTURES),
                WORKING_FOLDER_NAME
            )
    }

    override suspend fun prepareAlbumDirectory(albumName: String): Boolean {
        Logger.setTag("generateAlbumToGallery")
        val workingDir = WORKING_DIRECTORY
        workingDir.exists().let { existing ->
            Logger.i("Working directory \"${workingDir.name}\": ${if (existing) "existing" else "not existing"}")
            if (!existing) {
                workingDir.mkdir()
                if (workingDir.exists())
                    Logger.d("Initializing working directory \"${WORKING_FOLDER_NAME}\": success")
                else
                    Logger.e("Initializing working directory \"${WORKING_FOLDER_NAME}\": failed")
            }
        }
        val albumDir = File(workingDir, albumName)
        var success = true
        albumDir.exists().let { existing ->
            Logger.i("Album directory (${albumDir.name}): ${if (existing) "existing. Deleting..." else "not existing"}")
            if (existing) albumDir.deleteRecursively()
            albumDir.mkdir()
            if (albumDir.exists())
                Logger.d("Initializing album directory (${albumName}): success")
            else {
                Logger.e("Initializing album directory (${albumName}): failed")
                success = false
            }
        }
        Logger.restoreTag()
        return success
    }

    override suspend fun savePhoto(albumName: String, fileName: String, photo: Bitmap): Boolean {
        return saveBitmapImage(
            contentResolver = contentResolver,
            bitmap = photo,
            albumName = albumName,
            fileName = fileName
        )
    }

    private fun saveBitmapImage(
        contentResolver: ContentResolver,
        bitmap: Bitmap,
        albumName: String,
        fileName: String
    ): Boolean {
        Logger.setTag("saveBitmapImage")
        Logger.i("Saving new image \"$fileName\" into album \"$albumName\"")
        val timestamp = System.currentTimeMillis()

        // Tell the media scanner about the new file so that it is immediately available to the user.
        val values = ContentValues()
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        values.put(MediaStore.Images.Media.DATE_ADDED, timestamp)
        values.put(MediaStore.Images.Media.DATE_TAKEN, timestamp)
        val relativePath =
            (if (USE_DCIM) "DCIM" else "Pictures") + "/${WORKING_FOLDER_NAME}/$albumName"
        values.put(MediaStore.Images.Media.RELATIVE_PATH, relativePath)
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
        values.put(MediaStore.Images.Media.IS_PENDING, true)
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        if (uri == null) {
            Logger.e("Failed to create new uri for image \"$fileName\"")
            Logger.restoreTag()
            return false
        }
        contentResolver.openOutputStream(uri)?.use { os ->
            Logger.i("Compressing bitmap into jpeg")
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
        }
        values.put(MediaStore.Images.Media.IS_PENDING, false)
        contentResolver.update(uri, values, null, null)
        Logger.restoreTag()
        return true
    }
}