package com.steurendo.bordo.presentation.common.shared_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.steurendo.bordo.R
import com.steurendo.bordo.domain.model.CropMask
import com.steurendo.bordo.domain.model.EffectParams
import com.steurendo.bordo.domain.model.PaddingEffect
import com.steurendo.bordo.presentation.common.shared_models.AlbumPhotoUiModel
import com.steurendo.bordo.presentation.common.shared_models.aspectRatio
import kotlin.math.roundToInt

/* Single-photo container and displayer */

@Composable
fun FramedPhotoDisplayer(
    modifier: Modifier = Modifier,
    photo: AlbumPhotoUiModel,
    aspectRatio: Float,
    paddingEffect: PaddingEffect,
    paddingEffectParameters: EffectParams
) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .aspectRatio(aspectRatio)
                .fillMaxSize()
                .align(Alignment.Center)
                .border(BorderStroke(2.dp, Color.Black))
        ) {
            val photoWidth = photo.width * (1 - (photo.cropMask.left + photo.cropMask.right))
            val photoHeight = photo.height * (1 - (photo.cropMask.top + photo.cropMask.bottom))
            ImagePaddingContainer(
                modifier = Modifier
                    .aspectRatio(aspectRatio)
                    .align(Alignment.Center),
                photo = photo,
                paddingEffect = paddingEffect,
                effectParams = paddingEffectParameters
            )
            AsyncImage(
                modifier = Modifier
                    .scale(
                        if (aspectRatio > photo.aspectRatio) (photo.height / photoHeight).coerceAtMost(
                            aspectRatio * photo.height / photoWidth
                        )
                        else (photo.width / photoWidth).coerceAtMost(photo.width / (aspectRatio * photoHeight))
                    )
                    .align(Alignment.Center)
                    .clip(RectangleShape)
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)
                        layout(
                            width = (placeable.width * photoWidth / photo.width).roundToInt(),
                            height = (placeable.height * photoHeight / photo.height).roundToInt()
                        ) {
                            placeable.place(
                                -(placeable.width * photo.cropMask.left).roundToInt(),
                                -(placeable.height * photo.cropMask.top).roundToInt()
                            )
                        }
                    }
                    .aspectRatio(photo.aspectRatio),
                model = photo.uri,
                placeholder = photo.placeholderId?.let { painterResource(id = it) },
                contentDescription = null
            )
        }
    }
}


private val cropMask = CropMask(
    left = 0.1f,
    top = 0.5f,
    right = 0.3f,
    bottom = 0.2f
)

@Preview(showBackground = true)
@Composable
private fun PhotoDisplayerPreview1() {
    FramedPhotoDisplayer(
        modifier = Modifier
            .background(Color.LightGray)
            .aspectRatio(1.4f),
        photo = AlbumPhotoUiModel(
            placeholderId = R.drawable.pic_v,
            cropMask = cropMask,
            width = 3000,
            height = 4000
        ),
        aspectRatio = 0.6f,
        paddingEffect = PaddingEffect.WhiteBlack,
        paddingEffectParameters = EffectParams.init(PaddingEffect.WhiteBlack)
    )
}

@Preview(showBackground = true)
@Composable
private fun PhotoDisplayerPreview2() {
    FramedPhotoDisplayer(
        modifier = Modifier
            .background(Color.LightGray)
            .aspectRatio(1.4f),
        photo = AlbumPhotoUiModel(
            placeholderId = R.drawable.pic_v,
            cropMask = cropMask,
            width = 3000,
            height = 4000
        ),
        aspectRatio = 2f,
        paddingEffect = PaddingEffect.WhiteBlack,
        paddingEffectParameters = EffectParams.init(PaddingEffect.WhiteBlack)
    )
}

@Preview(showBackground = true)
@Composable
private fun PhotoDisplayerPreview3() {
    FramedPhotoDisplayer(
        modifier = Modifier
            .background(Color.LightGray)
            .aspectRatio(1.4f),
        photo = AlbumPhotoUiModel(
            placeholderId = R.drawable.pic_h,
            cropMask = cropMask,
            width = 4000,
            height = 3000
        ),
        aspectRatio = 0.6f,
        paddingEffect = PaddingEffect.WhiteBlack,
        paddingEffectParameters = EffectParams.init(PaddingEffect.WhiteBlack)
    )
}

@Preview(showBackground = true)
@Composable
private fun PhotoDisplayerPreview4() {
    FramedPhotoDisplayer(
        modifier = Modifier
            .background(Color.LightGray)
            .aspectRatio(1.4f),
        photo = AlbumPhotoUiModel(
            placeholderId = R.drawable.pic_h,
            cropMask = cropMask,
            width = 4000,
            height = 3000
        ),
        aspectRatio = 2f,
        paddingEffect = PaddingEffect.WhiteBlack,
        paddingEffectParameters = EffectParams.init(PaddingEffect.WhiteBlack)
    )
}