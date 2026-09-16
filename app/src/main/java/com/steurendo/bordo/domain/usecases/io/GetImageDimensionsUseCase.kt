package com.steurendo.bordo.domain.usecases.io

import android.net.Uri
import android.util.Size
import com.steurendo.bordo.domain.repository.IORepository
import javax.inject.Inject

class GetImageDimensionsUseCase @Inject constructor(
    private val ioRepository: IORepository
) {
    operator fun invoke(uri: Uri): Size {
        return ioRepository.getImageDimensions(uri)
    }
}