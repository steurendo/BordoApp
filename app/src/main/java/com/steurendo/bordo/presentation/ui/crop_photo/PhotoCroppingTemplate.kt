package com.steurendo.bordo.presentation.ui.crop_photo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.steurendo.bordo.R
import com.steurendo.bordo.domain.model.CropMask
import com.steurendo.bordo.presentation.common.mock.testPhotos
import com.steurendo.bordo.presentation.common.shared_models.AlbumPhotoUiModel
import com.steurendo.bordo.presentation.common.shared_modules.PinchCornersReactor
import com.steurendo.bordo.presentation.common.shared_modules.PinchMove
import com.steurendo.bordo.presentation.theme.BordoAppTheme
import com.steurendo.bordo.presentation.theme.PreviewTheme
import com.steurendo.bordo.presentation.ui.crop_photo.components.BottomBar
import com.steurendo.bordo.presentation.ui.crop_photo.components.PhotoCropper
import com.steurendo.bordo.presentation.ui.crop_photo.components.TopBar


@Composable
fun PhotoCroppingTemplate(
    onClickBack: () -> Unit,
    onClickRestore: () -> Unit,
    onClickSave: () -> Unit,
    onChangeCroppingMode: (PhotoCroppingMode) -> Unit,
    pinchCornersReactor: PinchCornersReactor,
    moveCropMask: PinchMove,
    photo: AlbumPhotoUiModel,
    croppingMode: PhotoCroppingMode,
    cropMask: CropMask
) {
    Scaffold(
        topBar = {
            TopBar(
                onClickBack = onClickBack,
                onClickRestore = onClickRestore,
                onClickSave = onClickSave
            )
        },
        bottomBar = {
            BottomBar(
                onChangeCroppingMode = onChangeCroppingMode,
                croppingMode = croppingMode
            )
        }
    ) { innerPadding ->
        PhotoCroppingScreenContent(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            pinchCornersReactor = pinchCornersReactor,
            moveCropMask = moveCropMask,
            photo = photo,
            cropMask = cropMask,
            pinchAvailableOnBorders = croppingMode == PhotoCroppingMode.FreeTransform
        )
    }
}

@Composable
private fun PhotoCroppingScreenContent(
    modifier: Modifier = Modifier,
    pinchCornersReactor: PinchCornersReactor,
    moveCropMask: PinchMove,
    photo: AlbumPhotoUiModel,
    cropMask: CropMask,
    pinchAvailableOnBorders: Boolean
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        PhotoCropper(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_large)),
            photo = photo,
            cropMask = cropMask,
            pinchCornersReactor = pinchCornersReactor,
            pinchAvailableOnBorders = pinchAvailableOnBorders,
            moveCropMask = moveCropMask
        )
    }
}


@PreviewTheme
@Composable
fun PhotoCroppingPreview() {
    val photo = testPhotos[1]
    val cropMask = CropMask(left = 0.4f, right = 0.05f, top = 0.2f, bottom = 0.1f)

    BordoAppTheme {
        PhotoCroppingTemplate(
            onClickBack = {},
            onClickRestore = {},
            onClickSave = {},
            onChangeCroppingMode = {},
            pinchCornersReactor = PinchCornersReactor(
                onPinchLeftTop = { _, _ -> },
                onPinchRightTop = { _, _ -> },
                onPinchBottomRight = { _, _ -> },
                onPinchBottomLeft = { _, _ -> },
                onPinchLeft = { _, _ -> },
                onPinchTop = { _, _ -> },
                onPinchRight = { _, _ -> },
                onPinchBottom = { _, _ -> },
            ),
            moveCropMask = { _, _ -> },
            photo = photo,
            croppingMode = PhotoCroppingMode.FreeTransform,
            cropMask = cropMask
        )
    }
}