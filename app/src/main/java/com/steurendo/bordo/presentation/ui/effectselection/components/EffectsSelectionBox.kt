package com.steurendo.bordo.presentation.ui.effectselection.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.steurendo.bordo.R
import com.steurendo.bordo.domain.model.EffectParams
import com.steurendo.bordo.domain.model.PaddingEffect
import com.steurendo.bordo.presentation.common.shared_models.AlbumUiModel
import com.steurendo.bordo.presentation.theme.PreviewTheme
import com.steurendo.bordo.presentation.theme.BordoAppTheme

@Composable
fun EffectsSelectionBox(
    modifier: Modifier = Modifier,
    onSelectEffect: (PaddingEffect) -> Unit,
    onEffectParametersSet: (EffectParams) -> Unit,
    selectedPaddingEffect: PaddingEffect,
    effectParams: EffectParams
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
    ) {
        EffectParameters(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.2f),
            effect = selectedPaddingEffect,
            params = effectParams,
            onParametersSet = onEffectParametersSet
        )
        EffectsList(
            onClickEffect = onSelectEffect,
            selectedPaddingEffect = selectedPaddingEffect
        )
    }
}


@PreviewTheme
@Composable
fun EffectSelectionBoxWhiteBlackPreview() {
    val album = AlbumUiModel(
        paddingEffect = PaddingEffect.WhiteBlack,
        effectParameters = EffectParams.init(PaddingEffect.WhiteBlack)
    )

    BordoAppTheme {
        EffectsSelectionBox(
            onSelectEffect = {},
            onEffectParametersSet = {},
            selectedPaddingEffect = album.paddingEffect,
            effectParams = album.effectParameters
        )
    }
}

@PreviewTheme
@Composable
fun EffectSelectionBoxBlurPreview() {
    val album = AlbumUiModel(
        paddingEffect = PaddingEffect.Blur,
        effectParameters = EffectParams.init(PaddingEffect.Blur)
    )

    BordoAppTheme {
        EffectsSelectionBox(
            onSelectEffect = {},
            onEffectParametersSet = {},
            selectedPaddingEffect = album.paddingEffect,
            effectParams = album.effectParameters
        )
    }
}