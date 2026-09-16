package com.steurendo.bordo.presentation.ui.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.steurendo.bordo.R


@Composable
fun OnboardingContentBase(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .weight(0.7f)
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            content = content
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.75f)
                .weight(0.3f)
                .align(alignment = Alignment.CenterHorizontally)
                .padding(vertical = dimensionResource(R.dimen.padding_medium)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
            Text(text = subTitle, textAlign = TextAlign.Center)
        }
    }
}


@Preview
@Composable
fun OnboardingContentBasePreview() {
    OnboardingContentBase(
        modifier = Modifier.background(color = Color.White),
        title = "Title",
        subTitle = "Sub-title",
        content = {
            Surface(color = Color.Green, modifier = Modifier.fillMaxSize()) {}
        })
}