package com.steurendo.bordo.presentation.ui.effectselection.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.steurendo.bordo.R
import com.steurendo.bordo.domain.model.PaddingEffect
import com.steurendo.bordo.presentation.theme.BordoAppTheme
import com.steurendo.bordo.presentation.theme.PreviewTheme


@Composable
fun EffectsList(
    modifier: Modifier = Modifier,
    onClickEffect: (PaddingEffect) -> Unit,
    selectedPaddingEffect: PaddingEffect
) {
    Row(modifier = modifier) {
        EffectItem(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.5f)
                .padding(dimensionResource(id = R.dimen.padding_small)),
            selected = selectedPaddingEffect == PaddingEffect.WhiteBlack,
            onClick = { onClickEffect(PaddingEffect.WhiteBlack) },
            title = stringResource(id = R.string.effect_white_black),
            content = {
                EffectItemWhiteBlackThumbnail()
            }
        )
        EffectItem(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_small)),
            selected = selectedPaddingEffect == PaddingEffect.Blur,
            onClick = { onClickEffect(PaddingEffect.Blur) },
            title = stringResource(id = R.string.effect_blur),
            content = {
                EffectItemBlurThumbnail()
            }
        )
    }
}


@PreviewTheme
@Composable
private fun EffectsListPreview() {
    BordoAppTheme {
        EffectsList(
            onClickEffect = {},
            selectedPaddingEffect = PaddingEffect.Blur
        )
    }
}