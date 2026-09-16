package com.steurendo.bordo.presentation.ui.onboarding.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.steurendo.bordo.R
import com.steurendo.bordo.presentation.common.shared_components.CustomButton
import com.steurendo.bordo.presentation.theme.PreviewTheme
import com.steurendo.bordo.presentation.theme.BordoAppTheme


@Composable
fun OnboardingBottomPanel(
    modifier: Modifier = Modifier,
    onClickFinish: () -> Unit,
    onClickNext: () -> Unit,
    isLastPage: Boolean
) {
    val transitionBetweenButtons = remember { Animatable(if (!isLastPage) 0f else 1f) }
    var showFinishButtonText by remember { mutableStateOf(true) }
    var showTwoButtonsText by remember { mutableStateOf(true) }
    var showTwoButtons by remember { mutableStateOf(!isLastPage) }

    LaunchedEffect(isLastPage) {
        if (isLastPage) {
            transitionBetweenButtons.animateTo(1f, animationSpec = tween(durationMillis = 200))
            showTwoButtons = false
            showFinishButtonText = true
        } else {
            showTwoButtonsText = true
            showFinishButtonText = false
            showTwoButtons = true
            transitionBetweenButtons.animateTo(0f, animationSpec = tween(durationMillis = 200))
        }
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy((24 - transitionBetweenButtons.value * 88).dp)
    ) {
        if (showTwoButtons) {
            CustomButton(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(
                        horizontal = dimensionResource(R.dimen.padding_small),
                        vertical = dimensionResource(R.dimen.padding_small)
                    ),
                onClick = onClickFinish
            ) {
                if (showTwoButtonsText)
                    Text(text = stringResource(R.string.onboarding_button_skip))
            }
            CustomButton(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(
                        horizontal = dimensionResource(R.dimen.padding_small),
                        vertical = dimensionResource(R.dimen.padding_small)
                    ),
                onClick = onClickNext
            ) {
                if (showTwoButtonsText)
                    Text(
                        modifier = Modifier.alpha(1f - transitionBetweenButtons.value),
                        text = stringResource(id = R.string.onboarding_button_next)
                    )
            }
        } else {
            CustomButton(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(
                        horizontal = dimensionResource(R.dimen.padding_small),
                        vertical = dimensionResource(R.dimen.padding_small)
                    ),
                onClick = onClickFinish
            ) {
                if (showFinishButtonText)
                    Text(
                        modifier = Modifier.alpha(transitionBetweenButtons.value),
                        text = stringResource(id = R.string.onboarding_button_understood)
                    )
            }
        }
    }
}


@PreviewTheme
@Composable
fun OnboardingBottomPanelPreview() {
    BordoAppTheme {
        OnboardingBottomPanel(
            modifier = Modifier.height(64.dp),
            onClickFinish = {},
            onClickNext = {},
            isLastPage = false
        )
    }
}