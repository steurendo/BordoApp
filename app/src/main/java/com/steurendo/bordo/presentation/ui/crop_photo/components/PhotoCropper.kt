package com.steurendo.bordo.presentation.ui.crop_photo.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.steurendo.bordo.domain.model.CropMask
import com.steurendo.bordo.presentation.common.mock.testPhotos
import com.steurendo.bordo.presentation.common.shared_models.AlbumPhotoUiModel
import com.steurendo.bordo.presentation.common.shared_models.aspectRatio
import com.steurendo.bordo.presentation.common.shared_modules.PinchCornersReactor
import com.steurendo.bordo.presentation.common.shared_modules.PinchMove


@Composable
fun PhotoCropper(
    modifier: Modifier = Modifier,
    photo: AlbumPhotoUiModel,
    cropMask: CropMask,
    pinchCornersReactor: PinchCornersReactor,
    pinchAvailableOnBorders: Boolean,
    moveCropMask: PinchMove
) {
    var width by remember { mutableFloatStateOf(0f) }
    var height by remember { mutableFloatStateOf(0f) }
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(photo.aspectRatio)
                .onGloballyPositioned {
                    width = it.size.width.toFloat()
                    height = it.size.height.toFloat()
                }
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = photo.uri,
                placeholder = photo.placeholderId?.let { painterResource(id = it) },
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
            Canvas(modifier = Modifier.fillMaxSize()) {
                clipRect(
                    left = cropMask.left * width,
                    right = (1 - cropMask.right) * width,
                    top = cropMask.top * height,
                    bottom = (1 - cropMask.bottom) * height,
                    clipOp = ClipOp.Difference
                ) {
                    drawRect(
                        color = Color(0xBB000000),
                        topLeft = Offset(0f, 0f),
                        size = Size(
                            width,
                            height
                        )
                    )
                }
            }
            CropFrame(
                modifier = Modifier.fillMaxSize(),
                cropRect = cropMask,
                pinchCornersReactor = PinchCornersReactor(
                    onPinchLeftTop = { x, y ->
                        pinchCornersReactor.onPinchLeftTop(
                            x / width,
                            y / height
                        )
                    },
                    onPinchRightTop = { x, y ->
                        pinchCornersReactor.onPinchRightTop(
                            x / width,
                            y / height
                        )
                    },
                    onPinchBottomRight = { x, y ->
                        pinchCornersReactor.onPinchBottomRight(
                            x / width,
                            y / height
                        )
                    },
                    onPinchBottomLeft = { x, y ->
                        pinchCornersReactor.onPinchBottomLeft(
                            x / width,
                            y / height
                        )
                    },
                    onPinchLeft = { x, _ -> pinchCornersReactor.onPinchLeft(x / width, 0f) },
                    onPinchTop = { _, y -> pinchCornersReactor.onPinchTop(0f, y / height) },
                    onPinchRight = { x, _ -> pinchCornersReactor.onPinchRight(x / width, 0f) },
                    onPinchBottom = { _, y -> pinchCornersReactor.onPinchBottom(0f, y / height) },
                ),
                pinchAvailableOnBorders = pinchAvailableOnBorders,
                moveCropMask = { x, y -> moveCropMask(x / width, y / height) }
            )
        }
    }
}


@Preview
@Composable
fun PhotoCropperPreview() {
    val photo = testPhotos[1]
    PhotoCropper(
        modifier = Modifier.fillMaxSize(),
        photo = photo,
        cropMask = CropMask(left = 0.1f, right = 0.2f, top = 0.3f, bottom = 0.4f),
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
        pinchAvailableOnBorders = true,
        moveCropMask = { _, _ -> }
    )
}