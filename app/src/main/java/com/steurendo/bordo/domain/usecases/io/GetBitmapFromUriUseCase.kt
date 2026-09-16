package com.steurendo.bordo.domain.usecases.io

import android.graphics.Bitmap
import android.net.Uri
import com.steurendo.bordo.domain.repository.IORepository
import javax.inject.Inject

class GetBitmapFromUriUseCase @Inject constructor(
    private val ioRepository: IORepository
) {
    operator fun invoke(uri: Uri): Bitmap {
        return ioRepository.getBitmapFromUri(uri)
    }
}