package com.steurendo.bordo.presentation.ui.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.steurendo.bordo.R


@Composable
fun PageIndicator(modifier: Modifier = Modifier, state: PagerState) {
    Row(modifier = modifier) {
        repeat(state.pageCount) { iteration ->
            val color = if (state.currentPage == iteration) Color.Blue else Color.LightGray
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(dimensionResource(R.dimen.shape_extra_small))
            )
        }
    }
}


@Preview
@Composable
fun PageIndicatorPreview() {
    PageIndicator(state = rememberPagerState(pageCount = { 4 }, initialPage = 1))
}