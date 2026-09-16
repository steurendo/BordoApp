package com.steurendo.bordo.presentation.ui.effectselection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.steurendo.bordo.R
import com.steurendo.bordo.domain.model.EffectParams
import com.steurendo.bordo.domain.model.PaddingEffect
import com.steurendo.bordo.presentation.common.mock.testPhotos
import com.steurendo.bordo.presentation.common.shared_components.PagerPhotosDisplayer
import com.steurendo.bordo.presentation.common.shared_models.AlbumUiModel
import com.steurendo.bordo.presentation.theme.PreviewTheme
import com.steurendo.bordo.presentation.theme.BordoAppTheme
import com.steurendo.bordo.presentation.ui.effectselection.components.EffectsSelectionBox
import com.steurendo.bordo.presentation.ui.effectselection.components.TopBar


@Composable
fun SelectPaddingEffectTemplate(
    onClickBack: () -> Unit,
    onClickSave: () -> Unit,
    onEffectSelected: (PaddingEffect) -> Unit,
    onEffectParametersSet: (EffectParams) -> Unit,
    album: AlbumUiModel,
    currentPhotoIndex: Int
) {
    Scaffold(
        topBar = {
            TopBar(
                onClickBack = onClickBack,
                onClickSave = onClickSave
            )
        }
    ) { innerPadding ->
        EffectSelectionScreenContent(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            album = album,
            onSelectEffect = onEffectSelected,
            onEffectParametersSet = onEffectParametersSet,
            selectedPaddingEffect = album.paddingEffect,
            currentPhotoIndex = currentPhotoIndex
        )
    }
}

@Composable
fun EffectSelectionScreenContent(
    onSelectEffect: (PaddingEffect) -> Unit,
    onEffectParametersSet: (EffectParams) -> Unit,
    modifier: Modifier = Modifier,
    album: AlbumUiModel,
    currentPhotoIndex: Int,
    selectedPaddingEffect: PaddingEffect
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        PagerPhotosDisplayer(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.65f),
            album = album,
            initialPage = currentPhotoIndex
        )
        EffectsSelectionBox(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.padding_small)),
            onSelectEffect = onSelectEffect,
            onEffectParametersSet = onEffectParametersSet,
            selectedPaddingEffect = selectedPaddingEffect,
            effectParams = album.effectParameters
        )
    }
}


@PreviewTheme
@Composable
private fun EffectSelectionScreenPreview() {
    val paddingEffect = PaddingEffect.Blur
    val album = AlbumUiModel(
        photos = testPhotos,
        referencePhotoIndex = 0,
        paddingEffect = paddingEffect,
        effectParameters = EffectParams.init(paddingEffect)
    )

    BordoAppTheme {
        EffectSelectionScreenContent(
            modifier = Modifier.fillMaxSize(),
            onSelectEffect = {},
            onEffectParametersSet = {},
            album = album,
            selectedPaddingEffect = album.paddingEffect,
            currentPhotoIndex = 0
        )
    }
}