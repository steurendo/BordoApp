package com.steurendo.bordo.presentation.ui.onboarding.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.steurendo.bordo.R
import com.steurendo.bordo.presentation.common.shared_components.AppLogo


@Composable
fun OnboardingContent1(modifier: Modifier = Modifier) {
    OnboardingContentBase(
        modifier = modifier,
        title = stringResource(R.string.page1_title),
        subTitle = stringResource(R.string.page1_subtitle),
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AppLogo(
                    modifier = Modifier.fillMaxWidth(fraction = 0.7f)
                )
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
private fun OnboardingContent1Preview() {
    OnboardingContent1(modifier = Modifier.fillMaxSize())
}