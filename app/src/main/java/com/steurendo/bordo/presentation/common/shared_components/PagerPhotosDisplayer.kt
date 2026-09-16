package com.steurendo.bordo.presentation.common.shared_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.steurendo.bordo.R
import com.steurendo.bordo.domain.model.BlurEffectParams
import com.steurendo.bordo.domain.model.EffectParams
import com.steurendo.bordo.domain.model.PaddingEffect
import com.steurendo.bordo.domain.model.WhiteBlackEffectParams
import com.steurendo.bordo.presentation.common.mock.testPhotos
import com.steurendo.bordo.presentation.common.shared_models.AlbumPhotoUiModel
import com.steurendo.bordo.presentation.common.shared_models.AlbumUiModel
import com.steurendo.bordo.presentation.common.shared_models.computedAspectRatio
import com.steurendo.bordo.presentation.common.shared_models.getReferencePhoto


@Composable
fun PagerPhotosDisplayer(
    modifier: Modifier = Modifier,
    initialPage: Int = 0,
    emitCurrentPhotoIndex: (Int) -> Unit = {},
    album: AlbumUiModel
) {
    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { album.photos.size },
    )
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { emitCurrentPhotoIndex(it) }
    }
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState
        ) { page ->
            FramedPhotoDisplayer(
                modifier = Modifier.fillMaxSize(),
                photo = album.photos[page],
                aspectRatio = album.getReferencePhoto().computedAspectRatio,
                paddingEffect = album.paddingEffect,
                paddingEffectParameters = album.effectParameters
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(dimensionResource(id = R.dimen.padding_small))
        ) {
            PhotosIndicatorNumber(
                modifier = Modifier,
                photoNumber = (pagerState.currentPage + 1),
                photosCount = pagerState.pageCount,
                isReferencePhotoIndex = pagerState.currentPage == album.referencePhotoIndex
            )
        }
    }
}

@Composable
fun Int.toDp() = with(LocalDensity.current) { this@toDp.toDp() }

//@Composable
//fun Dp.toPx() = with(LocalDensity.current) { this@toPx.toPx() }

@Composable
fun ImagePaddingContainer(
    modifier: Modifier = Modifier,
    photo: AlbumPhotoUiModel,
    paddingEffect: PaddingEffect,
    effectParams: EffectParams
) {
    when (paddingEffect) {
        PaddingEffect.WhiteBlack -> Box(modifier = modifier.background(color = if ((effectParams as WhiteBlackEffectParams).isBlack) Color.Black else Color.White))
        PaddingEffect.Blur -> AdaptiveAsyncImage(
            modifier = modifier,
            uri = photo.uri,
            blurRadiusFactor = (effectParams as BlurEffectParams).blurRadius,
            contentScale = ContentScale.Crop,
            placeholder = photo.placeholderId?.let { painterResource(id = it) },
            contentDescription = null
        )
    }
}

@Preview
@Composable
private fun PagerPhotosDisplayerPreview() {
    PagerPhotosDisplayer(
        emitCurrentPhotoIndex = {},
        album = AlbumUiModel(
            photos = testPhotos,
            referencePhotoIndex = 0,
            paddingEffect = PaddingEffect.Blur,
            effectParameters = EffectParams.init(PaddingEffect.Blur)
        ),
    )
}