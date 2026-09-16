package com.steurendo.bordo.presentation.ui.effectselection.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.steurendo.bordo.R
import com.steurendo.bordo.app.BordoApplication.Companion.USING_LEGACY_BLUR
import com.steurendo.bordo.domain.model.BlurEffectParams
import com.steurendo.bordo.domain.model.EffectParams
import com.steurendo.bordo.domain.model.PaddingEffect
import com.steurendo.bordo.domain.model.WhiteBlackEffectParams
import com.steurendo.bordo.presentation.theme.PreviewTheme
import com.steurendo.bordo.presentation.theme.BordoAppTheme


@Composable
fun EffectParameters(
    modifier: Modifier = Modifier,
    onParametersSet: (EffectParams) -> Unit,
    effect: PaddingEffect,
    params: EffectParams
) {
    when (effect) {
        PaddingEffect.WhiteBlack -> WhiteBlackEffectParameters(
            modifier = modifier,
            isBlack = (params as WhiteBlackEffectParams).isBlack,
            onSelect = { isBlackSelected ->
                onParametersSet(WhiteBlackEffectParams(isBlack = isBlackSelected))
            })

        PaddingEffect.Blur -> BlurEffectParameters(
            modifier = modifier,
            blurAmount = (params as BlurEffectParams).blurRadius,
            onBlurAmountSet = { onParametersSet(BlurEffectParams(it)) }
        )
    }
}

private data class WhiteBlackRadioOption(
    val text: String,
    val action: () -> Unit
)

@Composable
private fun WhiteBlackEffectParameters(
    modifier: Modifier,
    isBlack: Boolean,
    onSelect: (isBlackSelected: Boolean) -> Unit
) {
    val radioOptions: List<WhiteBlackRadioOption> = listOf(
        WhiteBlackRadioOption(
            text = stringResource(R.string.effect_white_black_white),
            action = { onSelect(false) }
        ),
        WhiteBlackRadioOption(
            text = stringResource(R.string.effect_white_black_black),
            action = { onSelect(true) }
        )
    )
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[if (isBlack) 1 else 0]) }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = stringResource(id = R.string.effect_white_black))
        Row(
            modifier = Modifier
                .selectableGroup()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            radioOptions.forEach { radioOption ->
                Row(
                    modifier = Modifier
                        .selectable(
                            selected = radioOption == selectedOption,
                            onClick = {
                                onOptionSelected(radioOption)
                                radioOption.action()
                            },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = dimensionResource(R.dimen.padding_medium)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = radioOption == selectedOption, onClick = null)
                    Text(
                        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_small)),
                        text = radioOption.text
                    )
                }
            }
        }
    }
}

@Composable
private fun BlurEffectParameters(
    modifier: Modifier,
    blurAmount: Float,
    onBlurAmountSet: (Float) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var sliderState by remember { mutableFloatStateOf(blurAmount) }
        Text(text = stringResource(id = R.string.effect_blur))
        Slider(
            modifier = Modifier.fillMaxWidth(fraction = 0.85f),
            value = sliderState,
            onValueChange = {
                sliderState = it
                if (!USING_LEGACY_BLUR) onBlurAmountSet(sliderState)
            },
            onValueChangeFinished = { if (USING_LEGACY_BLUR) onBlurAmountSet(sliderState) },
            valueRange = 0f..1f
        )
    }
}


@PreviewTheme
@Composable
private fun WhiteBlackEffectParametersPreview() {
    BordoAppTheme {
        EffectParameters(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp),
            onParametersSet = {},
            effect = PaddingEffect.WhiteBlack,
            params = EffectParams.init(PaddingEffect.WhiteBlack)
        )
    }
}

@PreviewTheme
@Composable
private fun BlurEffectParametersPreview() {
    BordoAppTheme {
        EffectParameters(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp),
            onParametersSet = {},
            effect = PaddingEffect.Blur,
            params = BlurEffectParams(BlurEffectParams.DEFAULT_BLUR_RADIUS)
        )
    }
}