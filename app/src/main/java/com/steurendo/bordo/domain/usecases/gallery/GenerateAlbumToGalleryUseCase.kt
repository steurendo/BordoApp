package com.steurendo.bordo.domain.usecases.gallery

import androidx.core.net.toUri
import com.steurendo.bordo.domain.model.Album
import com.steurendo.bordo.domain.model.computedAspectRatio
import com.steurendo.bordo.domain.model.getReferencePhoto
import com.steurendo.bordo.domain.repository.GalleryRepository
import com.steurendo.bordo.domain.repository.IORepository
import com.steurendo.bordo.presentation.common.shared_modules.EffectParamsParser
import com.steurendo.bordo.utils.ImagePadding
import com.steurendo.bordo.utils.crop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GenerateAlbumToGalleryUseCase @Inject constructor(
    private val ioRepository: IORepository,
    private val galleryRepository: GalleryRepository
) {
    suspend operator fun invoke(album: Album): Boolean = withContext(Dispatchers.Default) {
        val albumAspectRatio = album.getReferencePhoto().computedAspectRatio
        val effectParams = EffectParamsParser.parseTo(album.effectParamsParser)

        if (!galleryRepository.prepareAlbumDirectory(album.name))
            return@withContext false

        var success = true
        album.photos.forEachIndexed { index, photo ->
            val uri = photo.uriString.toUri()
            val originalBitmap = ioRepository.getBitmapFromUri(uri)
            val croppedBitmap = originalBitmap.crop(photo.cropMask)
            if (croppedBitmap !== originalBitmap) originalBitmap.recycle()

            val outBitmap = ImagePadding.getPaddedImage(
                image = croppedBitmap,
                outputAspectRatio = albumAspectRatio,
                effect = album.paddingEffect,
                params = effectParams
            )

            val fileName = "${album.name}_${index + 1}.jpg"
            val saved = galleryRepository.savePhoto(
                albumName = album.name,
                fileName = fileName,
                photo = outBitmap
            )
            if (!saved) success = false

            if (croppedBitmap !== outBitmap) croppedBitmap.recycle()
            outBitmap.recycle()
        }
        
        return@withContext success
    }
}