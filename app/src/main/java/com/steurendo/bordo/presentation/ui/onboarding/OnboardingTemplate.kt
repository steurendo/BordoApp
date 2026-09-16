package com.steurendo.bordo.presentation.ui.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.steurendo.bordo.R
import com.steurendo.bordo.presentation.theme.PreviewTheme
import com.steurendo.bordo.presentation.theme.BordoAppTheme
import com.steurendo.bordo.presentation.ui.onboarding.components.OnboardingBottomPanel
import com.steurendo.bordo.presentation.ui.onboarding.components.OnboardingContent1
import com.steurendo.bordo.presentation.ui.onboarding.components.OnboardingContent2
import com.steurendo.bordo.presentation.ui.onboarding.components.OnboardingContent3
import com.steurendo.bordo.presentation.ui.onboarding.components.OnboardingContent4
import com.steurendo.bordo.presentation.ui.onboarding.components.OnboardingContent5
import com.steurendo.bordo.presentation.ui.onboarding.components.PageIndicator


@Composable
fun OnboardingTemplate(
    onFinish: () -> Unit,
    onClickNext: () -> Unit,
    pagerState: PagerState
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            HorizontalPager(
                modifier = Modifier.weight(0.9f),
                state = pagerState
            ) { page ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    PageContentSwitcher(
                        modifier = Modifier.weight(1f),
                        page = page
                    )
                }
            }
            PageIndicator(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                state = pagerState
            )
            OnboardingBottomPanel(
                modifier = Modifier
                    .weight(0.1f)
                    .padding(
                        vertical = dimensionResource(id = R.dimen.padding_small),
                        horizontal = dimensionResource(id = R.dimen.padding_large)
                    ),
                onClickFinish = onFinish,
                onClickNext = onClickNext,
                isLastPage = pagerState.currentPage == pagerState.pageCount - 1
            )
        }
    }
}

@Composable
private fun PageContentSwitcher(modifier: Modifier = Modifier, page: Int) {
    when (page) {
        0 -> OnboardingContent1(modifier = modifier)
        1 -> OnboardingContent2(modifier = modifier)
        2 -> OnboardingContent3(modifier = modifier)
        3 -> OnboardingContent4(modifier = modifier)
        4 -> OnboardingContent5(modifier = modifier)
    }
}


@PreviewTheme
@Composable
private fun OnboardingTemplatePreview() {
    BordoAppTheme {
        OnboardingTemplate(
            pagerState = rememberPagerState(pageCount = { 5 }),
            onClickNext = {},
            onFinish = {},
        )
    }
}