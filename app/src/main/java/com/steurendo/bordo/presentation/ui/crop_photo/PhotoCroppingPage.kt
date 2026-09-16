package com.steurendo.bordo.presentation.ui.crop_photo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.steurendo.bordo.presentation.common.shared_models.getPhoto
import com.steurendo.bordo.presentation.common.shared_modules.PinchCornersReactor

@Composable
fun PhotoCroppingPage(
    onClickBack: () -> Unit,
    onClickSave: () -> Unit,
    vm: PhotoCroppingViewModel
) {
    val uiState by vm.uiState.collectAsState()

    PhotoCroppingTemplate(
        onClickBack = onClickBack,
        onClickRestore = { vm.restoreCropMask() },
        onClickSave = {
            vm.saveCropping()
            onClickSave()
        },
        onChangeCroppingMode = { vm.setCroppingMode(it) },
        pinchCornersReactor = PinchCornersReactor(
            onPinchLeftTop = { x, y -> vm.pinchLeftTop(offsetX = x, offsetY = y) },
            onPinchRightTop = { x, y -> vm.pinchRightTop(offsetX = x, offsetY = y) },
            onPinchBottomRight = { x, y -> vm.pinchBottomRight(offsetX = x, offsetY = y) },
            onPinchBottomLeft = { x, y -> vm.pinchBottomLeft(offsetX = x, offsetY = y) },
            onPinchLeft = { x, _ -> vm.pinchLeft(offsetX = x, state = uiState) },
            onPinchTop = { _, y -> vm.pinchTop(offsetY = y, state = uiState) },
            onPinchRight = { x, _ -> vm.pinchRight(offsetX = x, state = uiState) },
            onPinchBottom = { _, y -> vm.pinchBottom(offsetY = y, state = uiState) },
        ),
        moveCropMask = { x, y -> vm.moveCropMask(offsetX = x, offsetY = y) },
        photo = uiState.album.getPhoto(uiState.photoIndex),
        croppingMode = uiState.croppingMode,
        cropMask = uiState.newCropMask
    )
}