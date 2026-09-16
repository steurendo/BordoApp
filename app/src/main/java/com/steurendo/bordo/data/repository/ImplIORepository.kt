package com.steurendo.bordo.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.util.Size
import androidx.core.net.toUri
import androidx.exifinterface.media.ExifInterface
import com.steurendo.bordo.domain.repository.IORepository
import com.steurendo.bordo.utils.Logger
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.util.UUID
import javax.inject.Inject

class ImplIORepository @Inject constructor(
    @param:ApplicationContext private val context: Context
) : IORepository {
    private val contentResolver = context.contentResolver

    override fun getBitmapFromUri(uri: Uri): Bitmap {
        val src = ImageDecoder.createSource(contentResolver, uri)
        val bitmap = ImageDecoder.decodeBitmap(src).copy(Bitmap.Config.ARGB_8888, false)
        Logger.d(
            "Bitmap obtained (Uri: ${uri}).\nWidth: ${bitmap.width}; Height: ${bitmap.height}",
            customTag = "getBitmapFromUri"
        )
        return bitmap
    }

    override fun getImageDimensions(uri: Uri): Size {
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        contentResolver.openInputStream(uri).use {
            BitmapFactory.decodeStream(it, null, options)
        }
        val rawWidth = options.outWidth
        val rawHeight = options.outHeight
        if (rawWidth <= 0 || rawHeight <= 0) {
            Logger.e("Unable to read image dimensions (Uri: ${uri}).", customTag = "getImageDimensions")
            return Size(rawWidth, rawHeight)
        }

        // BitmapFactory reports the dimensions of the stored pixels and ignores the EXIF
        // orientation tag, while ImageDecoder/Coil apply it when decoding. For images that are
        // stored rotated the two would not be the same, so the EXIF orientation is applied here.
        val swapDimensions = hasSwappedExifOrientation(uri)
        val size =
            if (swapDimensions) Size(rawHeight, rawWidth)
            else Size(rawWidth, rawHeight)

        Logger.d(
            "Image dimensions obtained (Uri: ${uri}).\n" +
                    "Width: ${size.width}; Height: ${size.height} " +
                    "(raw: ${rawWidth}x${rawHeight}; exif swap: ${swapDimensions})",
            customTag = "getImageDimensions"
        )
        return size
    }

    /**
     * Returns true when the EXIF orientation of the image implies a 90° or 270° rotation,
     * i.e. when the decoded bitmap has width and height swapped with respect to the stored pixels.
     */
    private fun hasSwappedExifOrientation(uri: Uri): Boolean {
        return try {
            contentResolver.openInputStream(uri)?.use { inputStream ->
                val orientation = ExifInterface(inputStream).getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )
                when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_90,
                    ExifInterface.ORIENTATION_ROTATE_270,
                    ExifInterface.ORIENTATION_TRANSPOSE,
                    ExifInterface.ORIENTATION_TRANSVERSE -> true

                    else -> false
                }
            } ?: false
        } catch (e: Exception) {
            Logger.e(
                "Unable to read the EXIF orientation (Uri: ${uri}): ${e.message}",
                customTag = "getImageDimensions"
            )
            false
        }
    }

    override fun savePhotoToLocal(photoUri: Uri): Uri {
        val fileName = UUID.randomUUID().toString() + ".jpg"
        val inputStream = context.contentResolver.openInputStream(photoUri)
        val outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        val fileUri = File(context.filesDir, fileName).toUri()
        return fileUri
    }

    override fun deletePhotoFromLocal(photoUri: Uri): Boolean {
        val result = photoUri.path?.let { File(it).delete() } ?: false
        Logger.d("Deleting: ${if (result) "success" else "failed"}")
        return result
    }
}