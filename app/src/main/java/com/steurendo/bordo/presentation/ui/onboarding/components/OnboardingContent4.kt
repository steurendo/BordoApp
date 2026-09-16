package com.steurendo.bordo.presentation.ui.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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


@Composable
fun OnboardingContent4(modifier: Modifier = Modifier) {
    OnboardingContentBase(
        modifier = modifier,
        title = stringResource(R.string.page4_title),
        subTitle = stringResource(R.string.page4_subtitle),
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(R.dimen.padding_medium))
            ) {
                PicturesGrid(modifier = Modifier.align(Alignment.Center))
            }
        }
    )
}

@Composable
private fun PicturesGrid(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(dimensionResource(R.dimen.shape_small)))
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(dimensionResource(R.dimen.shape_small)))
            .background(color = Color.LightGray),
    ) {
        for (row in 0..<nRows)
            Row(verticalAlignment = Alignment.CenterVertically) {
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
    OnboardingContent4(modifier = Modifier.fillMaxSize())
}