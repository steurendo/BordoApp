package com.steurendo.bordo.presentation.ui.crop_photo

import androidx.lifecycle.ViewModel
import com.steurendo.bordo.domain.model.CropMask
import com.steurendo.bordo.domain.model.computedAspectRatio
import com.steurendo.bordo.domain.model.getReferencePhoto
import com.steurendo.bordo.domain.usecases.album_management.ApplyCropMaskUseCase
import com.steurendo.bordo.domain.usecases.album_management.GetCurrentAlbumManagementUseCase
import com.steurendo.bordo.presentation.common.shared_models.aspectRatio
import com.steurendo.bordo.presentation.common.shared_models.computedAspectRatio
import com.steurendo.bordo.presentation.common.shared_models.getPhoto
import com.steurendo.bordo.presentation.common.shared_models.getReferencePhoto
import com.steurendo.bordo.presentation.mapper.toUiModel
import com.steurendo.bordo.presentation.navigation.destinations.PhotoCroppingDestination
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.abs

enum class PhotoCroppingMode {
    FreeTransform,
    ReferencePhoto
}

const val maxCroppingSize: Float = 0.8f

@HiltViewModel(assistedFactory = PhotoCroppingViewModel.Factory::class)
class PhotoCroppingViewModel @AssistedInject constructor(
    // Album management
    getCurrentAlbumManagementUseCase: GetCurrentAlbumManagementUseCase,
    private val applyCropMaskUseCase: ApplyCropMaskUseCase,
    //Params
    @Assisted val navKey: PhotoCroppingDestination
) : ViewModel() {
    @AssistedFactory
    interface Factory {
        fun create(navKey: PhotoCroppingDestination): PhotoCroppingViewModel
    }

    private val _uiState = MutableStateFlow(PhotoCroppingUiState())
    val uiState: StateFlow<PhotoCroppingUiState> = _uiState.asStateFlow()

    val referenceAspectRatio: Float

    init {
        val album = getCurrentAlbumManagementUseCase.invoke()
        val photoIndex = navKey.photoIndex
        referenceAspectRatio = album.getReferencePhoto().computedAspectRatio
        _uiState.update {
            PhotoCroppingUiState(
                album = album.toUiModel(),
                photoIndex = photoIndex,
                newCropMask = album.photos[photoIndex].cropMask
            )
        }
    }

    private fun coerciveValueLeft(croppingMode: PhotoCroppingMode): Float {
        if (croppingMode == PhotoCroppingMode.FreeTransform)
            return maxCroppingSize - _uiState.value.newCropMask.right
        return 1f
    }

    private fun coerciveValueRight(croppingMode: PhotoCroppingMode): Float {
        if (croppingMode == PhotoCroppingMode.FreeTransform)
            return maxCroppingSize - _uiState.value.newCropMask.left
        return 1f
    }

    private fun coerciveValueTop(croppingMode: PhotoCroppingMode): Float {
        if (croppingMode == PhotoCroppingMode.FreeTransform)
            return maxCroppingSize - _uiState.value.newCropMask.bottom
        return 1f
    }

    private fun coerciveValueBottom(croppingMode: PhotoCroppingMode): Float {
        if (croppingMode == PhotoCroppingMode.FreeTransform)
            return maxCroppingSize - _uiState.value.newCropMask.top
        return 1f
    }

    fun pinchLeftTop(offsetX: Float, offsetY: Float) = _uiState.update {
        if (it.croppingMode == PhotoCroppingMode.FreeTransform)
            return@update pinchLeftTopFreeTransform(offsetX, offsetY, it)
        else
            return@update pinchLeftTopReference(offsetX, offsetY, it)
    }

    private fun pinchLeftTopFreeTransform(
        offsetX: Float,
        offsetY: Float,
        state: PhotoCroppingUiState
    ): PhotoCroppingUiState {
        return state.copy(
            newCropMask = state.newCropMask.copy(
                left = (state.newCropMask.left + offsetX).coerceIn(0f, coerciveValueLeft(state.croppingMode)),
                top = (state.newCropMask.top + offsetY).coerceIn(0f, coerciveValueTop(state.croppingMode))
            )
        )
    }

    private fun pinchLeftTopReference(
        offsetX: Float,
        offsetY: Float,
        state: PhotoCroppingUiState
    ): PhotoCroppingUiState {
        val selectedPhoto = state.album.getPhoto(state.photoIndex)
        val amountX = offsetX * selectedPhoto.width
        val amountY = offsetY * selectedPhoto.height
        val y = (amountX + amountY) / (referenceAspectRatio + 1)
        val x = referenceAspectRatio * y
        var left = state.newCropMask.left + x / selectedPhoto.width
        var top = state.newCropMask.top + y / selectedPhoto.height
        if (left !in 0f..coerciveValueLeft(state.croppingMode) && top !in 0f..coerciveValueTop(state.croppingMode)) {
            val deltaLeft = abs(left.coerceIn(0f, coerciveValueLeft(state.croppingMode)) - left)
            val deltaTop = abs(top.coerceIn(0f, coerciveValueTop(state.croppingMode)) - top)
            if (deltaLeft > deltaTop) { // if space to occupy is bigger on x-axis, crop state
                left = left.coerceIn(0f, coerciveValueLeft(state.croppingMode))
                top =
                    state.newCropMask.top + (left - state.newCropMask.left) / referenceAspectRatio * selectedPhoto.aspectRatio
            } else {
                top = top.coerceIn(0f, coerciveValueTop(state.croppingMode))
                left =
                    state.newCropMask.left + (top - state.newCropMask.top) * referenceAspectRatio / selectedPhoto.aspectRatio
            }
        } else if (left !in 0f..coerciveValueLeft(state.croppingMode)) {
            left = left.coerceIn(0f, coerciveValueLeft(state.croppingMode))
            top = state.newCropMask.top + (left - state.newCropMask.left) / referenceAspectRatio
        } else if (top !in 0f..coerciveValueTop(state.croppingMode)) {
            top = top.coerceIn(0f, coerciveValueTop(state.croppingMode))
            left = state.newCropMask.left + (top - state.newCropMask.top) * referenceAspectRatio
        }
        return state.copy(newCropMask = state.newCropMask.copy(left = left, top = top))
    }

    fun pinchRightTop(offsetX: Float, offsetY: Float) = _uiState.update {
        if (it.croppingMode == PhotoCroppingMode.FreeTransform)
            return@update pinchRightTopFreeTransform(offsetX, offsetY, it)
        else
            return@update pinchRightTopReference(offsetX, offsetY, it)
    }

    private fun pinchRightTopFreeTransform(
        offsetX: Float,
        offsetY: Float,
        state: PhotoCroppingUiState
    ): PhotoCroppingUiState {
        return state.copy(
            newCropMask = state.newCropMask.copy(
                right = (state.newCropMask.right - offsetX).coerceIn(0f, coerciveValueRight(state.croppingMode)),
                top = (state.newCropMask.top + offsetY).coerceIn(0f, coerciveValueTop(state.croppingMode))
            )
        )
    }

    private fun pinchRightTopReference(
        offsetX: Float,
        offsetY: Float,
        state: PhotoCroppingUiState
    ): PhotoCroppingUiState {
        val selectedPhoto = state.album.getPhoto(state.photoIndex)
        val amountX = -offsetX * selectedPhoto.width
        val amountY = offsetY * selectedPhoto.height
        val y = (amountX + amountY) / (referenceAspectRatio + 1)
        val x = referenceAspectRatio * y
        var right = state.newCropMask.right + x / selectedPhoto.width
        var top = state.newCropMask.top + y / selectedPhoto.height
        if (right !in 0f..coerciveValueRight(state.croppingMode) && top !in 0f..coerciveValueTop(state.croppingMode)) {
            val deltaRight = abs(right.coerceIn(0f, coerciveValueRight(state.croppingMode)) - right)
            val deltaTop = abs(top.coerceIn(0f, coerciveValueTop(state.croppingMode)) - top)
            if (deltaRight > deltaTop) { // if space to occupy is bigger on x-axis, crop state
                right = right.coerceIn(0f, coerciveValueRight(state.croppingMode))
                top =
                    state.newCropMask.top + (right - state.newCropMask.right) / referenceAspectRatio * selectedPhoto.aspectRatio
            } else {
                top = top.coerceIn(0f, coerciveValueTop(state.croppingMode))
                right =
                    state.newCropMask.right + (top - state.newCropMask.top) * referenceAspectRatio / selectedPhoto.aspectRatio
            }
        } else if (right !in 0f..coerciveValueRight(state.croppingMode)) {
            right = right.coerceIn(0f, coerciveValueRight(state.croppingMode))
            top = state.newCropMask.top + (right - state.newCropMask.right) / referenceAspectRatio
        } else if (top !in 0f..coerciveValueTop(state.croppingMode)) {
            top = top.coerceIn(0f, coerciveValueTop(state.croppingMode))
            right = state.newCropMask.right + (top - state.newCropMask.top) * referenceAspectRatio
        }
        return state.copy(newCropMask = state.newCropMask.copy(right = right, top = top))
    }

    fun pinchBottomRight(offsetX: Float, offsetY: Float) = _uiState.update {
        if (it.croppingMode == PhotoCroppingMode.FreeTransform)
            return@update pinchBottomRightFreeTransform(offsetX, offsetY, it)
        else
            return@update pinchBottomRightReference(offsetX, offsetY, it)
    }

    private fun pinchBottomRightFreeTransform(
        offsetX: Float,
        offsetY: Float,
        state: PhotoCroppingUiState
    ): PhotoCroppingUiState {
        return state.copy(
            newCropMask = state.newCropMask.copy(
                right = (state.newCropMask.right - offsetX).coerceIn(0f, coerciveValueRight(state.croppingMode)),
                bottom = (state.newCropMask.bottom - offsetY).coerceIn(0f, coerciveValueBottom(state.croppingMode))
            )
        )
    }

    private fun pinchBottomRightReference(
        offsetX: Float,
        offsetY: Float,
        state: PhotoCroppingUiState
    ): PhotoCroppingUiState {
        val selectedPhoto = state.album.getPhoto(state.photoIndex)
        val amountX = -offsetX * selectedPhoto.width
        val amountY = -offsetY * selectedPhoto.height
        val y = (amountX + amountY) / (referenceAspectRatio + 1)
        val x = referenceAspectRatio * y
        var right = state.newCropMask.right + x / selectedPhoto.width
        var bottom = state.newCropMask.bottom + y / selectedPhoto.height
        if (right !in 0f..coerciveValueRight(state.croppingMode) && bottom !in 0f..coerciveValueBottom(state.croppingMode)) {
            val deltaRight = abs(right.coerceIn(0f, coerciveValueRight(state.croppingMode)) - right)
            val deltaBottom = abs(bottom.coerceIn(0f, coerciveValueBottom(state.croppingMode)) - bottom)
            if (deltaRight > deltaBottom) { // if space to occupy is bigger on x-axis, crop state
                right = right.coerceIn(0f, coerciveValueRight(state.croppingMode))
                bottom =
                    state.newCropMask.bottom + (right - state.newCropMask.right) / referenceAspectRatio * selectedPhoto.aspectRatio
            } else {
                bottom = bottom.coerceIn(0f, coerciveValueBottom(state.croppingMode))
                right =
                    state.newCropMask.right + (bottom - state.newCropMask.bottom) * referenceAspectRatio / selectedPhoto.aspectRatio
            }
        } else if (right !in 0f..coerciveValueRight(state.croppingMode)) {
            right = right.coerceIn(0f, coerciveValueRight(state.croppingMode))
            bottom =
                state.newCropMask.bottom + (right - state.newCropMask.right) / referenceAspectRatio
        } else if (bottom !in 0f..coerciveValueBottom(state.croppingMode)) {
            bottom = bottom.coerceIn(0f, coerciveValueBottom(state.croppingMode))
            right =
                state.newCropMask.right + (bottom - state.newCropMask.bottom) * referenceAspectRatio
        }
        return state.copy(newCropMask = state.newCropMask.copy(right = right, bottom = bottom))
    }

    fun pinchBottomLeft(offsetX: Float, offsetY: Float) = _uiState.update {
        if (it.croppingMode == PhotoCroppingMode.FreeTransform)
            return@update pinchBottomLeftFreeTransform(offsetX, offsetY, it)
        else
            return@update pinchBottomLeftReference(offsetX, offsetY, it)
    }

    private fun pinchBottomLeftFreeTransform(
        offsetX: Float,
        offsetY: Float,
        state: PhotoCroppingUiState
    ): PhotoCroppingUiState {
        return state.copy(
            newCropMask = state.newCropMask.copy(
                left = (state.newCropMask.left + offsetX).coerceIn(0f, coerciveValueLeft(state.croppingMode)),
                bottom = (state.newCropMask.bottom - offsetY).coerceIn(0f, coerciveValueBottom(state.croppingMode))
            )
        )
    }

    private fun pinchBottomLeftReference(
        offsetX: Float,
        offsetY: Float,
        state: PhotoCroppingUiState
    ): PhotoCroppingUiState {
        val selectedPhoto = state.album.getPhoto(state.photoIndex)
        val amountX = offsetX * selectedPhoto.width
        val amountY = -offsetY * selectedPhoto.height
        val y = (amountX + amountY) / (referenceAspectRatio + 1)
        val x = referenceAspectRatio * y
        var left = state.newCropMask.left + x / selectedPhoto.width
        var bottom = state.newCropMask.bottom + y / selectedPhoto.height
        if (left !in 0f..coerciveValueLeft(state.croppingMode) && bottom !in 0f..coerciveValueBottom(state.croppingMode)) {
            val deltaLeft = abs(left.coerceIn(0f, coerciveValueLeft(state.croppingMode)) - left)
            val deltaBottom = abs(bottom.coerceIn(0f, coerciveValueBottom(state.croppingMode)) - bottom)
            if (deltaLeft > deltaBottom) { // if space to occupy is bigger on x-axis, crop state
                left = left.coerceIn(0f, coerciveValueLeft(state.croppingMode))
                bottom =
                    state.newCropMask.bottom + (left - state.newCropMask.left) / referenceAspectRatio * selectedPhoto.aspectRatio
            } else {
                bottom = bottom.coerceIn(0f, coerciveValueBottom(state.croppingMode))
                left =
                    state.newCropMask.left + (bottom - state.newCropMask.bottom) * referenceAspectRatio / selectedPhoto.aspectRatio
            }
        } else if (left !in 0f..coerciveValueLeft(state.croppingMode)) {
            left = left.coerceIn(0f, coerciveValueLeft(state.croppingMode))
            bottom =
                state.newCropMask.bottom + (left - state.newCropMask.left) / referenceAspectRatio
        } else if (bottom !in 0f..coerciveValueBottom(state.croppingMode)) {
            bottom = bottom.coerceIn(0f, coerciveValueBottom(state.croppingMode))
            left =
                state.newCropMask.left + (bottom - state.newCropMask.bottom) * referenceAspectRatio
        }
        return state.copy(newCropMask = state.newCropMask.copy(left = left, bottom = bottom))
    }

    fun pinchLeft(offsetX: Float, state: PhotoCroppingUiState) {
        if (_uiState.value.croppingMode == PhotoCroppingMode.ReferencePhoto) return
        _uiState.update {
            it.copy(
                newCropMask = it.newCropMask.copy(
                    left = (it.newCropMask.left + offsetX).coerceIn(0f, coerciveValueLeft(state.croppingMode))
                )
            )
        }
    }

    fun pinchTop(offsetY: Float, state: PhotoCroppingUiState) {
        if (_uiState.value.croppingMode == PhotoCroppingMode.ReferencePhoto) return
        _uiState.update {
            it.copy(
                newCropMask = it.newCropMask.copy(
                    top = (it.newCropMask.top + offsetY).coerceIn(0f, coerciveValueTop(state.croppingMode))
                )
            )
        }
    }

    fun pinchRight(offsetX: Float, state: PhotoCroppingUiState) {
        if (_uiState.value.croppingMode == PhotoCroppingMode.ReferencePhoto) return
        _uiState.update {
            it.copy(
                newCropMask = it.newCropMask.copy(
                    right = (it.newCropMask.right - offsetX).coerceIn(0f, coerciveValueRight(state.croppingMode))
                )
            )
        }
    }

    fun pinchBottom(offsetY: Float, state: PhotoCroppingUiState) {
        if (_uiState.value.croppingMode == PhotoCroppingMode.ReferencePhoto) return
        _uiState.update {
            it.copy(
                newCropMask = it.newCropMask.copy(
                    bottom = (it.newCropMask.bottom - offsetY).coerceIn(0f, coerciveValueBottom(state.croppingMode))
                )
            )
        }
    }


    fun moveCropMask(offsetX: Float, offsetY: Float) = _uiState.update {
        val coerciveValueX = it.newCropMask.left + it.newCropMask.right
        val coerciveValueY = it.newCropMask.top + it.newCropMask.bottom
        it.copy(
            newCropMask = it.newCropMask.copy(
                left = (it.newCropMask.left + offsetX).coerceIn(0f, coerciveValueX),
                top = (it.newCropMask.top + offsetY).coerceIn(0f, coerciveValueY),
                right = (it.newCropMask.right - offsetX).coerceIn(0f, coerciveValueX),
                bottom = (it.newCropMask.bottom - offsetY).coerceIn(0f, coerciveValueY)
            )
        )
    }

    fun setCroppingMode(croppingMode: PhotoCroppingMode) {
        if (croppingMode == PhotoCroppingMode.ReferencePhoto)
            restoreCropMask(croppingMode)
        else _uiState.update { it.copy(croppingMode = croppingMode) }
    }

    fun restoreCropMask(croppingMode: PhotoCroppingMode? = null) =
        _uiState.update {
            val usedCroppingMode = croppingMode ?: it.croppingMode
            var newCropMask = CropMask()
            if (usedCroppingMode == PhotoCroppingMode.ReferencePhoto) {
                val selectedPhoto = it.album.getPhoto(it.photoIndex)
                val referenceAspectRatio = it.album.getReferencePhoto().computedAspectRatio
                if (selectedPhoto.aspectRatio < referenceAspectRatio) {
                    val border =
                        (1 - selectedPhoto.aspectRatio / referenceAspectRatio) / 2
                    newCropMask = CropMask(
                        top = border,
                        bottom = border
                    )
                } else {
                    val border =
                        (1 - referenceAspectRatio / selectedPhoto.aspectRatio) / 2
                    newCropMask = CropMask(
                        left = border,
                        right = border
                    )
                }
            }
            return@update it.copy(newCropMask = newCropMask, croppingMode = usedCroppingMode)
        }

    fun saveCropping() {
        applyCropMaskUseCase.invoke(
            photoIndex = _uiState.value.photoIndex,
            cropMask = _uiState.value.newCropMask
        )
    }
}