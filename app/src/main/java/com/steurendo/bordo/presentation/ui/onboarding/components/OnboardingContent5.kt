package com.steurendo.bordo.presentation.ui.onboarding.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.steurendo.bordo.R
import com.steurendo.bordo.presentation.common.shared_components.FavoriteIcon
import com.steurendo.bordo.presentation.ui.onboarding.aspectRatio
import com.steurendo.bordo.presentation.ui.onboarding.imagesRes
import com.steurendo.bordo.presentation.ui.onboarding.nCols
import com.steurendo.bordo.presentation.ui.onboarding.nRows
import com.steurendo.bordo.presentation.ui.onboarding.referencePhotoIndex
import kotlinx.coroutines.launch


private const val animationDuration: Int = 6000

@Composable
fun OnboardingContent5(modifier: Modifier = Modifier) {
    val alphaAnimation = remember { Animatable(1f) }
    val scaleAnimation = remember { Animatable(1f) }
    val galleryVisibilityAnimation = remember { Animatable(0f) }
    val downloadVisibilityAnimation = remember { Animatable(0f) }

    LaunchedEffect(null) {
        alphaAnimation.snapTo(0f)
        scaleAnimation.snapTo(1f)
        galleryVisibilityAnimation.snapTo(0f)
        downloadVisibilityAnimation.snapTo(0f)
        launch {
            alphaAnimation.animateTo(
                targetValue = 0f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = animationDuration
                        0f at 0
                        1f at 500
                        0f at 3000
                        0f at animationDuration
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }
        launch {
            scaleAnimation.animateTo(
                targetValue = 0.2f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = animationDuration
                        1f at 500
                        0.2f at 3000
                        1f at animationDuration
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }
        launch {
            galleryVisibilityAnimation.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = animationDuration
                        0f at 2500
                        1f at 3000
                        1f at 5500
                        0f at animationDuration
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }
        launch {
            downloadVisibilityAnimation.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = animationDuration
                        0f at 3000
                        1f at 3500
                        1f at 5500
                        0f at animationDuration
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    OnboardingContentBase(
        modifier = modifier,
        title = stringResource(R.string.page5_title),
        subTitle = stringResource(R.string.page5_subtitle),
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(R.dimen.padding_medium))
            ) {
                PicturesGrid(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .alpha(alphaAnimation.value)
                        .scale(scaleAnimation.value)
                )
                Icon(
                    painterResource(R.drawable.baseline_photo_album_24),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(256.dp)
                        .alpha(galleryVisibilityAnimation.value)
                )
                Icon(
                    painterResource(R.drawable.baseline_download_24),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .size(96.dp)
                        .alpha(downloadVisibilityAnimation.value)
                )
            }
        }
    )
}

@Composable
private fun PicturesGrid(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(dimensionResource(R.dimen.shape_small)))
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(dimensionResource(R.dimen.shape_small))
            )
            .background(color = Color.LightGray)
            .aspectRatio(1f)
            .padding(dimensionResource(R.dimen.padding_medium)),
    ) {
        for (row in 0..<nRows)
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                for (col in 0..<nCols) {
                    Box(
                        modifier = Modifier
                            .aspectRatio(ratio = aspectRatio)
                            .padding(dimensionResource(R.dimen.padding_medium))
                            .weight(weight = 1f)
                            .border(width = 1.dp, color = Color.Gray)
                            .background(color = Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        if (row * nCols + col == referencePhotoIndex) {
                            Image(
                                painterResource(imagesRes[row * nCols + col]),
                                contentDescription = null,
                            )
                            FavoriteIcon(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .scale(0.9f)
                            )
                        } else
                            Image(
                                painterResource(imagesRes[row * nCols + col]),
                                contentDescription = null,
                            )
                    }
                }
            }
    }
}


@Preview(showBackground = true)
@Composable
private fun OnboardingContent5Preview() {
    OnboardingContent5(modifier = Modifier.fillMaxSize())
}